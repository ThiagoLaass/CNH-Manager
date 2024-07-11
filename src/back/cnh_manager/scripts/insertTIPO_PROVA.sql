ALTER TABLE tipo_prova ADD CONSTRAINT unique_nome UNIQUE (nome);
INSERT INTO tipo_prova(id, nome)
VALUES  (1, 'Teórica'),
		(2, 'Prática')
ON CONFLICT (id) DO UPDATE
SET nome = excluded.nome;