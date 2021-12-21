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
        <v-toolbar-title
                style="cursor: pointer"
                class="flex text-center fill-height"
        >
            <v-layout fill-height>
                <v-row
                        align="center"
                        justify="center"
                        @click="$router.push('/')"
                        v-if="this.$route.path !== '/'"
                >
                    ЧИБИС
                </v-row>
                <v-row align="center" justify="center" v-else>
                    ЧИБИС
                </v-row>
            </v-layout>
        </v-toolbar-title>

        <v-layout fill-height>
            <v-row align="end" xs9 style="margin: 0 0 13px 0">
                <navigation-button></navigation-button>
            </v-row>
        </v-layout>

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
    import NavigationButton from "./NavigationButton.vue";
    export default {
        name: "AppBar",
        components: {NavigationButton}
    }
</script>

<style scoped>

</style>