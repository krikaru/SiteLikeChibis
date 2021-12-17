<template>
    <v-app>
        <app-bar></app-bar>
        <v-main>
            <registration-form></registration-form>
        </v-main>
    </v-app>
</template>

<script>
    import AppBar from 'components/AppBar.vue'
    import RegistrationForm from 'components/RegistrationForm.vue'
    export default {
        components: { AppBar, RegistrationForm }
    }
</script>

<style scoped>

</style>