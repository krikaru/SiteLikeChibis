export default {
    nameRules () {
        return [
            name => !!name || 'Введите своё имя!',
            name => name.length <= 25 || 'Имя должно быть не больше 25 символов',
            name => name.length >= 3 || 'Имя должно быть не меньше 3 символов',
            name => /^[a-zA-Zа-яА-ЯёЁ -]+$/.test(name) || 'Имя должно содержать только буквы'
        ]
    },
    emailRules () {
        return [
            email => !!email || 'Введите email!',
            email => /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(String(email).toLowerCase()) || 'Неверный формат email'
        ]
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
            userpic => !userpic || userpic.size < 5242880 || 'Размер фото не должен превышать 5 MB!',
            userpic => (userpic !== null && (userpic.type==='image/jpeg' || userpic.type==='image/png')) || 'Неверное расширение файла'
        ]
    },
}