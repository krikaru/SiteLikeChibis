<template>
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

</template>

<script>
    export default {
        name: "NavigationButton"
    }
</script>

<style scoped>

</style>