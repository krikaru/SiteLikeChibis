<template>
    <v-main>
        <v-container fluid>
            <v-layout row>
                <v-flex xs3>
                    <news-sorter></news-sorter>
                </v-flex>
                <v-flex xs9>
                    <news-unit v-for="newsUnit in sortedNews" :key="newsUnit.id" :newsUnit="newsUnit"></news-unit>
                </v-flex>
            </v-layout>
        </v-container>
    </v-main>

</template>

<script>
    import {mapGetters} from 'vuex'
    import NewsUnit from 'components/NewsUnit.vue'
    import NewsSorter from 'components/NewsSorter.vue'
    export default {
        components: {NewsSorter, NewsUnit},
        computed: mapGetters(['sortedNews'])
    }
</script>

<style scoped>

</style>