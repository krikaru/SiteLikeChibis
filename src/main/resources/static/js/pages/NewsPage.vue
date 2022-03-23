<template>
    <v-main>
        <v-layout justify-center>
            <v-flex xs10>
                <v-card class="ma-4">
                    <div class="ma-6">
                        <v-card-title>
                            <span style="word-break: normal">{{ newsUnit.head }}</span>
                        </v-card-title>
                        <v-card-subtitle>
                            <div>{{ newsUnit.creationDate }}. Автор: {{ newsUnit.author.name }} </div>
                        </v-card-subtitle>
                        <v-card-text>
                            <span style="word-break: normal">{{ newsUnit.text }}</span>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                                <v-icon>
                                    chat_bubble_outline
                                </v-icon>
                            </v-btn>
                            <v-btn icon>
                                <v-icon>
                                    favorite_border
                                </v-icon>
                            </v-btn>
                        </v-card-actions>
                    </div>

                </v-card>
            </v-flex>
        </v-layout>


    </v-main>

</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: "NewsPage",
        data() {
            return {
                newsUnit: {
                    id: 0,
                    head: "",
                    text: "",
                    creationDate: "",
                    author: {
                        id: 0,
                        name: ""
                    }
                }
            }
        },
        computed: mapState(['news']),
        mounted() {
            let id = Number(this.$route.params.id)
            this.newsUnit = this.news.find(news => news.id === id)
        }
    }
</script>

<style scoped>

</style>