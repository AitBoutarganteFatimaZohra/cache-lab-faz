package com.faz.cache.presentation;

import com.faz.cache.service.DataInitFazService;
import com.faz.cache.service.PerformanceTestFazService;

public class LanceurCacheFaz {

    public static void main(String[] args) {
        System.out.println("=== TP OPTIMISATION DES REQUETES JPA ===\n");

        // 1. Initialisation des donnees
        System.out.println("--- Initialisation des donnees ---");
        DataInitFazService initServiceFaz = new DataInitFazService();
        initServiceFaz.initDataFaz();
        initServiceFaz.fermerFaz();

        // 2. Test N+1 (sans optimisation)
        System.out.println("\n--- Test SANS optimisation (probleme N+1) ---");
        PerformanceTestFazService perfService1Faz = new PerformanceTestFazService();
        perfService1Faz.testPerformanceSansCacheFaz();
        perfService1Faz.fermerFaz();

        // 3. Test avec JOIN FETCH
        System.out.println("\n--- Test avec JOIN FETCH ---");
        PerformanceTestFazService perfService2Faz = new PerformanceTestFazService();
        perfService2Faz.testPerformanceAvecJoinFetchFaz();
        perfService2Faz.fermerFaz();

        // 4. Test avec Entity Graph
        System.out.println("\n--- Test avec Entity Graph ---");
        PerformanceTestFazService perfService3Faz = new PerformanceTestFazService();
        perfService3Faz.testPerformanceAvecEntityGraphFaz();
        perfService3Faz.fermerFaz();

        System.out.println("\n=== FIN ===");
    }
}