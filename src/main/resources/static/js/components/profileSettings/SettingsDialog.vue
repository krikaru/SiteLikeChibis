<template>
    <v-dialog
            v-model="dialog"
            max-width="600px"
            persistent
    >
        <template v-slot:activator="{ on, attrs }">
            <v-list-item v-bind="attrs"
                         v-on="on">
                <v-list-item-content>
                    <v-list-item-title>{{item.title}}</v-list-item-title>
                    <v-list-item-subtitle>{{item.subtitle}}</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </template>

        <v-card>
            <v-alert v-if="item.deleteError" type="error">
                {{item.deleteError}}
            </v-alert>
            <v-card-title>
                <span class="text-h5">{{item.dialogTitle}}</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-alert v-if="typeAlert" :type="typeAlert">
                        {{this.alertMsg}}
                    </v-alert>

                    <v-row v-if="item.fieldLabel || item.fileInput">
                        <v-col>
                            <v-form ref="form"
                                    v-model="valid">
                                <v-text-field
                                        v-if="item.fieldLabel"
                                        :label=item.fieldLabel
                                        v-model="newValue"
                                        :rules="item.rules()"
                                        :type="item.propName === 'password' ? 'password' : ''"
                                ></v-text-field>
                                <v-file-input
                                        v-if="item.fileInput"
                                        show-size
                                        accept="image/png, image/jpeg"
                                        :label=item.fileInput
                                        v-model="image"
                                        prepend-icon="mdi-camera"
                                        :rules="item.rules()"
                                ></v-file-input>
                            </v-form>
                        </v-col>
                    </v-row>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                                color="blue darken-1"
                                text
                                @click="close"
                        >
                            Закрыть
                        </v-btn>
                        <v-btn
                                color="blue darken-1"
                                text
                                :disabled="!valid"
                                @click="submit"
                        >
                            <span>Сохранить</span>
                        </v-btn>
                    </v-card-actions>
                </v-container>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: "SettingsDialog",
        props: ['item', 'setUpdatedAttribute', 'updateFunction'],
        data() {
            return {
                dialog: '',
                newValue: '',
                image: [],
                valid: true,
                typeAlert: '',
                alertMsg:'',
                updatedUser: {
                    id: '',
                    name: '',
                    password: '',
                    email: '',
                    userpic: []
                }
            }
        },

        computed: {
            ...mapState(['principal'])
        },

        methods: {

            async submit () {
                this.setUpdatedAttribute(this)

                let errors = await this.updateFunction({ updatedUser: this.updatedUser, propName: this.item.propName})

                if (!!!errors) {
                    this.alertMsg = 'Ваши данные обновлены!'
                    this.typeAlert = 'success'
                } else {
                    this.alertMsg = 'Данные не были обновлены!'
                    this.typeAlert = 'error'
                }
            },

            close() {
                this.typeAlert = ''
                this.$refs.form.resetValidation()
                this.newValue = ''
                this.image = []
                this.dialog = false
            }
        },

    }
</script>

<style scoped>

</style>