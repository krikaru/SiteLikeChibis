import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        news,
        username
    },

    mutations: {
    },

    actions: {
        async addUserAction({commit, state}, user) {
            const result = await userApi.add(user)
            const data = await result.json()

        }
    }
})