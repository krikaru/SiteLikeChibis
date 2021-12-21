<template>
        <v-main style="padding: 150px 0px 0px">
            <registration-form></registration-form>
        </v-main>
</template>

<script>
    import AppBar from 'components/appBar/AppBar.vue'
    import RegistrationForm from 'components/RegistrationForm.vue'
    export default {
        components: { AppBar, RegistrationForm }
    }
</script>

<style scoped>

</style>