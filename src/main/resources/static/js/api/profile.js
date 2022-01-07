import Vue from 'vue'

// const profile = Vue.resource('/profile')
const userpic = Vue.resource('/profile/userpic')

export default {
    addUserpic: pic => userpic.save({}, pic)
}