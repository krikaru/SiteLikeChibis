<template>
    <v-app>
        <v-card class="overflow-hidden">
            <v-app-bar
                    elevation="8"
                    hide-on-scroll
                    scroll-target="#scrolling-techniques-4"
                    prominent
                    src="https://picsum.photos/1920/1080?random"
            >
                <template v-slot:img="{ props }">
                    <v-img
                            v-bind="props"
                            gradient="to top right, rgba(19,84,122,.5), rgba(128,208,199,.8)"
                    ></v-img>
                </template>
                <v-toolbar-title>ООО ЧИБИС</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-app-bar>
        </v-card>

        <v-container>
            <v-layout align-space-around justify-center column>
                <v-card class="ma-4" v-for="oneNews in news"
                        :key="oneNews.id"
                >
                    <v-card-title primary-title>
                        <div>
                            <h3 class="headline mb-0">{{ oneNews.head }}</h3>
                            <div> {{ oneNews.text }} </div>
                        </div>
                    </v-card-title>
                </v-card>
            </v-layout>
        </v-container>
    </v-app>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        computed: mapState(['news']),
    }
</script>

<style scoped>

</style>