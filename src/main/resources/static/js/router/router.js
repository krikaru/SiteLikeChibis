import Vue from 'vue'
import VueRouter from 'vue-router'
import NewsPage from "pages/NewsPage.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: NewsPage}
]

export default new VueRouter({
    routes
})