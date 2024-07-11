## 7. Testes do software


**Caso de Teste** | **CT01 - Relacionar um instrutor com um aluno.**
 :--------------: | ------------
**Procedimento**  | Relacionar um instrutor com um aluno. |
**Dados de entrada** | Aluno e instrutor cadastrados. |
**Resultado obtido** | Relação de aluno e intrutor construida. |
**Teste associado** | `UserServiceTest.testSetInstrutor()` |

**Caso de Teste** | **CT02 - Confirmação de horário**
 :--------------: | ------------
**Procedimento**  | Aluno confirmar um horário.
**Dados de entrada** | Horario atualizado.
**Resultado obtido** | Horarios esperando aprovação do instrutor.
**Teste associado** | `HorarioPraticaServiceTest.testConfirm()` |

**Caso de Teste** | **CT03 - Atualização de carga horária**
 :--------------: | ------------
**Procedimento**  | Atualizar carga horária do aluno.
**Dados de entrada** | Horas, aluno.
**Resultado obtido** | Carga horária do aluno especifico atualizada.
**Teste associado** | `CargaHorariaServiceTest.testSetHours()` |


**Caso de Teste** | **CT04 - Cálculo da média de aprovação dos alunos**
 :--------------: | ------------
**Procedimento**  | Calcular a media de aprovação dos alunos.
**Dados de entrada** | Usuários no sistema
**Resultado obtido** | Média de aprovação com base no total de usuários.
**Teste associado** | `PerformanceIndicatorService.testCalculateApprovalRate()` |

**Caso de Teste** | **CT05 - Salvar uma prova no banco de dados**
 :--------------: | ------------
**Procedimento**  | Salvar uma prova no banco de dados.
**Dados de entrada** | Dados do usuário, dados da prova.
**Resultado obtido** | Prova inserida no sistema.
**Teste associado** | `ProvaRepositoryTest.testSaveProva()` |

**Caso de Teste** | **CT07 - Cancelar um horário**
 :--------------: | ------------
**Procedimento**  | Ccancelamento de um horário.
**Dados de entrada** | Id do horário
**Resultado obtido** | Aluno atualizada para nulo e statusAberto para true.
**Teste associado** | `HorarioPraticaServiceTest.testCancel()` |

**Caso de Teste** | **CT08 - Atualizar solicitação**
 :--------------: | ------------
**Procedimento**  | Atualizar solicitação.
**Dados de entrada** | Dados da solicitação.
**Resultado obtido** | Solicitação atualizada de acordo com os parâmetros.
**Teste associado** | `SolicitacaoServiceTest.testAtualizarSolicitacao()` |






## Avaliação dos Testes de Software

A avaliação dos testes do sistema de gerenciamento da retirada da CNH revelou importantes insights sobre a estrutura e a lógica de negócio da aplicação desenvolvida para a autoescola. Os testes abrangeram aspectos críticos como gestão de carga horária, agendamento de horários de prática, indicadores de desempenho, reposição de provas e solicitações de serviços. Estes nos ajudam a ter um controle maior da qualidade do software, prevenindo o descobrimento de erros em tempo de entrega e/ou tarde demais no desenvolvimento. Eles ajudam apontando diretamente onde os bugs estão localizados e quais ações necessárias.

#### Resultados e Pontos Identificados

Os testes destacaram pontos fortes na precisão do cálculo da carga horária dos alunos e na eficiência do agendamento de horários de prática. No entanto, foram identificados pontos que necessitam de correção e aprimoramento no fluxo do sistema e na lógica de negócio. Isso incluiu problemas identificados na sequência correta de operações em determinados processos, inconsistências na manipulação de dados e na validação de entradas.

#### Estratégias para Próximas Iterações

Para abordar esses pontos, o grupo planeja focar em melhorias específicas:

- **Correção de Fluxo do Sistema**: Revisar e ajustar a sequência de operações para garantir que o sistema execute corretamente em todas as situações.

- **Aprimoramento da Lógica de Negócio**: Refinar as regras e validações para garantir consistência e precisão nos resultados.

- **Revisão e Melhoria de Processos**: Identificar e corrigir inconsistências nos processos de integração entre os diferentes módulos do sistema.

#### Melhorias Implementadas

As falhas detectadas durante os testes levaram a melhorias significativas no sistema, incluindo ajustes na sequência de operações para melhorar a lógica de fluxo do sistema e garantir que todas as etapas sejam executadas na ordem correta. Além disso, houve uma revisão detalhada das regras de negócio e validações para assegurar que todas as condições sejam adequadamente verificadas e tratadas.

Essas ações visam garantir um sistema mais consistente, confiável e alinhado com as necessidades operacionais da autoescola durante o processo de retirada da CNH.


