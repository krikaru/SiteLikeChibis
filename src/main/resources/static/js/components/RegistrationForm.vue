<template>
    <v-layout align-start justify-center row>
        <v-flex xs6>
            <v-card>
                <v-card-text>
                    <h1 align="center">Регистрация</h1>
                </v-card-text>
                <v-form
                        ref="form"
                        v-model="valid"
                >
                    <v-container>
                        <v-layout column>
                            <v-flex>
                                <v-text-field
                                        v-model="username"
                                        :rules="usernameRules"
                                        label="Логин"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="name"
                                        label="Ваше имя"
                                        :rules="nameRules"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="password"
                                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                        :rules="passwordRules"
                                        :type="show1 ? 'text' : 'password'"
                                        label="Пароль"
                                        hint="At least 8 characters"
                                        counter
                                        @click:append="show1 = !show1"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="email"
                                        label="E-mail"
                                        :rules="emailRules"
                                        required
                                ></v-text-field>
                            </v-flex>
                            <v-btn
                                    :disabled="!valid"
                                    @click="save" >
                                Зарегистрироваться
                            </v-btn>
                        </v-layout>
                    </v-container>
                </v-form>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {

        data() {
            return {
                valid: true,
                show1: false,
                username: '',
                usernameRules: [
                    username => !!username || 'Введите логин!',
                    username => username.length >= 2 && username.length <=8 || 'Логин должен быть не меньше 2 и не больше 8 символов',
                    username => this.checkUsername(username) || 'В логине должны быть только латинские буквы и цифры'
                ],
                name: '',
                nameRules: [
                    name => !!name || 'Введите своё имя!',
                    name => name.length <= 25 || 'Имя должно быть не больше 25 символов',
                    name => /^[a-zа-яё -]+$/i.test(name) || 'Имя должно содержать только буквы'
                ],
                password: '',
                passwordRules: [
                    password => !!password || 'Введите пароль!',
                    password => password.length <= 15 || 'Пароль должен быть не больше 25 символов',
                    password => password.length >= 6 || 'Пароль должен быть не меньше 6 символов',
                    password => /^\S+$/.test(password) || 'В пароле не должно быть пробелов'
                ],
                email: '',
                emailRules: [
                    email => !!email || 'Введите email!',
                    email => this.checkEmail(email) || 'Неверный формат'
                ]
            }
        },
        watch: {
            username: function (newUsername) {
                this.username = newUsername
            },
            name: function (newName) {
                this.name = newName
            },
            password: function (newPassword) {
                this.password = newPassword
            },
            email: function (newEmail) {
                this.email = newEmail
            },

        },
        methods: {
            ...mapActions(['addUserAction']),
            save() {
                const user = {
                    username: this.username,
                    name: this.name,
                    password: this.password,
                    email: this.email
                }
                this.addUserAction(user)

                this.username = ''
                this.name = ''
                this.password = ''
                this.email = ''

                window.location.href = 'http://localhost:9000/auth'
                // this.$router.push('auth')
            },

            checkUsername(username) {
                const regExpUsername = /^[a-zA-Z1-9]+$/
                return regExpUsername.test(String(username))
            },

            checkEmail(email) {
                const regExpEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return regExpEmail.test(String(email).toLowerCase());
            }
        }

    }
</script>

<style scoped>

</style>