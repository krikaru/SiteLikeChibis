import Vue from 'vue'

const users = Vue.resource('/user')
const login = Vue.resource('/login')
const logout = Vue.resource('/logout')

export default {
    add: user => users.save({}, user),
    login: loginForm => login.save({}, loginForm),
    logout: () => logout.save({})
}