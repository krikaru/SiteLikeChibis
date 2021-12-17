<template>
    <v-app-bar
            app
            shrink-on-scroll
            src="https://picsum.photos/1920/1080?random"
    >
        <template v-slot:img="{ props }">
            <v-img
                    v-bind="props"
                    gradient="to top right, rgba(19,84,122,.5), rgba(128,208,199,.8)"
            ></v-img>
        </template>

        <v-app-bar-nav-icon></v-app-bar-nav-icon>

        <v-toolbar-title>Title</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn>
            <router-link to="/registration">
                Registration
            </router-link>
        </v-btn>

    </v-app-bar>
</template>

<script>
    export default {
        name: "AppBar"
    }
</script>

<style scoped>

</style>