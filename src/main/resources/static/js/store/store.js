import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'
import newsApi from 'api/news'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        news: JSON.parse(rowNews),
        principal: JSON.parse(rowPrincipal),
        registrationForm: {
            errors: null,
            user: null
        },
        loginError: null,
        updateError: null,
    },

    getters: {
        //если меседжей нет, то возвращаем пустой массив
        sortedNews: state => (state.news || []).sort((a, b) => -(a.id - b.id))
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

        likeMutation(state, news) {
            state.news.find(newsUnit => newsUnit.id === news.newsId).likes = news.data.likes
        },

        logoutMutation(state) {
            state.principal = null
        },

        addNewsMutation(state, news) {
            state.news.push(news)
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

        async likeAction({commit, state}, newsId) {
            let status = 200
            try {
                const result = await newsApi.like(newsId)
                const data = await result.json()
                await commit('likeMutation', { data, newsId })
            }catch (e) {
                status = e.status
            }
            return status
        },

        async deleteProfileAction({commit, state}, deletedId) {
            const result = await userApi.delete(deletedId)
            return result
        },

        async logoutAction({ commit, state }) {
            await userApi.logout()
            await commit('logoutMutation')
        },

        async addNewsAction({commit, state}, news) {

            try {
                const result = await newsApi.add(news)
                const data = await result.json()
                await commit('addNewsMutation', data.updatedEntity)
                return data.errors
            } catch (e) {
                return e.body.errors
            }

        }

    }
})