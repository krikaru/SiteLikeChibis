import Vue from 'vue'

const users = Vue.resource('/user')
const login = Vue.resource('/login')
const logout = Vue.resource('/logout')

const userpic = Vue.resource('/user/userpic')

export default {
    add: user => users.save({}, user),
    addUserpic: (pic) => userpic.save({}, pic),
    login: loginForm => login.save({}, loginForm),
    logout: () => logout.save({}),
    updateUser: updatedUserInfo => Vue.http.patch(`/user/${updatedUserInfo.updatedUser.id}`, updatedUserInfo),

}