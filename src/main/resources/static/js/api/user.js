import Vue from 'vue'

const users = Vue.resource('/user')
const login = Vue.resource('/login')
const logout = Vue.resource('/logout')


export default {
    add: user => Vue.http.post('/user/registration', user),
    updateUserpic: updateInfo => Vue.http.post(`/user/${updateInfo.id}/userpic`, updateInfo.userpic),
    login: loginForm => login.save({}, loginForm),
    logout: () => logout.save({}),
    updateUser: updatedUserInfo => Vue.http.patch(`/user/${updatedUserInfo.updatedUser.id}`, updatedUserInfo),
    delete: (deletedId) => Vue.http.delete(`/user/${deletedId}`)
}