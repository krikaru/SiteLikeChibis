import Vue from 'vue'

const users = Vue.resource('/user')

export default {
    add: user => users.save({}, user)
}