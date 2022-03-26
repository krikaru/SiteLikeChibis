import Vue from 'vue'
import VueRouter from 'vue-router'
import NewsPage from "pages/NewsPage.vue";
import RegistrationPage from "pages/RegistrationPage.vue";
import LoginPage from "pages/LoginPage.vue";
import ProfilePage from "pages/ProfilePage.vue";
import SettingsPage from "pages/SettingsPage.vue";
import MainPage from "pages/MainPage.vue";
import ControlNewsPage from "pages/ControlNewsPage.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: MainPage },
    { path: '/controlnews', component: ControlNewsPage},
    { path: '/news/:id', component: NewsPage },
    { path: '/news/:id/like', component: NewsPage },
    { path: '/registration', component: RegistrationPage },
    { path: '/login/:activated?', component: LoginPage },
    { path: '/profile/:id', component: ProfilePage,
        children: [
            { path: 'settings', component: SettingsPage },
        ]},
    { path: '*', component: MainPage}
]

export default new VueRouter({
    mode: 'history',
    routes
})