<template>
    <v-main style="padding: 150px 0px 0px">
        <v-layout align-center justify-space-around column>

            <login-form></login-form>

            <span class="mt-6">
                Если у вас нет аккаунта вы можете
                <router-link to="/registration">зарегистрироваться</router-link>
            </span>




        </v-layout>
    </v-main>
</template>

<script>
    import LoginForm from 'components/LoginForm.vue';
    export default {
        name: "LoginPage",
        components: {LoginForm}
    }
</script>

<style scoped>

</style>