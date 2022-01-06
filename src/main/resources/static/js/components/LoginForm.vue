<template>
    <v-container fluid>
        <v-layout align-center justify-center row>
            <v-flex xs6>

                <v-alert v-if="loginError" type="error">
                    {{this.loginError}}
                </v-alert>

                <v-card>
                    <v-card-text>
                        <h1 align="center">Вход</h1>
                    </v-card-text>

                    <v-form
                            id="loginForm"
                            ref="loginForm"
                            v-model="valid"
                    >
                        <v-container>
                            <v-layout column>
                                <v-flex>
                                    <v-text-field
                                            :rules="[username => !!username || 'Введите логин!']"
                                            label="Логин"
                                            name="username"
                                            v-model="username"
                                    >
                                    </v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            :rules="[password => !!password || 'Введите пароль!']"
                                            label="Пароль"
                                            v-model="password"
                                            type="password"
                                    >
                                    </v-text-field>
                                </v-flex>

                                <v-btn
                                        :disabled="!valid"
                                        @click="login"
                                >
                                    Войти
                                </v-btn>
                            </v-layout>
                        </v-container>
                    </v-form>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>

</template>

<script>
    import { mapState, mapActions } from 'vuex'
    export default {

        name: "LoginForm",
        data() {
            return {
                valid: false,
                username: '',
                password: '',
            }
        },
        computed: mapState(['loginError']),

        methods: {
            ...mapActions(['loginAction']),
            async login() {
                let loginForm = new FormData
                loginForm.append('username', this.username)
                loginForm.append('password', this.password)
                await this.loginAction(loginForm)
                if (!this.loginError) {
                    window.location.href = '/'
                }
            }
        },
    }
</script>

<style scoped>

</style>