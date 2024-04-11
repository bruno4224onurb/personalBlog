USE db_blogpessoal;

SELECT * FROM tb_postagens;INSERT INTO tb_postagens (id, data, texto, titulo) 
VALUES (1,current_timestamp(), 'Texto da postagem 01', 'Postagem 01');
INSERT INTO tb_postagens (id, data, texto, titulo) 
VALUES (2,current_timestamp(), 'Texto da postagem 02', 'Postagem 02');

SELECT * FROM tb_postagens;