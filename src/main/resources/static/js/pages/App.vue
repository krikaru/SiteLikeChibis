<template>
    <v-app>
        <app-bar></app-bar>
        <router-view></router-view>
    </v-app>
</template>

<script>
    import AppBar from "components/appBar/AppBar.vue"
    export default {
        components: {AppBar}
    }
</script>

<style scoped>

</style>