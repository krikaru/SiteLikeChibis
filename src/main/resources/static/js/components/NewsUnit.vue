<template>

    <v-card class="ma-6 pa-4">
        <v-card-title>
            <span style="word-break: normal">{{ newsUnit.head }}</span>
        </v-card-title>
        <v-card-subtitle>
            <div>{{ newsUnit.creationDate }}. Автор: {{ newsUnit.author.name }} </div>
        </v-card-subtitle>
        <v-card-text :class="$route.path === '/' ? 'd-block text-truncate' : ''">
            <span style="word-break: normal">{{ newsUnit.text }}</span>
        </v-card-text>
        <v-card-actions>
            <v-btn v-if="$route.path === '/'"
                   color="purple"
                   text
                   @click="$router.push(`/news/${newsUnit.id}`)"
            >
                Читать полостью
            </v-btn>
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
                <v-icon>
                    {{isLiked ? 'favorite' : 'favorite_border'}}
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
    </v-card>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    export default {
        props: ["newsUnit"],

        data() {
            return {
                isLiked: false,
                errorLike: false,
                errorMessage: '',
                likeCount: 0,
            }
        },

        computed: mapState(['principal']),

        methods: {
            ...mapActions(['likeAction']),
            async like(newsId) {
                let status = await this.likeAction(newsId)
                if (status === 200) {
                    this.isLiked = !this.isLiked
                    this.likeCount = this.newsUnit.likes.length
                }
                if (status === 403) {
                    this.errorLike = true
                    this.errorMessage = 'Чтобы поставить лайк нужно авторизоваться!'
                }
            }
        },

        mounted() {
            this.likeCount = this.newsUnit.likes.length
            this.isLiked = this.principal ? this.newsUnit.likes.find(usr => usr.id === this.principal.id) : false
        }
    }
</script>

<style scoped>

</style>