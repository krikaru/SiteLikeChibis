import Vue from 'vue'

const login = Vue.resource('/login')
const logout = Vue.resource('/logout')


export default {
    add: user => Vue.http.post('/user/registration', user),
    updateUserpic: updatedUser => Vue.http.put(`/user/${updatedUser.id}/userpic`, updatedUser.userpic),
    login: loginForm => login.save({}, loginForm),
    logout: () => logout.save({}),
    updateUser: updatedUserInfo => Vue.http.put(`/user/${updatedUserInfo.updatedUser.id}/${updatedUserInfo.propName}`, updatedUserInfo.updatedUser),
    delete: (deletedId) => Vue.http.delete(`/user/${deletedId}`)

}