# Atividade Prática: Comparação de Desempenho entre Modelos de Threads N:M e 1:1

## Objetivo

Desenvolver e comparar o desempenho de dois programas em Java que simulam os modelos de execução de threads **N:M** (Múltiplas Threads de Usuário para Múltiplas Threads do Sistema) e **1:1** (Uma Thread de Usuário para Uma Thread do Sistema).

## Implementação

A tarefa computacional escolhida foi um loop de cálculo intensivo em CPU (`CpuBoundTask.java`).

1.  **Modelo 1:1 (`Model1to1.java`):** Implementado usando a API `java.lang.Thread` padrão, onde cada thread de usuário corresponde a uma thread do sistema operacional.
2.  **Modelo N:M (`ModelNtoM.java`):** Implementado usando `java.util.concurrent.ExecutorService` com um pool de threads fixo, cujo tamanho é igual ao número de núcleos de CPU disponíveis. Isso simula o multiplexamento de N tarefas em M threads do sistema.

## Resultados dos Comparativos

Os testes foram realizados com 10, 100, 500 e 1000 threads de usuário, e o tempo total de execução foi medido em milissegundos (ms).

### Tabela de Desempenho

| Threads (N) | Modelo 1:1 (ms) | Modelo N:M (ms) |
|:---:|:---:|:---:|
| 10 | 1556 | 1756 |
| 100 | 8790 | 15953 |
| 500 | 41466 | 77660 |
| 1000 | 80986 | 80899 |

### Análise

*   **Baixa e Média Concorrência (10 a 500 threads):** O **Modelo 1:1** apresentou melhor desempenho. Isso ocorre porque, para tarefas intensivas em CPU, o mapeamento direto permite que o escalonador do SO otimize a execução. O Modelo N:M sofre com o *overhead* de multiplexação quando o número de tarefas é moderado.
*   **Alta Concorrência (1000 threads):** O **Modelo N:M** se igualou e superou ligeiramente o Modelo 1:1. O *overhead* de criar e gerenciar **1000 threads do sistema operacional** no Modelo 1:1 se torna proibitivo, causando uma degradação acentuada no desempenho. O Modelo N:M, ao limitar o número de threads do SO, demonstra melhor escalabilidade neste cenário.

**Conclusão:** O ponto de virada onde o Modelo N:M se torna mais vantajoso é em cenários de **altíssima concorrência**, onde o custo de gerenciamento de threads do sistema operacional supera os benefícios do mapeamento direto.
