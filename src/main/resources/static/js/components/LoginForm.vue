<template>
    <v-container fluid>
        <v-layout align-center justify-center row>
            <v-flex xs6>

                <v-alert v-if="this.$route.query.error" type="error">
                    Некорректный логин или пароль.
                </v-alert>

                <v-card>
                    <v-card-text>
                        <h1 align="center">Вход</h1>
                    </v-card-text>

                    <v-form
                            method="post" action="/login"
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
                                    >
                                    </v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            :rules="[password => !!password || 'Введите пароль!']"
                                            label="Пароль"
                                            name="password"
                                            type="password"
                                    >
                                    </v-text-field>
                                </v-flex>

                                <v-btn
                                        :disabled="!valid"
                                        type="submit"
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
    export default {
        name: "LoginForm",
        data() {
            return {
                valid: false,
            }
        },
    }
</script>

<style scoped>

</style>