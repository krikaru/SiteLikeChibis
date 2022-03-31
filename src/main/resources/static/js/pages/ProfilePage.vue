<template>
    <v-main style="padding: 150px 10px 10px">
        <router-view></router-view>
        <v-container v-if="this.$route.path === `/profile/${this.principal.id}`">
            <v-layout align-start justify-space-between row>
                <v-flex xs3>
                    <v-card>
                        <v-img
                                :src="`/img/${this.principal.userpic}`">
                        </v-img>
                        <v-card-title>
                            {{this.principal.name}}
                        </v-card-title>
                        <v-card-text>
                            E-mail: {{this.principal.email}}
                        </v-card-text>
                        <v-divider></v-divider>
                        <v-list-item-group>

                            <v-list-item
                                @click="$router.push(`/profile/${this.principal.id}/settings`)"
                            >
                                <v-list-item-icon>
                                    <v-icon v-text="'settings'"></v-icon>
                                </v-list-item-icon>
                                <v-list-item-title>
                                    Настройка
                                </v-list-item-title>
                            </v-list-item>
                        </v-list-item-group>
                    </v-card>

                </v-flex>
                <v-flex xs8>
                    <v-card>
                        <v-card-title style="background-color: lightskyblue">
                            Ваши заявки
                        </v-card-title>
                        <v-divider></v-divider>
                        <v-card-text>У вас пока нет заявок</v-card-text>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>

    </v-main>

</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: "ProfilePage",

        computed: mapState(['principal']),
    }
</script>

<style scoped>

</style>