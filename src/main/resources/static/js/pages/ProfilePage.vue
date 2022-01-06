<template>
    <v-main style="padding: 150px 0px 0px">
        <v-container fluid>
            <v-layout>
                <v-file-input
                        v-model="userpic"
                        label="File input"
                        :rules="[pic => !!pic || 'should'] "
                        filled
                        prepend-icon="mdi-camera"
                ></v-file-input>
                <v-btn
                        @click = "submitUserpic"
                >
                    Добавить
                </v-btn>
            </v-layout>
        </v-container>
    </v-main>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "ProfilePage",
        data() {
            return {
                userpic: '',
            }
        },
        methods: {
            ...mapActions(['addUserpicAction']),
            submitUserpic() {
                let pic = new FormData;
                pic.set('userpic', this.userpic);
                this.addUserpicAction(pic)
            }
        }
    }
</script>

<style scoped>

</style>