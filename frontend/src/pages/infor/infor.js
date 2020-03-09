import Vue from 'vue'
import Infor from './Infor.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

window.onload = function () {
	new Vue({
		render: h => h(Infor)
	}).$mount('#app');
}