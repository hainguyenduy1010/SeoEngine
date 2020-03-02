import Vue from 'vue'
import Admin from './Admin.vue'
import router from '@/router'
import store from '@/pages/admin/store'
import moment from 'moment'

Vue.config.productionTip = false

window.onload = function () {
	new Vue({
		router,
		store,
		render: h => h(Admin)
	}).$mount('#app');

	Vue.filter('formatDate', function(value) {
		if (value) {
			return moment(String(value)).format('MM/DD/YYYY HH:mm:ss')
		}
	})
}