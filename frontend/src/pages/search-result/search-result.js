import Vue from 'vue'
import SearchResult from './SearchResult.vue'
import router from '@/router'
import api from "../backend-api.js"
import Footer from '@/components/Footer.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

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
			current_page: 0,
			total_time: 0
		}
	},
	methods: {
		searchSubmit() {
			if (this.keyword) {
				window.location.href = '/search?' + new URLSearchParams({k: this.keyword});
			}
		},
		setData(response) {
			this.search_result = response;
			this.data_list = response.search_data_list;
			this.current_page = response.current_page;
			this.result_count_fake = response.count_fake.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			this.number_of_pages = Math.ceil(parseInt(response.count) / parseInt(response.number_results_per_page));
			this.total_time = response.total_time;
		},
		linkGen(pageNumber) {
			return `/search?k=` + this.keyword + `&p=${pageNumber}`
		}
	},

	components: {
		Footer
	}
}