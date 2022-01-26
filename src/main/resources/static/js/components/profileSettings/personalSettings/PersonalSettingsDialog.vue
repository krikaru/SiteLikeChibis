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
            <v-card-title>
                <span class="text-h5">{{item.dialogTitle}}</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col>
                            <v-form ref="form"
                                    v-model="valid">
                                <v-text-field
                                        :label=item.fieldLabel
                                        v-model="newValue"
                                        :rules="item.rules()"
                                        validate-on-blur
                                        required
                                ></v-text-field>

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
                            Close
                        </v-btn>
                        <v-btn
                                color="blue darken-1"
                                text
                                :disabled="!valid"
                                @click="validate"
                        >
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-container>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "PersonalSettings",
        props: ['principal', 'item'],
        data() {
            return {
                dialog: '',
                newValue: '',
                valid: true,
            }
        },

        methods: {
            ...mapActions(['updateProfileAction']),
            validate () {
                const updatedUserInfo = {
                    updatedUser: {
                        id: this.$route.params.id,
                    },
                    nameAttribute: this.item.propName
                }
                this.setUpdatedAttribute(updatedUserInfo)

                this.updateProfileAction(updatedUserInfo)
                this.$refs.form.validate()
            },

            setUpdatedAttribute(updatedUserInfo) {
                switch (updatedUserInfo.nameAttribute) {
                    case 'name':
                        updatedUserInfo.updatedUser.name = this.newValue
                        break
                    case 'password':
                        updatedUserInfo.updatedUser.password = this.newValue
                        break
                    case 'email':
                        updatedUserInfo.updatedUser.email = this.newValue
                        break
                }
            }
        }
    }
</script>

<style scoped>

</style>