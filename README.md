# CNH Manager

Nosso software visa se estabelecer como mediador da relação entre a autoescola e o aluno, facilitando a comunicação e a organização entre ambos e permitindo ao cliente gerenciar e visualizar seu processo durante a obtenção da CNH.

## Integrantes

* Ian Matsuhara Ferraz
* Isadora Maria de Araujo Cathalat
* Leandro Pacheco
* Leticia Amaral Figueiredo
* Matheus Hermenegildo
* Thiago Borges Laass

## Professoras

* Aline Norberta de Brito
* Eveline Alonso Veloso

## Instruções de Utilização do Software

### 1. Introdução
**Descrição**: Este software é uma ferramenta de gerenciamento para o curso de obtenção da CNH, visando facilitar o processo tanto para os alunos quanto para as autoescolas.

### 2. Requisitos do Sistema
- **Sistema Operacional**: Windows, macOS, ou Linux.
- **Java**: JDK 17 ou superior.
- **Maven**: Necessário para gerenciamento de dependências.
- **PostgreSQL**: Necessário para gerenciamento do banco de dados.

### 3. Instalação

#### 3.1 Download e Instalação do Maven
1. Baixe o Maven no site oficial: [Maven Download](https://maven.apache.org/download.cgi).
2. Siga as instruções de instalação para seu sistema operacional: [Maven Installation Guide](https://maven.apache.org/install.html).

#### 3.2 Download e Instalação do PostgreSQL
1. Baixe e instale o PostgreSQL a partir do site oficial: [PostgreSQL Download](https://www.postgresql.org/download/).
2. Durante a instalação, configure a senha do usuário `postgres` para "admin".
   - **Nota**: Se já possuir o PostgreSQL instalado, certifique-se de que a senha do usuário `postgres` é "admin". Caso contrário, altere a senha no arquivo `application.properties` localizado na pasta `resources`.

#### 3.3 Configuração Inicial
1. **Configurar Banco de Dados**:
   - Popule as tabelas `tipo_prova`, `tipo_pagamento` e `aula` utilizando os scripts fornecidos na pasta `scripts`.
   - Executar os scripts SQL no PostgreSQL.

2. **Configurar o Software**:
   - No arquivo `application.properties` em `resources`, configure as propriedades do banco de dados conforme necessário.

### 4. Interface do Usuário

#### 4.1 Modais e telas
- **Horário**: O usuário deve escolher o instrutor desejado e será redirecionado para uma tela contendo os horários disponíveis do instrutor escolhido.
- **Prova**: Requerimento de provas de acordo com os tipos disponíveis.
- **Presença**: Requerimento e confirmação de presença nas aulas.
- **Ver pagamentos**: Ver os pagamentos a serem efetuados, ou aqueles já aprovados ou negados.
- **Minhas provas**: Ver as provas, tenham sido aprovadas ou não.
- **Aprovar ou negar horário**: Na tela "instrutor", o mesmo tem a capacidade de confirmar ou não os horários.
- **Cadastrar horários**: Na tela "instrutor", o mesmo tem a capacidade de cadastrar seus horários.
- **Aprovar ou negar presença**: Na tela "admin", o mesmo tem a capacidade de aprovar ou não o requerimento de presença.
- **Aprovar ou negar prova**: Na tela "admin", este modal lista as provas, o admin tem a capacidade de aprovar ou reprovar o aluno nas mesmas com base em sua nota.
- **Indicadores**: Tela contendo os dados das KPIs desenvolvidas. 

### 5. Funcionalidades Principais
- **Requerimento de Presença**: Permite ao usuário registrar e confirmar presença nas aulas.
- **Escolha de Horários para Aula Prática**: O usuário pode selecionar o instrutor e agendar horários para aulas práticas.
- **Requerimento de Prova**: Facilita o requerimento de provas, listando os tipos disponíveis.

## Histórico de versões

* 0.8.0
    * Versão atual.
* 0.7.0
    * Implementação dos indicadores.
* 0.6.0
    * Implementação da funcionalidade "avaliação do usuario".
* 0.5.1
    * Implementação da funcionalidade "notificação de pagamento a serem efetuados".
* 0.5.0
    * Implementação da funcionalidade "escolher horario adicional" do processo 4 (Solicitar aulas adicionais de direção).
* 0.4.2
    * Implementação da funcionalidade "confirmar horario" do processo 3 (Solicitar aula prática).
* 0.4.1
    * Implementação da funcionalidade "escolha do instrutor e dos horarios" do processo 3 (Solicitar aula prática).
* 0.4.0
    * Implementação da funcionalidade "cadastrar horarios" do processo 3 (Solicitar aula prática).
* 0.3.3
    * Implementação da funcionalidade "aprovar ou reprovar na prova" do processo 2 (Solicitar prova).
* 0.3.2
    * Implementação da funcionalidade "cadastrar pagamento para prova caso aprovada" do processo 2 (Solicitar prova).
* 0.3.1
    * Implementação da funcionalidade "aprovar ou negar requisição de marcação de prova" do processo 2 (Solicitar prova).
* 0.3.0
    * Implementação da funcionalidade "requisição de marcação de prova" do processo 2 (Solicitar prova).
* 0.2.2
    * Atualização da documentação. Sem alteração no código.
* 0.2.1
    * Implementação da funcionalidade "aprovar ou negar requisição de presença" do processo 1 (Solicitar comprovante de presença).
* 0.2.0 
    * Implementação da funcionalidade "cadastar requisição de presença" do processo 1 (Solicitar comprovante de presença).
* 0.1.0 
    * Desenvolvendo modelo de dados.
* 0.0.1
    * Trabalhando na modelagem dos processos de negócio.

