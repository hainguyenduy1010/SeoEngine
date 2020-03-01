import Vue from 'vue'
import router from '@/router'
import Admin from './Admin.vue'

import store from '@/pages/admin/store'

Vue.config.productionTip = false

window.onload = function () {
	new Vue({
		router,
		store,
		render: h => h(Admin)
	}).$mount('#app');
}

export default {
	name: 'admin'
}