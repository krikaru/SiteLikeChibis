<template>
    <v-main style="padding: 150px 0px 0px">
        <login-form></login-form>
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