package com.manus.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulação do modelo de threads 1:1 (Thread API padrão do Java).
 * Cada thread de usuário é mapeada diretamente para uma thread do sistema.
 */
public class Model1to1 {

    public static long runTest(int numThreads) throws InterruptedException {
        List<Thread> threads = new ArrayList<>(numThreads);
        CpuBoundTask task = new CpuBoundTask();

        long startTime = System.currentTimeMillis();

        // 1. Criação das threads
        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(task));
        }

        // 2. Início das threads
        for (Thread thread : threads) {
            thread.start();
        }

        // 3. Espera pela conclusão de todas as threads
        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Model1to1 <numero_de_threads>");
            return;
        }
        try {
            int numThreads = Integer.parseInt(args[0]);
            long executionTime = runTest(numThreads);
            System.out.println("Modelo 1:1 com " + numThreads + " threads: " + executionTime + " ms");
        } catch (NumberFormatException e) {
            System.err.println("O argumento deve ser um número inteiro.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("O teste foi interrompido.");
        }
    }
}

