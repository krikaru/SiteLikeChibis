import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        news,
    },

    mutations: {
    },

    actions: {
        async addUserAction({commit, state}, user) {
            await userApi.add(user)
        }
    }
})