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
		}, error => console.log(error));
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			search_result: {},
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
			this.current_page = response.current_page;
			this.result_count_fake = response.count_fake.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			this.number_of_pages = Math.ceil(parseInt(response.count) / parseInt(response.number_results_per_page));
		},
		linkGen(pageNumber) {
			return `/search?k=` + this.keyword + `&p=${pageNumber}`
		}
	},

	components: {
		Footer
	}
}