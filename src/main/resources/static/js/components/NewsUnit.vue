<template>
<div>
    <v-card class="ma-4" v-for="oneNews in news"
            :key="oneNews.id"
    >
        <v-card-title primary-title>
            <div>
                <h3 class="headline mb-0">{{ oneNews.head }}</h3>
                <div> {{ oneNews.text }} </div>
            </div>
        </v-card-title>
    </v-card>
</div>
</template>

<script>
    import { mapGetters } from 'vuex'
    export default {
        computed: mapGetters(['news'])
    }
</script>

<style scoped>

</style>