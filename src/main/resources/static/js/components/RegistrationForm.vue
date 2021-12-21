<template>
    <v-layout align-start justify-center row>
        <v-flex xs6>
            <v-card>
                <v-card-text>
                    <h1 align="center">Регистрация</h1>
                </v-card-text>
                <v-form>
                    <v-container>
                        <v-layout column>
                            <v-flex>
                                <v-text-field
                                        v-model="username"
                                        :rules="[rules.required, rules.min]"
                                        label="Логин"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="name"
                                        label="Ваше имя"
                                        :rules="[rules.required, rules.range]"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="password"
                                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                        :rules="[rules.required, rules.min]"
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
                                        :rules="[rules.required, rules.email]"
                                        required
                                ></v-text-field>
                            </v-flex>
                            <v-btn @click="save" >
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
                show1: false,
                username: '',
                name: '',
                password: '',
                email: '',
                rules: {
                    required: value => !!value || 'Необходимо заполнить.',
                    min: v => v.length >= 2 || 'Min 8 characters',
                    range: username => username.length >=3 && username.length<=10 || 'Логин должен быть больше 3, но меньше 10 знаков',
                    email: email => this.checkEmail(email) || 'Не валидный емэйл'
                },
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

            checkEmail(email) {
                const regExpEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return regExpEmail.test(String(email).toLowerCase());
            }
        }

    }
</script>

<style scoped>

</style>