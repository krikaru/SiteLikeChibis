import Vue from 'vue'

export default {
    like: (newsId) => Vue.http.get(`/api/news/${newsId}/like`)
}
