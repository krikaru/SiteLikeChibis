<template>
    <v-container fluid>
        <v-layout align-start justify-space-around row>
            <v-flex xs11>
                <v-card>
                    <v-tabs
                            vertical
                    >
                        <v-tab>
                            <v-icon left>
                                mdi-account
                            </v-icon>
                            Личные данные
                        </v-tab>
                        <v-tab-item>
                            <personal-settings :principal="this.principal"></personal-settings>
                        </v-tab-item>

                        <v-tab>
                            <v-icon left>
                                mdi-lock
                            </v-icon>
                            Разрешения
                        </v-tab>
                        <v-tab-item>
                            <delete-settings></delete-settings>
                        </v-tab-item>

                        <v-tab style="color: red">Удаление аккаунта</v-tab>
                        <v-tab-item>
                            <delete-settings :principal="this.principal"></delete-settings>
                        </v-tab-item>
                    </v-tabs>
                </v-card>
            </v-flex>
            <v-flex xs8>

            </v-flex>

        </v-layout>
    </v-container>


</template>

<script>
    import { mapState } from 'vuex'
    import PersonalSettings from 'components/profileSettings/PersonalSettings.vue';
    import DeleteSettings from 'components/profileSettings/DeleteSettings.vue';
    export default {
        name: "SettingsPage",
        components: {DeleteSettings, PersonalSettings },
        computed: {
            ...mapState(['principal']),
        },
    }
</script>

<style scoped>

</style>