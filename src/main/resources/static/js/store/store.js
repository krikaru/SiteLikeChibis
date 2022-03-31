import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'
import newsApi from 'api/news'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        news: JSON.parse(rowNews),
        principal: JSON.parse(rowPrincipal),
    },

    getters: {
        sortedNews: state => (state.news || []).sort((a, b) => -(a.id - b.id))
    },

    mutations: {

        updateProfileMutation(state, updatedUserInfo) {
            if (!!!updatedUserInfo.updatedUser.password) {
                updatedUserInfo.updatedUser.userpic = state.principal.userpic
                Object.assign(state.principal, updatedUserInfo.updatedUser)
            }
        },

        updateUserpicMutation(state, userpicName) {
            state.principal.userpic = userpicName
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
            try {
                await userApi.add(registrationForm)
                return null
            } catch (e) {
                return e.body.errors
            }
        },


        async loginAction({commit, state}, loginForm) {
            try{
                await userApi.login(loginForm)
                return null
            } catch (e) {
                return e.body.error
            }
        },

        async updateProfileAction({commit, state}, updatedUserInfo) {
            try {
                await userApi.updateUser(updatedUserInfo)
                return null
            } catch (e) {
                return e.body.errors
            }
        },

        async updateUserpicAction({commit, state}, updateInfo) {
            try {
                let result = await userApi.updateUserpic(updateInfo)
                await commit('updateUserpicMutation', result.body.userpicName)
                return null
            } catch (e) {
                return e.body.errors
            }
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
                await commit('addNewsMutation', data.entity)
                return null
            } catch (e) {
                return e.body.errors
            }

        }

    }
})