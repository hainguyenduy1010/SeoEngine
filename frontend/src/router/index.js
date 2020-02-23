import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../pages/home/Home.vue'
import Search from '../pages/search-result/SearchResult.vue'

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
  next();
});

export default router
