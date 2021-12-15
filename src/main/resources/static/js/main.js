import Vue from 'vue'
import Vuetify from "vuetify";
import '@babel/polyfill'
// import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import 'vuetify/dist/vuetify.min.css'


Vue.use(Vuetify)

var app = new Vue({
    el: '#app',
    store,
    router,
    vuetify: new Vuetify(),
    render: a => a(App)
})