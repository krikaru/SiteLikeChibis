<template>
    <v-dialog
            v-model="dialog"
            max-width="600px"
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
                    <v-row v-if="item.fieldLabel || item.fileInput">
                        <v-col>
                            <v-form ref="form"
                                    v-model="valid">
                                <v-text-field
                                        v-if="item.fieldLabel"
                                        :label=item.fieldLabel
                                        v-model="newValue"
                                        :rules="item.rules()"
                                ></v-text-field>
                                <v-file-input
                                        v-if="item.fileInput"
                                        show-size
                                        accept="image/png, image/jpeg"
                                        :label=item.fileInput
                                        v-model="newValue"
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
                                @click="dialog = false"
                        >
                            Закрыть
                        </v-btn>
                        <v-btn
                                color="blue darken-1"
                                text
                                :disabled="!valid"
                                @click="submit"
                        >
                            <span v-if="item.submitItem">Удалить</span>
                            <span v-else>Сохранить</span>
                        </v-btn>
                    </v-card-actions>
                </v-container>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: "SettingsDialog",
        props: ['item', 'submitFunction'],
        data() {
            return {
                dialog: '',
                newValue: '',
                valid: true,
            }
        },

        methods: {
            submit () {
                this.submitFunction(this.newValue, this.item.propName)
            },
        },

        created() {
            this.newValue = this.item.fileInput ? null : ''
        }
    }
</script>

<style scoped>

</style>