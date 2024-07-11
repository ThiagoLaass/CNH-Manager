ALTER TABLE tipo_pagamento ADD CONSTRAINT unique_name UNIQUE (nome);
INSERT INTO tipo_pagamento(id, nome, preco)
VALUES  (0, 'Horário Adicional', 60),
        (1, 'Prova Teórica', 30),
        (2, 'Prova Prática', 200)
ON CONFLICT (id) DO UPDATE
SET nome = excluded.nome,
    preco = excluded.preco;
