import Vue from 'vue'

export default {
    like: (newsId) => Vue.http.get(`/api/news/${newsId}/like`),
    add: (news) => Vue.http.post('/api/news', news)
}
