import Vue from 'vue'
import Admin from './Admin.vue'
import router from '@/router'
import store from '@/pages/admin/store'

Vue.config.productionTip = false

window.onload = function () {
	new Vue({
		router,
		store,
		render: h => h(Admin)
	}).$mount('#app');
}