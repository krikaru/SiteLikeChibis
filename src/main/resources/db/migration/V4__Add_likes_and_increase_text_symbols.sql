alter table news alter column text type varchar(10000);
alter table news add likes_count int;
alter table news add check ( likes_count > 0 );