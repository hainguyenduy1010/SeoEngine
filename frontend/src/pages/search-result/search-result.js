import Vue from 'vue'
import SearchResult from './SearchResult.vue'
import router from '@/router'
import api from "../backend-api.js"
import Footer from '@/components/Footer.vue'

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
		search() {
			api.search2(this.keyword).then(response => {
				// console.log(JSON.stringify(response));
				this.setData(response);
			}, error => console.log(error));
		},
		setData(response) {
			this.searchResult = response;
			this.resultCount = this.searchResult.count.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		// setSuggestionUrl(suggestionList) {
		// 	for (suggestion of suggestionList) {
		// 		var keyword = suggestion.keyword;
		// 		// var url =
		// 	}
		// }
	},

	components: {
		Footer
	}
}