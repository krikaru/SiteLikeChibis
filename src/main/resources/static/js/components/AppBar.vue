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

        <v-toolbar-title style="cursor: pointer" v-if="this.$route.path !== '/'" @click="$router.push('/')" >
            Chibis
        </v-toolbar-title>
        <v-toolbar-title style="cursor: pointer" v-else>
            Chibis
        </v-toolbar-title>

        <v-btn text>
            registration
        </v-btn>
        <v-spacer></v-spacer>

        <v-btn icon >
            <router-link to="/registration">
                <span class="material-icons">
                        account_circle
                    </span>
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