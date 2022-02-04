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

        <v-layout fill-height>
            <v-flex xs3>
                <left-side-bar></left-side-bar>
            </v-flex>

            <v-flex xs6>
                <navigation-button></navigation-button>
            </v-flex>

            <v-flex xs3>
                <right-side-bar :principal="principal"></right-side-bar>
            </v-flex>
        </v-layout>
    </v-app-bar>
</template>

<script>
    import NavigationButton from "./NavigationButton.vue";
    import LeftSideBar from "./LeftSideBar.vue";
    import RightSideBar from "./RightSideBar.vue";
    import { mapState } from "vuex";
    export default {
        name: "AppBar",
        components: {RightSideBar, LeftSideBar, NavigationButton},
        computed: {
            ...mapState(['principal']),
        },
    }
</script>

<style scoped>

</style>