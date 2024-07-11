### 3.3.5 Processo 5 – Avaliar Sistema (reserva)

O processo de avaliação do sistema pode ser significativamente aprimorado através da automatização das atividades. Nesse contexto, os alunos não precisariam mais buscar o local de avaliação; em vez disso, receberiam a oportunidade de fornecer feedback imediatamente após a conclusão do processo.

![Exemplo de um Modelo BPMN do PROCESSO 4](images/BPMN-processo5(reserva)-avaliar-sistema.png "Modelo BPMN do Processo 4.")


#### Detalhamento das atividades

* **Solicitar avaliação do processo concluido**: O sistema da Autoescola envia ao aluno um pedido de avalição quanto as atividades concluidas - solicitação de comprovante de presença ou solicitação de prova - para mensurar a eficácia do sistema.
* **Avaliar o processo**: O aluno preenche os campos do formulário referentes a sua experiência na plataforma.

_Os tipos de dados a serem utilizados são:_

_* **Área de texto** - campo texto de múltiplas linhas_

_* **Caixa de texto** - campo texto de uma linha_

_* **Número** - campo numérico_

_* **Data** - campo do tipo data (dd-mm-aaaa)_

_* **Hora** - campo do tipo hora (hh:mm:ss)_

_* **Data e Hora** - campo do tipo data e hora (dd-mm-aaaa, hh:mm:ss)_

_* **Imagem** - campo contendo uma imagem_

_* **Seleção única** - campo com várias opções de valores que são mutuamente exclusivas (tradicional radio button ou combobox)_

_* **Seleção múltipla** - campo com várias opções que podem ser selecionadas mutuamente (tradicional checkbox ou listbox)_

_* **Arquivo** - campo de upload de documento_

_* **Link** - campo que armazena uma URL_

_* **Tabela** - campo formado por uma matriz de valores_

**Solicitar avaliação do processo concluido**

| **Campo**       | **Tipo**         | **Restrições** | **Valor default** |
| ---             | ---              | ---            | ---               |
| Nome           | Caixa de Texto   | primeira letra maiúscula |                |
| Email           | Caixa de Texto   | formato de e-mail |           |
| Tipo de avaliação  | Caixa de Texto | required      |                   |

| **Comandos**         |  **Destino**                   | **Tipo** |
| ---                  | ---                            | ---               |
| Solicitar        | Envio da solicitação ao aluno     | default           |

**Avaliar o processo**

| **Campo**       | **Tipo**         | **Restrições** | **Valor default** |
| ---             | ---              | ---            | ---               |
| Mensagem | Área de texto  | mínimo 25 caracteres     |                   |

| **Comandos**         |  **Destino**                   | **Tipo**          |
| ---                  | ---                            | ---               |
| Preencher formulário | Envio da avaliação à autoescola |  |
| Cancelar avaliação   | Cancelamento da avaliação       | cancel          |
