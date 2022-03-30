import Rules from './rules'

export default {
    personalSettingsList: [
        {
            title: 'Изменить имя', subtitle: 'Имя, которое видят другие пользователи',
            dialogTitle:'Изменить имя', fieldLabel: 'Введите новое имя',
            rules: Rules.nameRules, propName: 'name'
        },
        {
            title: 'Добавить/изменить фото профиля', subtitle: null,
            dialogTitle:'Добавить/изменить фото профиля', fileInput: 'Загрузить новое фото',
            rules: Rules.userpicRules, propName: 'userpic'
        },
        {
            title: 'Изменить e-mail', subtitle: 'На этот e-mail будут приходить все новости',
            dialogTitle:'Изменить e-mail', fieldLabel: 'Введите новый e-mail',
            rules: Rules.emailRules, propName: 'email'
        },
        {
            title: 'Изменить пароль', subtitle: null,
            dialogTitle:'Изменить пароль', fieldLabel: 'Введите новый пароль',
            rules: Rules.passwordRules, propName: 'password'
        }
    ],
}