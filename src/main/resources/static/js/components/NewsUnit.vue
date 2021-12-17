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
    import {mapState} from 'vuex'
    export default {
        computed: mapState(['news'])
    }
</script>

<style scoped>

</style>