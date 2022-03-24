CREATE TABLE news_likes (
    news_id BIGINT references news(id),
    user_id BIGINT references usr(id)
)