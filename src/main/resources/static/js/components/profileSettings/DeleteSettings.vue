<template>
    <v-card flat>
        <v-list
                two-line
                subheader
        >
            <v-subheader>Удалить аккаунт</v-subheader>

            <settings-dialog v-for="(item, index) in deleteSettingsList"
                             :key="index"
                             :item="item"
                             :submitFunction="deleteProfile"
            >
            </settings-dialog>

        </v-list>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'
    import SettingsDialog from "./SettingsDialog.vue";
    export default {
        name: "DeleteSettings",
        props: ['item'],
        components: { SettingsDialog },
        data() {
            return {
                deleteSettingsList: [
                    {
                        title: 'Удалить аккаунт', subtitle: 'Полностью удалить аккаунт без возможности восстановления',
                        dialogTitle:'Удалить аккаунт', submitItem: 'Удалить'
                    },
                ]
            }
        },
        methods: {
            ...mapActions(['deleteProfileAction', 'logoutAction']),
            async deleteProfile() {
                const result = await this.deleteProfileAction(this.$route.params.id)
                if (result.body) {
                    window.location.href = '/logout'
                } else {
                    this.deleteSettingsList[0].deleteError = 'Ошибка удаления аккаунта'
                }
            }
        }


    }
</script>

<style scoped>

</style>