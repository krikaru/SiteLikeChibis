<template>
    <v-layout align-start justify-end fill-height>
        <v-flex class="text-end" style="height: 36px">
            <span class="body-1">
                Гость
            </span>
        </v-flex>
        <v-flex xs2>
            <v-btn icon >
                <router-link to="/registration">
                    <span class="material-icons">
                        account_circle
                    </span>
                </router-link>
            </v-btn>
        </v-flex>
    </v-layout>
</template>

<script>
    export default {
        name: "RightSideBar"
    }
</script>

<style scoped>

</style>