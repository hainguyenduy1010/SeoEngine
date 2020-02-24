import Vue from 'vue'
import SearchResult from './SearchResult.vue'
import router from '@/router'
import api from "../backend-api.js"
import Footer from '@/components/Footer.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(Footer)
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
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
		this.search(this.$route.query.k);
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			keyword: this.$route.query.k,
			searchResult: [],
			resultCount: ''
		}
	},
	methods: {
		searchSubmit() {
			window.location.href = '/search?k=' + this.keyword;
		},
		search() {
			api.search2(this.keyword).then(response => {
				// console.log(JSON.stringify(response));
				this.setData(response);
			}, error => console.log(error));
		},
		setData(response) {
			this.searchResult = response;
			this.resultCount = this.searchResult.count.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			this.setSuggestionUrl(this.searchResult.suggestionList);
		},
		setSuggestionUrl(suggestionList) {
			for (var suggestion of suggestionList) {
				var keyword = suggestion.keyword;
				var url = '/search?k=' + keyword;
				suggestion.url = url;
			}
		}
	}
}