import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'
import profileApi from 'api/profile'
import xhr from "vue-resource/src/http/client/xhr";

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        rowNews,
        rowPrincipal,
        registrationForm: {
            errors: null,
            user: null
        },
        loginError: null
    },

    getters: {
        principal: state => {
            return JSON.parse(state.rowPrincipal)
        },

        news: state => {
            return JSON.parse(state.rowNews)
        }
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
                await userApi.login(loginForm)
            } catch (e) {
                if (e.status === 404) {
                    state.loginError = 'Некорректный логин или пароль.'
                } else if (e.status === 200) {
                    state.loginError = null
                } else {
                    state.loginError = 'Неизвестная ошибка. Попробуйте войти позже.'
                }
            }
        },

        async logoutAction({commit, state}) {
            await userApi.logout()
        }
    }
})