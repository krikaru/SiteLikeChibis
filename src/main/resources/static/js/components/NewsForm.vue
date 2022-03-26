<template>
    <div>
        <v-alert
                class="mt-3"
                v-if="typeAlert"
                :type="typeAlert"
        >
            <span>{{updMessage}}</span>
        </v-alert>

        <v-form
                ref="messageForm"
                v-model="valid"
        >
            <v-container>
                <v-textarea

                        v-model="news.head"
                        :rules="headRules"
                        counter
                        rows="1"
                        label="Заголовок новости"
                        auto-grow
                        @click="typeAlert = ''"
                ></v-textarea>
                <v-textarea
                        class="mt-2"
                        v-model="news.text"
                        :rules="textRules"
                        counter
                        outlined
                        label="Текст новости"
                        auto-grow
                        @click="typeAlert = ''"
                ></v-textarea>
                <v-btn color="#87CEFA"
                       :disabled="!valid"
                       @click="submit()"
                >
                    Опубликовать
                </v-btn>
            </v-container>
        </v-form>
    </div>

</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: "NewsForm",
        data() {
            return {
                news: {
                    head: '',
                    text: '',
                },
                typeAlert: '',
                updMessage: null,
                valid: true,
                headRules: [
                    head => !!head || 'Введите заголовок!',
                    head => head.length < 100 || 'Текст заголовка должен быть меньше 100 символов!',
                    head => head.length > 5 || 'Текст заголовка должен быть больше 5 символов!'
                ],
                textRules: [
                    text => !!text || 'Введите текст!',
                    head => head.length < 10000 || 'Текст заголовка должен быть меньше 10000 символов!',
                    head => head.length > 100 || 'Текст заголовка должен быть больше 100 символов!'
                ]
            }
        },

        methods: {
            ...mapActions(['addNewsAction']),
            async submit() {
                let result = await this.addNewsAction(this.news)

                if (!result) {
                    this.updMessage = 'Новость успешно добавлена'
                    this.typeAlert = 'success'
                    this.news.head = ''
                    this.news.text = ''
                    this.$refs.messageForm.resetValidation()
                }
            }
        }
    }
</script>

<style scoped>

</style>