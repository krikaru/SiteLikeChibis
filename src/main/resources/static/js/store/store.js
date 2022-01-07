import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'
import profileApi from 'api/profile'
import xhr from "vue-resource/src/http/client/xhr";

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        rowNews,
        principal: JSON.parse(rowPrincipal),
        registrationForm: {
            errors: null,
            user: null
        },
        loginError: null
    },

    mutations: {
        addUserMutation(state, form) {
            state.registrationForm.errors = form.errors
            state.registrationForm.user = form.user
        },

        loginErrorMutation(state, value) {
            state.loginError = value
        },
        loginMutation(state, loginForm) {
            console.log("sef")
            console.log(loginForm)
            state.rowPrincipal = loginForm
        }
    },

    actions: {
        async addUserAction({commit, state}, registrationForm) {
            const result = await userApi.add(registrationForm)
            const data = await result.json()
            await commit('addUserMutation', data)
        },

        async addUserpicAction({commit, state}, userpic) {
            const result = await profileApi.addUserpic(userpic)
            const data = await result.json()
            return data;
        },

        async loginAction({commit, state}, loginForm) {

            try{
                const result = await userApi.login(loginForm)
                state.loginError = null
            } catch (e) {

                console.log(e.body.error)
                if (e.body.error ==='Bad credentials') {
                    state.loginError = 'Неверный пароль.'
                } else if (e.body.error === 'User is disabled') {
                    state.loginError = 'Аккаунт не подтвержден. Для активации необходимо пройти по ссылке в письме.'
                } else {
                    state.loginError = 'Неизвестная ошибка. Попробуйте зайти позже.'
                }

            }
        },

        async logoutAction({commit, state}) {
            await userApi.logout()
        }
    }
})