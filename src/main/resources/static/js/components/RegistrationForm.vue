<template>
    <v-container fluid>
        <v-layout align-center justify-center row>
            <v-flex xs6>
                <v-alert v-if="errors" type="error">
                    {{this.errors}}
                </v-alert>
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
                                            v-model="username"
                                            :rules="usernameRules"
                                            label="Логин"
                                            required
                                            validate-on-blur
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="name"
                                            label="Ваше имя"
                                            :rules="nameRules"
                                            validate-on-blur
                                            required
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="password"
                                            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                                            :rules="passwordRules"
                                            :type="showPassword ? 'text' : 'password'"
                                            label="Пароль"
                                            hint="At least 8 characters"
                                            counter
                                            @click:append="showPassword = !showPassword"
                                            validate-on-blur
                                            required
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            ref="confirmPasswordRef"
                                            v-model="confirmPassword"
                                            :append-icon="showConfirmPassword ? 'mdi-eye' : 'mdi-eye-off'"
                                            :rules="confirmPasswordRules"
                                            :type="showConfirmPassword ? 'text' : 'password'"
                                            label="Подтвердите пароль"
                                            hint="At least 8 characters"
                                            counter
                                            @click:append="showConfirmPassword = !showConfirmPassword"
                                            validate-on-blur
                                            required
                                    ></v-text-field>
                                </v-flex>

                                <v-flex>
                                    <v-text-field
                                            v-model="email"
                                            label="E-mail"
                                            :rules="emailRules"
                                            hint="На указанный e-mail будет выслана ссылка для подтверждения регистрации"
                                            validate-on-blur
                                            required
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
    import { mapState, mapActions } from 'vuex'
    export default {

        computed: mapState(['registrationForm']),

        data() {
            return {
                errors: null,
                valid: true,
                showPassword: false,
                showConfirmPassword: false,
                username: '',
                usernameRules: [
                    username => !!username || 'Введите логин!',
                    username => username.length >= 3 && username.length <=12 || 'Логин должен быть не меньше 3 и не больше 12 символов',
                    username => this.checkUsername(username) || 'В логине должны быть только латинские буквы и цифры'
                ],
                name: '',
                nameRules: [
                    name => !!name || 'Введите своё имя!',
                    name => name.length <= 25 || 'Имя должно быть не больше 25 символов',
                    name => /^[a-zA-Zа-яА-ЯёЁ -]+$/.test(name) || 'Имя должно содержать только буквы'
                ],
                password: '',
                passwordRules: [
                    password => !!password || 'Введите пароль!',
                    password => password.length <= 15 || 'Пароль должен быть не больше 25 символов',
                    password => password.length >= 6 || 'Пароль должен быть не меньше 6 символов',
                    password => /^\S+$/.test(password) || 'В пароле не должно быть пробелов'
                ],
                confirmPassword: '',
                confirmPasswordRules: [
                    pas2 => !!pas2 || 'Введите повторный пароль!',
                    pas2 => this.password === pas2 || 'Пароли не совпадают'
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
                this.$refs.confirmPasswordRef.validate();
            },
            email: function (newEmail) {
                this.email = newEmail
            },

        },
        methods: {
            ...mapActions(['addUserAction']),
            async save() {
                const registrationForm = {
                    user : {
                            username: this.username,
                            name: this.name,
                            password: this.password,
                            email: this.email
                        },
                    confirmPassword: this.confirmPassword
                }

                console.log('registration form . vue')
                console.log(registrationForm.confirmedPassword)
                console.log(registrationForm)
                await this.addUserAction(registrationForm)


                const form = this.registrationForm


                if (form.user.id !== null) {
                    await this.$router.push('login')
                    this.username = ''
                    this.name = ''
                    this.password = ''
                    this.confirmPassword = ''
                    this.email = ''
                    this.errors = null
                } else {
                    this.errors = form.errors.usernameError || form.errors.uniqueEmailError
                    this.confirmPassword = ''
                    if (form.errors.uniqueEmailError) {
                        this.email = ''
                    } else if (form.errors.usernameError)
                        this.username = ''
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