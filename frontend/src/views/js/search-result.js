import api from "./backend-api.js"

export default {
	mounted() {
		this.search(this.$route.params.keyword);
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			keyword: this.$route.params.keyword,
			searchResult: [],
			resultCount: ''
		}
	},
	methods: {
		search() {
			api.search(this.keyword).then(response => {
				this.searchResult = response.data;
				// console.log(JSON.stringify(response))
				this.resultCount = this.searchResult.count.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}, error => console.log(error));
		}
	}
}