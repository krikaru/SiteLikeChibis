<template>
        <v-main style="padding: 150px 0px 0px">
            <registration-form></registration-form>
        </v-main>
</template>

<script>
    import RegistrationForm from 'components/RegistrationForm.vue'
    export default {
        components: { RegistrationForm },
        beforeRouteLeave(to, from, next) {
            this.$store.state.loginError = null
            this.activatedStatus = null
            next()
        }
    }
</script>

<style scoped>

</style>