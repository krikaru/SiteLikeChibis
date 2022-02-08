import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        rowNews,
        principal: JSON.parse(rowPrincipal),
        registrationForm: {
            errors: null,
            user: null
        },
        loginError: null,
        updateError: null,
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
            state.rowPrincipal = loginForm
        },
        updateProfileMutation(state, updatedUserInfo) {
            if (updatedUserInfo.errors === null) {
                switch (updatedUserInfo.nameAttribute) {
                    case 'name':
                        state.principal.name = updatedUserInfo.updatedUser.name
                        break
                    case 'password':
                        state.principal.password = updatedUserInfo.updatedUser.password
                        break
                    case 'email':
                        state.principal.email = updatedUserInfo.updatedUser.email
                        break
                    case 'userpic':
                        state.principal.userpic = updatedUserInfo.updatedUser.userpic
                }
            } else {
                state.updateError = updatedUserInfo.errors
            }
        },
        logoutMutation(state) {
            state.principal = null
        }
    },

    actions: {
        async addUserAction({commit, state}, registrationForm) {
            const result = await userApi.add(registrationForm)
            const data = await result.json()
            await commit('addUserMutation', data)
        },

        async addUserpicAction({commit, state}, userpic) {
            const result = await userApi.addUserpic(userpic)
            const data = await result.json()
            return data;
        },

        async loginAction({commit, state}, loginForm) {

            try{
                const result = await userApi.login(loginForm)
                state.loginError = null
            } catch (e) {
                if (e.body.error ==='Bad credentials') {
                    state.loginError = 'Неверный пароль.'
                } else if (e.body.error === 'User is disabled') {
                    state.loginError = 'Аккаунт не подтвержден. Для активации необходимо пройти по ссылке в письме.'
                } else {
                    state.loginError = 'Неизвестная ошибка. Попробуйте зайти позже.'
                }

            }
        },

        async updateProfileAction({commit, state}, updatedUserInfo) {
            const result = await userApi.updateUser(updatedUserInfo)
            const data = await result.json()
            await commit('updateProfileMutation', data)
            return data
        },

        async updateUserpicAction({commit, state}, updateInfo) {
            const result = await userApi.updateUserpic(updateInfo)
            const data = await result.json()
            await commit('updateProfileMutation', data)
            return data
        },

        async deleteProfileAction({commit, state}, deletedId) {
            const result = await userApi.delete(deletedId)
            return result
        },

        async logoutAction({ commit, state }) {
            await userApi.logout()
            await commit('logoutMutation')
        }
    }
})