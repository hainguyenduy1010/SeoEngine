import Vue from 'vue'
import Home from './Home.vue'
import router from '@/router'
import Footer from '@/components/Footer.vue'

Vue.config.productionTip = false

window.onload = function () {
	new Vue({
		router,
		render: h => h(Home)
	}).$mount('#app');
}

export default {
	name: 'Home',
	components: {
		Footer
	},
	data() {
		return {
			logo: require('@/assets/search-logo.png'),
			keyword: ''
		}
	},
	methods: {
		searchSubmit() {
			window.location.href = '/search?k=' + this.keyword;
		}
	}
}