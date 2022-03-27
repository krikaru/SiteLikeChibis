<template>
    <v-container fluid>
        <v-layout align-center justify-center row>
            <v-flex xs6>
                <v-card>
                    <v-card-text>
                        <h1 align="center">Регистрация</h1>
                    </v-card-text>

                    <v-form
                            ref="registrationForm"
                            v-model="valid"
                    >
                        <v-container>
                            <v-layout column>
                                <v-flex>
                                    <v-text-field
                                            v-model="user.username"
                                            :rules="usernameRules"
                                            label="Логин"
                                            :error-messages="errors ? findErrorMessage('username') : ''"
                                            @click="errors ? errors = null : ''"
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="user.name"
                                            label="Ваше имя"
                                            :rules="nameRules"
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="user.password"
                                            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                                            :rules="passwordRules"
                                            :type="showPassword ? 'text' : 'password'"
                                            label="Пароль"
                                            hint="At least 8 characters"
                                            counter
                                            @click:append="showPassword = !showPassword"
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="user.confirmPassword"
                                            :append-icon="showConfirmPassword ? 'mdi-eye' : 'mdi-eye-off'"
                                            :rules="confirmPasswordRules"
                                            :type="showConfirmPassword ? 'text' : 'password'"
                                            label="Подтвердите пароль"
                                            hint="At least 8 characters"
                                            counter
                                            @click:append="showConfirmPassword = !showConfirmPassword"
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="user.email"
                                            label="E-mail"
                                            :rules="emailRules"
                                            hint="На указанный e-mail будет выслана ссылка для подтверждения регистрации"
                                            :error-messages="errors ? findErrorMessage('email') : ''"
                                            @click="errors ? errors = null : ''"
                                    ></v-text-field>
                                </v-flex>

                                <v-btn class="mt-6"
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
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {

        data() {
            return {
                errors: null,
                valid: true,
                showPassword: false,
                showConfirmPassword: false,
                user: {
                    username: '',
                    name: '',
                    password: '',
                    confirmPassword: '',
                    email: '',
                },

                usernameRules: [
                    username => !!username || 'Введите логин!',
                    username => username.length >= 3 && username.length <=12 || 'Логин должен быть не меньше 3 и не больше 12 символов',
                    username => this.checkUsername(username) || 'В логине должны быть только латинские буквы и цифры'
                ],

                nameRules: [
                    name => !!name || 'Введите своё имя!',
                    name => name.length <= 25 || 'Имя должно быть не больше 25 символов',
                    name => /^[a-zA-Zа-яА-ЯёЁ -]+$/.test(name) || 'Имя должно содержать только буквы'
                ],

                passwordRules: [
                    password => !!password || 'Введите пароль!',
                    password => password.length <= 15 || 'Пароль должен быть не больше 25 символов',
                    password => password.length >= 6 || 'Пароль должен быть не меньше 6 символов',
                    password => /^\S+$/.test(password) || 'В пароле не должно быть пробелов'
                ],

                confirmPasswordRules: [
                    pas2 => !!pas2 || 'Введите повторный пароль!',
                    pas2 => this.user.password === pas2 || 'Пароли не совпадают'
                ],

                emailRules: [
                    email => !!email || 'Введите email!',
                    email => this.checkEmail(email) || 'Неверный формат'
                ]
            }
        },

        methods: {
            ...mapActions(['addUserAction']),
            async save() {
                let result = await this.addUserAction(this.user)

                if (result) {
                    this.errors = result
                } else {
                    this.$refs.registrationForm.reset()
                    this.errors = null
                    await this.$router.push('login')
                }
            },

            findErrorMessage(fieldName) {
                let error = this.errors.find(error => error.fieldName === fieldName)
                if (error) {
                    return error.message
                } else {
                    return ''
                }
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