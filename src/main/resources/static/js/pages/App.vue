<template>
    <v-app>
        <app-bar></app-bar>
        <v-main>
            <router-view></router-view>
        </v-main>
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