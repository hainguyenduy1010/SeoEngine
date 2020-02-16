import api from "./backend-api.js"

export default {
	mounted() {
		this.search(this.$route.params.keyword);
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			keyword: this.$route.params.keyword,
			results: []
		}
	},
	methods: {
		search() {
			api.search(this.keyword).then(response => {
				this.results = response.data;
				// console.log(JSON.stringify(response))
			}, error => console.log(error));
		}
	}
}