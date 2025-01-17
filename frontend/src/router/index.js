import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../pages/home/Home.vue'
import SearchResult from '../pages/search-result/SearchResult.vue'
import Login from '../pages/admin/login/Login.vue'
import KeywordList from '../pages/admin/keyword-list/KeywordList.vue'
import DataList from '../pages/admin/data-list/DataList.vue'
import Create from '../pages/admin/create/Create.vue'
import Update from '../pages/admin/update/Update.vue'
import Infor from '../pages/infor/Infor.vue'

import store from '@/pages/admin/store'
import qs from 'qs';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      title: 'GetCode - search homepage'
    }
  },
  {
    path: '/search?k=:keyword',
    name: 'search',
    component: SearchResult,
    meta: {
      title: 'GetCode Search Results'
    }
  },
  {
    path: '/admin',
    name: 'admin',
    component: KeywordList,
    meta: {
      title: 'GetCode - admin',
      requiresAuth: true
    }
  },
  {
    path: '/admin/login',
    name: 'login',
    component: Login,
    meta: {
      title: 'GetCode - admin login'
    }
  },
  {
    path: '/admin/list/search-data',
    name: 'search-data-list',
    component: DataList,
    meta: {
      title: 'GetCode - admin search data list',
      requiresAuth: true
    }
  },
  {
    path: '/admin/create',
    name: 'create',
    component: Create,
    meta: {
      title: 'GetCode - admin create search data',
      requiresAuth: true
    }
  },
  {
    path: '/admin/update',
    name: 'update',
    component: Update,
    meta: {
      title: 'GetCode - admin update search data',
      requiresAuth: true
    }
  },
  {
    path: '/information',
    name: 'infor',
    component: Infor,
    meta: {
      title: 'GetCode - information'
    }
  }

  // { path: '*', redirect: '/' }
]

const router = new VueRouter({
  mode: 'history',
  stringifyQuery  : query => {
    let result = qs.stringify(query, { format: 'RFC1738' })
    return result ? ('?' + result) : ''
  },
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
