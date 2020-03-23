import Vue from 'vue'
import SearchResult from './SearchResult.vue'
import router from '@/router'
import api from "../backend-api.js"
import Footer from '@/components/Footer.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import $ from 'jquery'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

Vue.config.productionTip = false

window.onload = function () {
	new Vue({
		router,
		render: h => h(SearchResult)
	}).$mount('#app')
}

export default {
	name: 'Search',
	beforeMount() {
		api.search2(this.$route.query.k, this.$route.query.p).then(response => {
			// console.log(JSON.stringify(response));
			this.setData(response);
			if (response.title) {
				window.document.title = response.title;
			} else {
				window.document.title = this.$route.query.k + " - GetCode Search Results";
			}
			if (response.description) {
				var metaDes = document.querySelector('meta[name="description"]');
				if (metaDes) {
					metaDes.setAttribute("content", response.description);
				} else {
					var meta = document.createElement('meta');
					meta.setAttribute('name', 'description');
					meta.setAttribute('content', response.description);
					document.getElementsByTagName('title')[0].after(meta);
				}
			}
		}, error => console.log(error));
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			search_result: {},
			data_list: [],
			keyword: this.$route.query.k,
			result_count_fake: null,
			number_of_pages: null,
			current_page: 0
		}
	},
	methods: {
		searchSubmit() {
			if (this.keyword) {
				window.location.href = '/search?k=' + this.keyword;
			}
		},
		setData(response) {
			this.search_result = response;
			this.data_list = response.search_data_list;
			this.current_page = response.current_page;
			this.result_count_fake = response.count_fake.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			this.number_of_pages = Math.ceil(parseInt(response.count) / parseInt(response.number_results_per_page));
			// this.getExternalResults(this.data_list, response.external_param);
		},
		linkGen(pageNumber) {
			return `/search?k=` + this.keyword + `&p=${pageNumber}`
		},
		getExternalResults(data_list, external_param) {

			var start = external_param.start;
			var limit = external_param.limit;
			var url = external_param.url;
			var param = new URLSearchParams({
					key: external_param.key,
					cx: external_param.cx,
					q: this.keyword
				});
			
			if (limit <= 10 && limit > 0) {
				param.set('start', start);
				param.set('limit', limit);

				$.getJSON(url + param.toString(), function(data) {
					data.items.forEach(item => {
						var result = {};

						result.title = item.htmlTitle;
						result.url = item.link
						result.description = item.htmlSnippet

						data_list.push(result);
					});

					this.search_result.total_time = parseInt(this.search_result.total_time) + parseInt(data.searchInformation.searchTime) * 1000;
				})
				.fail(function(error) {
					console.log(error);
				})
			} else if (limit > 10) {
				while (limit > 0) {
					param.set('start', start);
					param.set('limit', limit > 10 ? 10 : limit);

					$.getJSON(url + param.toString(), function(data) {
						console.log(data.items)
						data.items.forEach(item => {
							var result = {};
	
							result.title = item.htmlTitle;
							result.url = item.link
							result.description = item.htmlSnippet
	
							data_list.push(result);
						});
console.log(parseInt(data.searchInformation.searchTime))
						this.search_result.total_time = parseInt(this.search_result.total_time) + parseInt(data.searchInformation.searchTime) * 1000;
					})
					.fail(function(error) {
						console.log(error);
					})

					start = start + 10;
					limit = limit - 10;
				}				
			}

			// if (limit > 0) {
			// 	$.getJSON(url, function(data) {
			// 		data.items.forEach(item => {
			// 			var result = {};
			// 			result.title = item.htmlTitle;
			// 			result.url = item.link
			// 			result.description = item.htmlSnippet

			// 			data_list.push(result);
			// 		});

			// 		if (data.items && limit > 10) {
			// 			var recursionParam = external_param;
			// 			recursionParam.start = start + 10;
			// 			recursionParam.limit = limit - 10;

			// 			var recursionResults = this.getExternalResults(recursionParam);
			// 		}
			// 	}, error => console.log(error));
			// }
		},
	},

	components: {
		Footer
	}
}