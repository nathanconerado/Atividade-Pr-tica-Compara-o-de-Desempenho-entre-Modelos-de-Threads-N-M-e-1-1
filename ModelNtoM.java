package com.manus.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simulação do modelo de threads N:M.
 * N tarefas (threads de usuário) são mapeadas para M threads do sistema
 * usando um ExecutorService com um pool de threads fixo.
 */
public class ModelNtoM {

    // Define o número de threads do sistema (M).
    // Geralmente é o número de núcleos de CPU disponíveis.
    private static final int SYSTEM_THREADS = Runtime.getRuntime().availableProcessors();

    public static long runTest(int numUserThreads) throws InterruptedException {
        // Cria um pool de threads fixo (M threads do sistema)
        ExecutorService executor = Executors.newFixedThreadPool(SYSTEM_THREADS);
        CpuBoundTask task = new CpuBoundTask();

        long startTime = System.currentTimeMillis();

        // 1. Distribuição das N tarefas (threads de usuário)
        for (int i = 0; i < numUserThreads; i++) {
            executor.execute(task);
        }

        // 2. Aguarda a conclusão de todas as tarefas
        executor.shutdown();
        // Espera no máximo 1 hora para todas as tarefas terminarem
        executor.awaitTermination(1, TimeUnit.HOURS);

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java ModelNtoM <numero_de_threads_usuario>");
            return;
        }
        try {
            int numUserThreads = Integer.parseInt(args[0]);
            long executionTime = runTest(numUserThreads);
            System.out.println("Modelo N:M com " + numUserThreads + " threads de usuário (M=" + SYSTEM_THREADS + "): " + executionTime + " ms");
        } catch (NumberFormatException e) {
            System.err.println("O argumento deve ser um número inteiro.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("O teste foi interrompido.");
        }
    }
}

