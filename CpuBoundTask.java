package com.manus.threads;

/**
 * Tarefa computacional simples para simular carga de CPU.
 * Cada thread executará esta tarefa.
 */
public class CpuBoundTask implements Runnable {
    private static final long ITERATIONS = 10_000_000L;

    @Override
    public void run() {
        double result = 0;
        for (long i = 0; i < ITERATIONS; i++) {
            // Simulação de cálculo pesado: raiz quadrada e multiplicação
            result += Math.sqrt(i) * Math.sin(i);
        }
        // Evita que o compilador otimize demais o loop, garantindo que o cálculo seja feito
        if (result == Double.MAX_VALUE) {
            System.out.println("Should not happen: " + result);
        }
    }
}

