import Vue from 'vue'
import Admin from './Admin.vue'
import router from '@/router'
import store from '@/pages/admin/store'
import moment from 'moment'
import VueNumericInput from 'vue-numeric-input';

Vue.config.productionTip = false
Vue.use(VueNumericInput)

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