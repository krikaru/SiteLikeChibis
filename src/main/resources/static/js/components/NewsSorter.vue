<template>
    <v-card class="ma-4">
        <v-card-text>{{username}}</v-card-text>
    </v-card>
</template>

<script>
    import {mapState} from 'vuex'
    export default {
        name: "NewsSorter",
        computed: mapState(['username'])
    }
</script>

<style scoped>

</style>