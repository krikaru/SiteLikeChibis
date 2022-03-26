<template>
    <v-main>
        <v-layout>
            <v-flex xs3>
                <news-sorter></news-sorter>
            </v-flex>
            <v-flex xs9>
                <v-card class="ma-6">
                    <v-expansion-panels focusable>
                        <v-expansion-panel>
                            <v-expansion-panel-header
                                    style="background-color: lightskyblue"
                            >
                                <span class="text-h6">Cоздать новость</span>
                            </v-expansion-panel-header>
                            <v-expansion-panel-content>
                                <news-form></news-form>
                            </v-expansion-panel-content>
                        </v-expansion-panel>
                    </v-expansion-panels>
                </v-card>
                <v-card class="ma-6">
                    <v-card-title style="background-color: lightskyblue">
                        <span>Опубликованные сообщения</span>
                    </v-card-title>
                    <v-divider></v-divider>
                    <v-card-text>У вас пока нет сообщений</v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-main>
</template>

<script>
    import NewsSorter from "components/NewsSorter.vue"

    import NewsForm from "components/NewsForm.vue";
    export default {
        data() {
            return {

            }
        },
        name: "ControlNewsPage",
        components: {NewsForm, NewsSorter },

    }
</script>

<style scoped>

</style>