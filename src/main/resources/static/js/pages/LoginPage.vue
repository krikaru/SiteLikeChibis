<template>
    <v-main style="padding: 150px 0px 0px">
        <v-layout align-center justify-space-around row>
            <v-flex xs6>
                <v-alert v-if="this.activatedStatus" :type=this.typeAlert>
                    {{this.activatedMessage()}}
                </v-alert>
            </v-flex>

            <v-flex xs12>
                <login-form></login-form>
            </v-flex>

            <v-flex xs6 class="mt-4 text-center">
                <span>
                    Если у вас нет аккаунта вы можете
                    <router-link to="/registration">зарегистрироваться</router-link>
                </span>
            </v-flex>


        </v-layout>
    </v-main>
</template>

<script>
    import LoginForm from 'components/LoginForm.vue';
    export default {
        name: "LoginPage",
        components: { LoginForm },
        data() {
            return {
                activatedStatus: this.$route.params.activated,
                typeAlert: null
            }
        },
        methods: {
            activatedMessage() {
                if (this.activatedStatus === 'success') {
                    this.typeAlert = 'success'
                    return 'Почта подтверждена.'
                } else if (this.activatedStatus === 'failure') {
                    this.typeAlert = 'warning'
                    return 'Активационный код не найден или уже активирован!'
                } else {
                    this.typeAlert = null
                }
            }
        },
        beforeRouteLeave(to, from, next) {
            this.activatedStatus = null
            next()
        }
    }
</script>

<style scoped>

</style>