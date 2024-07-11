ALTER TABLE aula ADD CONSTRAINT unique_n UNIQUE (nome);
INSERT INTO aula(id, carga_horaria, nome)
VALUES  (0, 20, 'Prática'),
        (1, 18, 'Legislação de Trânsito'),
		(2, 16, 'Direção defensiva'),
		(3, 4, 'Primeiros Socorros'),
		(4, 4, 'Meio Ambiente'),
		(5, 3, 'Mecânica')
ON CONFLICT (id) DO UPDATE
SET carga_horaria = excluded.carga_horaria,
    nome = excluded.nome;