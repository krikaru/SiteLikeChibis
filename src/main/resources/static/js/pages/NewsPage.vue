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

                            <v-btn icon
                                   @click="like(newsUnit.id)"
                            >
                                <h3>{{ likeCount }}</h3>
                                <v-icon v-if="isLiked">
                                    favorite
                                </v-icon>
                                <v-icon v-else>
                                    favorite_border
                                </v-icon>
                            </v-btn>

                            <v-snackbar
                                    v-model="errorLike"
                            >
                                {{ errorMessage }}
                                <template v-slot:action="{ attrs }">
                                    <v-btn
                                            color="pink"
                                            text
                                            v-bind="attrs"
                                            @click="errorLike = false"
                                    >
                                        Закрыть
                                    </v-btn>
                                </template>
                            </v-snackbar>

                        </v-card-actions>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>
    </v-main>
</template>

<script>
    import { mapState, mapActions } from 'vuex'
    export default {
        name: "NewsPage",
        data() {
            return {
                isLiked: false,
                errorLike: false,
                errorMessage: '',
                likeCount: 0,
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
        computed: {
            ...mapState(['news', 'principal']),
        },


        methods: {
            ...mapActions(['likeAction']),
            async like(newsId) {
                let status = await this.likeAction(newsId)
                if (status === 200) {
                    this.isLiked = !this.isLiked
                    this.likeCount = this.news.find(news => news.id === this.newsUnit.id).likes.length
                }
                if (status === 403) {
                    this.errorLike = true
                    this.errorMessage = 'Чтобы поставить лайк нужно авторизоваться!'
                }
            }
        },

        mounted() {
            this.newsUnit.id = Number(this.$route.params.id)
            this.newsUnit = this.news.find(news => news.id === this.newsUnit.id)
            this.likeCount = this.newsUnit.likes.size()
            this.isLiked = this.principal ? this.newsUnit.likes.find(usr => usr.id === this.principal.id) : false
        }
    }
</script>

<style scoped>

</style>