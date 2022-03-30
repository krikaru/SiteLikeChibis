<template>
    <v-card flat>
        <v-list
                two-line
                subheader
        >
            <v-subheader>Персональные данные</v-subheader>

            <settings-dialog v-for="(item, index) in personalSettingsList"
                             :key="index"
                             :item="item"
                             :setUpdatedAttribute="setUpdatedAttribute"
                             :updateFunction="updateFunction"
            >
            </settings-dialog>

        </v-list>

        <!--        <v-divider></v-divider>-->

        <!--        <v-list-->
        <!--                subheader-->
        <!--                two-line-->
        <!--                flat-->
        <!--        >-->
        <!--            <v-subheader>Видимость персональных данных</v-subheader>-->

        <!--            <v-list-item-group-->
        <!--                    multiple-->
        <!--            >-->
        <!--                <v-list-item>-->
        <!--                    <template v-slot:default="{ active, }">-->
        <!--                        <v-list-item-action>-->
        <!--                            <v-checkbox-->
        <!--                                    :input-value="active"-->
        <!--                                    color="primary"-->
        <!--                            ></v-checkbox>-->
        <!--                        </v-list-item-action>-->

        <!--                        <v-list-item-content>-->
        <!--                            <v-list-item-title>Видимость e-mail</v-list-item-title>-->
        <!--                            <v-list-item-subtitle>Видимость вашего e-mail для других пользователей</v-list-item-subtitle>-->
        <!--                        </v-list-item-content>-->
        <!--                    </template>-->
        <!--                </v-list-item>-->
        <!--            </v-list-item-group>-->
        <!--        </v-list>-->
    </v-card>
</template>

<script>
    import SettingsDialog from './SettingsDialog.vue'
    import Dialog from 'util/dialogs.js'
    import { mapActions, mapMutations } from 'vuex'
    export default {
        name: "PersonalSettings",
        components: { SettingsDialog },
        data() {
            return {
                personalSettingsList: Dialog.personalSettingsList
            }
        },

        methods: {
            ...mapActions(['updateProfileAction', 'updateUserpicAction']),
            ...mapMutations(['updateProfileMutation']),
            async updateFunction(updatedUserInfo) {
                let errors
                if (updatedUserInfo.propName === 'userpic' ) {
                    errors = await this.updateUserpicAction(updatedUserInfo.updatedUser)
                } else {
                    updatedUserInfo.updatedUser.userpic = ''
                    errors = await this.updateProfileAction(updatedUserInfo)
                    if (!!!errors) {
                        this.updateProfileMutation(updatedUserInfo)
                    }
                }
                return errors
            },

            setUpdatedAttribute(thisDialog) {
                switch (thisDialog.item.propName) {
                    case 'name':
                        thisDialog.updatedUser.name = thisDialog.newValue
                        break
                    case 'password':
                        thisDialog.updatedUser.password = thisDialog.newValue
                        break
                    case 'email':
                        thisDialog.updatedUser.email = thisDialog.newValue
                        break
                    case "userpic":
                        const userpic = new FormData()
                        userpic.set("userpic", thisDialog.image);
                        thisDialog.updatedUser.userpic = userpic
                        break
                }

                thisDialog.updatedUser.id = thisDialog.$route.params.id
            },
        },
    }
</script>

<style scoped>

</style>