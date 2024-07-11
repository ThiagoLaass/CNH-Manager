# CNH Manager


**Leticia Amaral Figueiredo, leticia.figueiredo.1417260@sga.pucminas.br**

**Leandro caldas pacheco, lcpacheco@sga.pucminas.br**

**Isadora Maria de Araujo Cathalat, icathalat@sga.pucminas.br**

**Ian Matsuhara Ferraz, ian.ferraz@sga.pucminas.br**

**Thiago Borges Laass, tblaass@sga.pucminas.br**

**Matheus Eduardo Marinho de Miranda Hermenegildo, matheus.hermenegildo@sga.pucminas.br**

---

Professores:

**Aline Norberta de Brito**

**Eveline Alonso Veloso**

---

_Curso de Engenharia de Software_

_Instituto de Informática e Ciências Exatas – Pontifícia Universidade Católica de Minas Gerais (PUC MINAS), Belo Horizonte – MG – Brasil_

---

**Resumo**. _Nosso software, CNH Manager, foi desenvolvido tendo em mente uma ferramenta que nasce para melhorar a comunicação e gestão dos processos entre alunos e autoescolas. O projeto procura solucionar uma necessidade clara de otimização da experiência dos usuários em autoescolas, evidenciada pelo alto volume de reclamações que observamos em sites como o ReclameAqui, além disso, fizemos uso também do insight valioso de participantes do grupo que se encontram atualmente no processo de obtenção da CNH. O principal objetivo do nosso software é tornar todo o processo envolvido na retirada da CNH, desde a marcação de aulas teóricas até a marcação do exame prático, mais intuitiva e automatizada. Além disso, visamos também uma interação mais eficiente e organizada entre autoescola, aluno e instrutor. O CNH Manager oferece funcionalidades como agendamento de aulas práticas, visualização de progresso e avaliação de serviços, entre outras. Esperamos assim melhorar a satisfação do cliente e, por consequência, a reputação e competitividade das autoescolas no mercado._

---


## 1. Introdução

Nosso software visa se estabelecer mediador da relação entre a autoescola e o aluno, facilitando a comunicação e a organização entre ambos e permitindo ao cliente gerenciar e visualizar seu processo durante a obtenção da CNH.

### 1.1 Contextualização

Com as mudanças geradas na contemporaneidade à sociedade, a carteira de motorista se tornou um documento essencial à vida adulta. Por essa razão, a demanda dos serviços da auto-escola - centro responsável por fornecer formação de condução veicular - têm aumentado significativamente. No Brasil o mesmo acontece, já são mais de 14.000 unidades de autoescola credenciadas no território brasileiro de acordo o blog Portal do Trânsito. No entanto, observa-se que o crescimento da procura por esse tipo de atendimento não necessariamente está relacionado à satisfação do consumidor com a qualidade desses serviços. Das reclamações registradas no site Reclame Aqui, 3503 são direcionadas à auto escolas, sendo 1405 referentes ao setor Administrativo e 811 ao setor de comunicação cliente-fornecedor, ilustrando dificuldade em marcar aulas, visualizar grade horária das auto escolas e atendimento precário. 

### 1.2 Problema

Os problemas que o sistema visa atacar são a falta de organização e visibilidade nos processos que envolvem a autoescola e o contratante do serviço oferecido. Tais carências acarretam na dificuldade de controle por parte do estudante em saber qual fase do processo de retirada da carteira de motorista ele se encontra, gerando uma má experiência para o cliente e um feedback negativo para os serviços oferecidos pela autoescola. 
Tal fenômeno é observado no famoso site de avaliações de empresas e serviços: [Reclame Aqui](#REFERÊNCIAS), em que os comentários negativos relacionados à autoescola sempre evidenciam uma comunicação ruim e a falta de organização como principal motivador para a insatisfação dos clientes.

### 1.3 Objetivo geral

O objetivo geral do grupo é descomplicar a visualização do processo de obtenção da Carteira Nacional de Habilitação, para isso, planejamos oferecer, tanto à autoescola quanto ao estudante, o controle e uma visualização abrangente de todo o processo  para a retirada da CNH, por meio de um diálogo mais objetivo entre ambos.

#### 1.3.1 Objetivos específicos

1 - Disponibilizar o agendamento de aulas práticas de direções com instrutores credenciados a depender das necessidades de horário dos alunos matriculados. 
2 - Permitir a visualização do progresso feito pelo aluno durante a execução do processo de retirada da CNH, bem como, da grade horária das aulas fornecidas pela autoescola. 
3 - Oferecer a possibilidade do agendamento de aulas práticas adicionais, mediante a necessidade do aluno.
4 - Possibilitar a marcação de provas práticas e teóricas, após o aluno ter atingido os requisitos.


### 1.4 Justificativas

A pesquisa sobre otimização da interface de serviços da IEEE, publicada em 2011, destaca a importância da melhoria de processos para ganhar vantagem competitiva. Essa abordagem pode ser exemplificada no contexto da obtenção de CNH, em que uma ferramenta de organização e visualização do processo pode reduzir a quantidade de problemas na comunicação entre as partes envolvidas e melhorar a experiência do contratante com a empresa. Com isso, a autoescola constrói uma boa reputação e, consequentemente, ganha notoriedade entre as empresas concorrentes.

## 2. Participantes do processo

**Aluno**: A entidade que contrata os serviços da autoescola, que participa dos processos que o software dispõe facilitar. Desde jovens, acima dos 18 anos, a pessoas mais velhas, com a possibilidade de parte destes não serem familiarizados com sistemas digitais. Sua atuação no sistema será verificar seu progresso e solicitar aulas e provas, assim como avaliar, seja a autoescola ou os instrutores, com o fim de dar feedback para os administradores da autoescola. 

**Autoescola**: Será o mediador dos processos que envolvem sua relação com os alunos, outro participante do processo, visando aumentar a comunicação com os mesmos. Assim como a entidade compradora do sistema que terá como manipulador do software algum funcionário da equipe administrativa ou tecnológica. Os aspectos relevantes desse perfil são ter entre 20 e 50 anos, ser alfabetizado  e possuir familiaridade com tecnologia e sistemas administrativos, que será seu papel desempenhado no sistema. Ou seja, será o controlador direto deste, cadastrando e confirmando as aulas feitas pelo aluno no sistema, com o objetivo de aumentar a evidência do progresso para o mesmo.

**Instrutor**: O instrutor, em uma etapa específica do processo, é uma peça chave para a formação do aluno, sendo ele o responsável por ensinar o aluno a conduzir o veículo e ensiná-lo às normas de legislação. Esse perfil se encaixa na faixa etária acima dos 30 anos, mesmo que o mínimo necessário seja 21 anos. O nível mínimo de escolaridade é o ensino médio. Esse usuário terá ações básicas no sistema, envolvendo apenas aceitar alunos ao seu perfil.

## 3. Modelagem do processo de negócio

### 3.1. Análise da situação atual

Atualmente, o único controle do processo de retirada da CNH é oriundo de dados vindos do DETRAN, com acesso privado à autoescola. Sendo assim, o próprio aluno precisa ter autonomia para se organizar e dessa forma, se localizar quanto às etapas do processo. 
Além disso, devido à dependência ao sistema do DETRAN, problemas relacionados com quedas de luz, atraso no carregamento do software no momento de confirmar as aulas feitas, ou até mesmo a queda do sistema em si são recorrentes e dificultam ainda mais a experiência do cliente e a visibilidade do processo.

### 3.2. Descrição geral da proposta

A proposta de solução tem como objetivo solucionar a falta de visibilidade durante o processo da retirada de CNH, tanto ao aluno, quanto à auto-escola, que muitas vezes não possui controle consciente das etapas em que os alunos matriculados se encontram. O software visa permitir ao aluno autonomia durante a retirada da CNH, bem como, uma comunicação mais clara, amigável e eficiente entre ambos os participantes desse processo. Para que dessa forma, a autoescola ganhe vantagem competitiva em relação aos outros centros de formação ao condutor. 
Dessa maneira, a aplicação propõe-se facilitar o acompanhamento do progresso pelo aluno, marcar provas e aulas; além de simplificar sua comunicação com a autoescola. Para que assim, se exclua a relação de dependência aos dados fornecidos pelo DETRAN que podem vir a tornar o processo da auto escola vulnerável aos problemas enfrentados pelo seu software. Contudo, a aplicação não pretende substituir as funções do DETRAN, mas otimizar todo o processo que envolve o estudante e a empresa.


### 3.3. Modelagem dos processos

[PROCESSO 1 - Solicitar comprovante de presença](processo-1-solicitar-comprovante-presenca.md "Detalhamento do Processo 1.")

[PROCESSO 2 - Solicitar prova de legislação e de direção](processo-2-solicitar-prova.md "Detalhamento do Processo 2.")

[PROCESSO 3 - Solicitar aulas práticas](processo-3-solicitar-aula-pratica.md "Detalhamento do Processo 3.")

[PROCESSO 4 - Solicitar aulas adicionais de direção](processo-4-solicitar-aulas-adicionais-de-direção.md "Detalhamento do Processo 4.")

## 4. Projeto da solução

O documento a seguir apresenta o detalhamento do projeto da solução. São apresentadas duas seções que descrevem, respectivamente: modelo relacional e tecnologias.

[Projeto da solução](solution-design.md "Detalhamento do projeto da solução: modelo relacional e tecnologias.")


## 5. Indicadores de desempenho

O documento a seguir apresenta os indicadores de desempenho dos processos.

[Indicadores de desempenho dos processos](performance-indicators.md)


## 6. Interface do sistema

A sessão a seguir apresenta a descrição do produto de software desenvolvido. 

[Documentação da interface do sistema](interface.md)

## 7. Conclusão

_Para a conclusão da produção técnica de software, intitulada “CNH Manager”, será destacado os resultados alcançados em meio às observações de cada integrante. Durante o desenvolvimento deste projeto, foram encontrados desafios significativos, no entanto, realizações importantes também foram obtidas. A implementação do Front-end e Back-end foi feita, com as ferramentas já citadas, com sucesso. Assim como a integração destas duas partes As funcionalidades essenciais para a gestão dos processos propostos foram implementadas com êxito. Cada um dos testes feitos foi de grande valia para o resultado final do projeto, ajudando a corrigir problemas precocemente. A colaboração entre os membros foi fundamental durante o desenvolvimento. Através de reuniões regulares, divisões de tarefas e comunicação eficaz, a entrega do projeto se alinhou com as expectativas e prazos estabelecidos._

# REFERÊNCIAS

_Como um projeto de software não requer revisão bibliográfica, a inclusão das referências não é obrigatória. No entanto, caso você deseje incluir referências relacionadas às tecnologias, padrões, ou metodologias que serão usadas no seu trabalho, relacione-as de acordo com a ABNT._

_Verifique no link abaixo como devem ser as referências no padrão ABNT:_

http://portal.pucminas.br/imagedb/documento/DOC_DSC_NOME_ARQUI20160217102425.pdf

**[1.1]** - _ELMASRI, Ramez; NAVATHE, Sham. **Sistemas de banco de dados**. 7. ed. São Paulo: Pearson, c2019. E-book. ISBN 9788543025001._

**[1.2]** - _RECLAME Aqui: Qualidade no serviço da autoescola. [S. l.]. Disponível em: RECLAME Aqui. Https://www.reclameaqui.com.br/busca/?q=qualidade%20do%20servi%C3%A7o%20na%20auto%20escola.  Acesso em: 23 fev. 2024._

**[1.3]** - _COPPIN, Ben. **Inteligência artificial**. Rio de Janeiro, RJ: LTC, c2010. E-book. ISBN 978-85-216-2936-8._

**[1.4]** - _CORMEN, Thomas H. et al. **Algoritmos: teoria e prática**. Rio de Janeiro, RJ: Elsevier, Campus, c2012. xvi, 926 p. ISBN 9788535236996._

**[1.5]** - _SUTHERLAND, Jeffrey Victor. **Scrum: a arte de fazer o dobro do trabalho na metade do tempo**. 2. ed. rev. São Paulo, SP: Leya, 2016. 236, [4] p. ISBN 9788544104514._

**[1.6]** - _RUSSELL, Stuart J.; NORVIG, Peter. **Inteligência artificial**. Rio de Janeiro: Elsevier, c2013. xxi, 988 p. ISBN 9788535237016._



# APÊNDICES


_Atualizar os links e adicionar novos links para que a estrutura do código esteja corretamente documentada._


## Apêndice A - Código fonte

[Código do front-end](../src/front) -- repositório do código do front-end

[Código do back-end](../src/back)  -- repositório do código do back-end


## Apêndice B - Apresentação final


[Slides da apresentação final](presentations/)


[Vídeo da apresentação final](video/)
[Vídeo da apresentação final no Youtube](https://youtu.be/8Ccwd53vY3A?si=50FM1UAeByUaB2CW)






