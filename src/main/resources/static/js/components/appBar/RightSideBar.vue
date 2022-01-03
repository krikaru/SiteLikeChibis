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
                        <v-list-item-content>
                            <v-list-item-title>{{this.principal ? this.principal.name : 'Гость'}}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item-group>
                        <v-divider v-if="this.principal"></v-divider>

                        <v-list-item
                                v-for="(item, index) in profileMenu()"
                                :key="index"
                                @click="$route.path === item.link ? '' : $router.push(item.link)"
                        >
                            <v-list-item-icon>
                                <v-icon v-text="item.icon"></v-icon>
                            </v-list-item-icon>
                            <v-list-item-title>
                                {{ item.title }}
                            </v-list-item-title>
                        </v-list-item>

                        <v-divider v-if="this.principal"></v-divider>

                        <v-list-item v-if="this.principal">
                            <v-list-item-icon>
                                <v-icon v-text="'logout'"></v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <form @submit="checkForm()" action="/logout" method="post">
                                    <input type="submit" value="Выйти">
                                </form>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-menu>
        </v-flex>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    export default {
        name: "RightSideBar",

        computed: {
            ...mapGetters(['principal']),

        },

        data() {
            return {
                nullProfileMenuItems: [
                    {title: 'Зарегистрироваться', link: '/registration', icon: 'add_box'},
                    {title: 'Войти', link: '/login', icon: 'login'},
                ],
                profileMenuItems: [
                    {title: 'Мой профиль', link: '/profile', icon: 'person'},
                    {title: 'Корзина', link: '/shopcart', icon: 'shopping_cart'},
                    {title: 'Мои публикации', link: '/mynews', icon: 'article'},
                    {title: 'Настройки', link: '/settings', icon: 'settings'},
                ]
            }
        },

        methods: {
            profileMenu() {
                return this.principal ? this.profileMenuItems : this.nullProfileMenuItems
            },

            checkForm() {
                return true
            }
        }

    }
</script>

<style scoped>

</style>