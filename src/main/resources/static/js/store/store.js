import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        news,
        username,
        registrationForm: {
            errors: null,
            user: null
        }
    },

    mutations: {
        addUserMutation(state, form) {
            state.registrationForm.errors = form.errors
            state.registrationForm.user = form.user
        }
    },

    actions: {
        async addUserAction({commit, state}, registrationForm) {
            const result = await userApi.add(registrationForm)
            const data = await result.json()
            await commit('addUserMutation', data)
        }
    }
})