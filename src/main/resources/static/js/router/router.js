import Vue from 'vue'
import VueRouter from 'vue-router'
import NewsPage from "pages/NewsPage.vue";
import RegistrationPage from "pages/RegistrationPage.vue";
import LoginPage from "pages/LoginPage.vue";
import ProfilePage from "pages/ProfilePage.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: NewsPage},
    { path: '/registration', component: RegistrationPage},
    { path: '/login/:activated?', component: LoginPage},
    { path: '/profile', component: ProfilePage},
    { path: '*', component: NewsPage}
]

export default new VueRouter({
    mode: 'history',
    routes
})