<template>
    <v-layout align-center justify-center row>
        <v-flex xs6>
            <v-card>
                <v-card-text>
                    <h1 align="center">Registration</h1>
                </v-card-text>
                <v-form>
                    <v-container>
                        <v-layout column>
                            <v-flex>
                                <v-text-field
                                        v-model="username"
                                        label="Login"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="name"
                                        label="Name"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="password"
                                        label="Password"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-flex>
                                <v-text-field
                                        v-model="email"
                                        label="E-mail"
                                        required
                                ></v-text-field>
                            </v-flex>

                            <v-btn icon href="/logout">
                                <v-icon>exit_to_app</v-icon>
                            </v-btn>

                            <v-btn @click="save">
                                Registration
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
                username: '',
                name: '',
                password: '',
                email: ''
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

                this.$router.push('/')
            }
        }

    }
</script>

<style scoped>

</style>