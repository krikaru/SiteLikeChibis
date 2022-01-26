import Vue from 'vue'

const profile = Vue.resource('/profile/settings{/propName}')
const userpic = Vue.resource('/profile/settings/userpic')

export default {
    addUserpic: (pic) => userpic.save({}, pic),
    updateProp: (propName, newValue) => profile.save({propName}, newValue)
}