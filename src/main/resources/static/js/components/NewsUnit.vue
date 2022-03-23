<template>
    <div>
        <v-card class="ma-4" v-for="oneNews in sortedNews"
                :key="oneNews.id"
        >
            <div class="ma-6">
                <v-card-title>
                    <span style="word-break: normal">{{ oneNews.head }}</span>
                </v-card-title>
                <v-card-subtitle>
                    <div>{{ oneNews.creationDate }}. Автор: {{ oneNews.author.name }} </div>
                </v-card-subtitle>
                <v-card-text class="d-block text-truncate">
                    <span style="word-break: normal">{{ oneNews.text }}</span>
                </v-card-text>
                <v-card-actions>
                    <v-btn
                            color="deep-purple accent-4"
                            @click="pushToLink(`/news/${oneNews.id}`)"
                    >
                        Читать полностью
                    </v-btn>
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
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    export default {

        computed: {
            ...mapGetters(['sortedNews']),
        },

        methods: {
            pushToLink(link) {
                this.$router.push(link)
            }
        }
    }
</script>

<style scoped>

</style>