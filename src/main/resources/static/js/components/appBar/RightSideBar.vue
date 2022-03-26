<template>
    <v-layout align-start justify-end fill-height>
        <v-flex xs2>
            <v-menu offset-y>
                <template v-slot:activator="{ on: menu, attrs }">
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on: tooltip }">
                            <v-btn
                                    icon
                                    v-bind="attrs"
                                    v-on="{ ...tooltip, ...menu }"
                            >
                                <v-icon>
                                    account_circle
                                </v-icon>
                            </v-btn>
                        </template>
                        <span>{{principal ? principal.name : 'Гость'}}</span>
                    </v-tooltip>
                </template>
                <v-list>
                    <v-list-item v-if="this.principal">
                        <v-list-item-avatar>
                            <img
                                    :src="`/img/${this.principal.userpic}`"
                                    alt="John"
                            >
                        </v-list-item-avatar>
                        <v-list-item-content>
                            <v-list-item-title>{{this.principal ? this.principal.name : 'Гость'}}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item-group>
                        <v-divider v-if="this.principal"></v-divider>

                        <v-list-item
                                v-for="(item, index) in profileMenu()"
                                :key="index"
                                @click="clickProfileMenu(item)"
                        >
                            <v-list-item-icon>
                                <v-icon v-text="item.icon"></v-icon>
                            </v-list-item-icon>
                            <v-list-item-title>
                                {{ item.title }}
                            </v-list-item-title>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-menu>
        </v-flex>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "RightSideBar",
        props: ['principal'],
        data() {
            return {

            }
        },

        methods: {
            ...mapActions(['logoutAction']),


            profileMenu() {
                if (this.principal) {
                    return [
                        {title: 'Мой профиль', link: `/profile/${this.principal.id}`, icon: 'person'},
                        {title: 'Корзина', link: '/shopcart', icon: 'shopping_cart'},
                        {title: 'Мои публикации', link: '/controlnews', icon: 'article'},
                        {title: 'Настройки', link: `/profile/${this.principal.id}/settings`, icon: 'settings'},
                        {title: 'Выйти', link: '/logout', icon: 'logout'},
                    ]
                } else {
                    return [
                        {title: 'Зарегистрироваться', link: '/registration', icon: 'add_box'},
                        {title: 'Войти', link: '/login', icon: 'login'},
                    ]
                }
            },

            async clickProfileMenu(item) {
                if (item.link === '/logout') {
                    await this.logoutAction()
                    this.principal = null
                    window.location.href = '/'
                }
                else if (this.$route.path !== item.link) {
                    return this.$router.push(item.link)
                } else {
                    return ''
                }
            }
        }

    }
</script>

<style scoped>

</style>