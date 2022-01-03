import Vue from 'vue'
import Vuex from 'vuex'
import userApi from 'api/user'
import json from "vue-resource/src/http/interceptor/json";

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        rowNews,
        rowPrincipal,
        registrationForm: {
            errors: null,
            user: null
        }
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