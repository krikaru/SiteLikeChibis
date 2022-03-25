<template>
    <v-main>
        <v-layout justify-center>
            <v-flex xs10>
                <news-unit :newsUnit="findNewsById()"></news-unit>
            </v-flex>
        </v-layout>

    </v-main>

</template>

<script>
    import NewsUnit from "components/NewsUnit.vue"
    import {mapState} from 'vuex'

    export default {
        name: "NewsPage",
        components: { NewsUnit },
        computed: mapState(['news']),
        methods: {
            findNewsById() {
                return this.news.find(unit => unit.id === Number(this.$route.params.id))
            }
        }
    }
</script>

<style scoped>

</style>