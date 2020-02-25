import Vue from 'vue'
import SearchResult from './SearchResult.vue'
import router from '@/router'
import api from "../backend-api.js"
import Footer from '@/components/Footer.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

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
		this.search(this.$route.query.k, this.current_page);
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			keyword: this.$route.query.k,
			search_result: {},
			current_page: null,
			result_count_fake: null
		}
	},
	methods: {
		searchSubmit() {
			window.location.href = '/search?k=' + this.keyword;
		},
		search() {
			api.search2(this.keyword, this.current_page).then(response => {
				// console.log(JSON.stringify(response));
				this.setData(response);
			}, error => console.log(error));
		},
		setData(response) {
			this.search_result = response;
			this.current_page = response.current_page;
			this.result_count_fake = response.count_fake.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		},
		goToPage(pageNumber) {
			alert(pageNumber)
		}
	},

	components: {
		Footer
	}
}