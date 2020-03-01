import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../pages/home/Home.vue'
import Search from '../pages/search-result/SearchResult.vue'
import Admin from '../pages/admin/Admin.vue'
import Login from '../pages/admin/login/Login.vue'

import store from '@/pages/admin/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      title: 'Search Homepage'
    }
  },
  {
    path: '/search?k=:keyword',
    name: 'search',
    component: Search,
    meta: {
      title: 'Search Results'
    }
  },
  {
    path: '/admin',
    name: 'admin',
    component: Admin,
    meta: {
      title: 'Admin',
      requiresAuth: true
    }
  },
  {
    path: '/admin/login',
    name: 'login',
    component: Login,
    meta: {
      title: 'Admin login'
    }
  }

  // { path: '*', redirect: '/' }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
  /* It will change the title when the router is change*/
  if (to.meta.title) {
    document.title = to.meta.title;
  }

  if (to.meta.requiresAuth && !localStorage.getItem('user')) {
    if (!store.getters.isLoggedIn) {
      next('/admin/login')
    }
  }

  next();
});

export default router
