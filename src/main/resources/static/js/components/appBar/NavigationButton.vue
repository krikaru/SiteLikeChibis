<template>
    <v-layout fill-height align-end>
        <v-flex style="margin: 0 0 13px 0">
            <v-layout align-center justify-center>
                <v-toolbar-items>
                    <v-btn text>
                        Новости
                    </v-btn>

                    <v-divider
                            vertical
                            style="height: 10px"
                    ></v-divider>

                    <v-btn text >
                        Автомобили
                    </v-btn>

                    <v-divider
                            vertical
                            style="height: 10px"
                    ></v-divider>

                    <v-btn text >
                        Продукция
                    </v-btn>

                    <v-divider
                            vertical
                            style="height: 10px"
                    ></v-divider>

                    <v-btn text >
                        О нас
                    </v-btn>

                    <v-divider
                            vertical
                            style="height: 10px"
                    ></v-divider>

                    <v-btn text >
                        Контакты
                    </v-btn>

                </v-toolbar-items>
            </v-layout>
        </v-flex>
    </v-layout>
</template>

<script>
    export default {
        name: "NavigationButton"
    }
</script>

<style scoped>

</style>