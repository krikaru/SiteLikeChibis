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
                             :submitFunction="update(item.propName)"
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
    import { mapActions } from "vuex";
    export default {
        name: "PersonalSettings",
        props: ['principal'],
        components: { SettingsDialog },
        data() {
            return {
                personalSettingsList: [
                    {
                        title: 'Изменить имя', subtitle: 'Имя, которое видят другие пользователи',
                        dialogTitle:'Изменить имя', fieldLabel: 'Введите новое имя',
                        rules: this.nameRules, dialog: '', propName: 'name'
                    },
                    {
                        title: 'Добавить/изменить фото профиля', subtitle: null,
                        dialogTitle:'Добавить/изменить фото профиля', fileInput: 'Загрузить новое фото',
                        rules: this.userpicRules, dialog: '', propName: 'userpic'
                    },
                    {
                        title: 'Изменить e-mail', subtitle: 'На этот e-mail будут приходить все новости',
                        dialogTitle:'Изменить e-mail', fieldLabel: 'Введите новый e-mail',
                        rules: this.emailRules, dialog: '', propName: 'email'
                    },
                    {
                        title: 'Изменить пароль', subtitle: null,
                        dialogTitle:'Изменить пароль', fieldLabel: 'Введите новый пароль',
                        rules: this.nameRules, dialog: '', propName: 'password'
                    }
                ],
            }
        },
        methods: {
            ...mapActions(['updateProfileAction', 'updateUserpicAction']),
            nameRules () {
                return [
                    name => !!name || 'Введите своё имя!',
                    name => name !== this.principal.name || 'Новое имя совпадает со старым',
                    name => name.length <= 25 || 'Имя должно быть не больше 25 символов',
                    name => name.length >= 3 || 'Имя должно быть не меньше 3 символов',
                    name => /^[a-zA-Zа-яА-ЯёЁ-]+$/.test(name) || 'Имя должно содержать только буквы'
                ]
            },
            emailRules () {
                return [
                    email => !!email || 'Введите email!',
                    email => this.checkEmail(email) || 'Неверный формат'
                ]
            },
            checkEmail(email) {
                const regExpEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return regExpEmail.test(String(email).toLowerCase());
            },
            passwordRules() {
                return [
                    password => !!password || 'Введите пароль!',
                    password => password.length <= 15 || 'Пароль должен быть не больше 25 символов',
                    password => password.length >= 6 || 'Пароль должен быть не меньше 6 символов',
                    password => /^\S+$/.test(password) || 'В пароле не должно быть пробелов'
                ]
            },

            userpicRules() {
                return [
                    userpic => !!userpic || 'Фото не добавлено!',
                    userpic => !userpic || userpic.size < 5000000 || 'Размер фото не должен превышать 5 MB!',
                    userpic => (userpic !== null && (userpic.type==='image/jpeg' || userpic.type==='image/png')) || 'Неверное расширение файла'
                ]
            },

            update(propName) {
                if (propName === 'userpic') {
                    return this.updateUserpic
                } else {
                    return this.updateInfo
                }
            },

            updateInfo(newValue, propName) {
                const updatedUserInfo = {
                    updatedUser: {
                        id: this.$route.params.id,
                    },
                    nameAttribute: propName
                }
                this.setUpdatedAttribute(updatedUserInfo, newValue)

                this.updateProfileAction(updatedUserInfo)
            },

            updateUserpic(newPic, propName=null) {
                const userpic = new FormData()
                userpic.set("userpic", newPic);
              this.updateUserpicAction({userpic, id: this.$route.params.id})
            },

            setUpdatedAttribute(updatedUserInfo, newValue) {
                switch (updatedUserInfo.nameAttribute) {
                    case 'name':
                        updatedUserInfo.updatedUser.name = newValue
                        break
                    case 'password':
                        updatedUserInfo.updatedUser.password = newValue
                        break
                    case 'email':
                        updatedUserInfo.updatedUser.email = newValue
                    case 'userpic':
                        updatedUserInfo.updatedUser.userpic = newValue
                        break
                }
            }
        }
    }
</script>

<style scoped>

</style>