<template>
    <v-layout align-center justify-center fill-height>
        <v-flex>
            <v-toolbar-title
                    style="cursor: pointer"
            >
                <v-flex
                        class="text-center"
                        @click="$router.push('/')"
                        v-if="this.$route.path !== '/'"
                >
                    ЧИБИС
                </v-flex>
                <v-flex v-else class="text-center">
                    ЧИБИС
                </v-flex>
            </v-toolbar-title>
        </v-flex>
    </v-layout>
</template>

<script>
    export default {
        name: "LeftSideBar"
    }
</script>

<style scoped>

</style>