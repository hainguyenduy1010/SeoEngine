import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../pages/home/Home.vue'
import SearchResult from '../pages/search-result/SearchResult.vue'
import Login from '../pages/admin/login/Login.vue'
import DataList from '../pages/admin/data-list/DataList.vue'
import Create from '../pages/admin/create/Create.vue'
import Update from '../pages/admin/update/Update.vue'

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
    component: SearchResult,
    meta: {
      title: 'Search Results'
    }
  },
  {
    path: '/admin',
    name: 'admin',
    component: DataList,
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
  },
  {
    path: '/admin/create',
    name: 'create',
    component: Create,
    meta: {
      title: 'Admin create search data'
    }
  },
  {
    path: '/admin/update',
    name: 'update',
    component: Update,
    meta: {
      title: 'Admin update search data'
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
