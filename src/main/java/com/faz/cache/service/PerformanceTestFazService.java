package com.faz.cache.service;

import com.faz.cache.modele.AuteurFaz;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Service pour mesurer les performances.
 * Teste : sans optimisation (N+1), avec JOIN FETCH, avec Entity Graph.
 */
public class PerformanceTestFazService {

    private final EntityManagerFactory emfFaz;

    public PerformanceTestFazService() {
        this.emfFaz = Persistence.createEntityManagerFactory("UniteCacheFaz");
    }

    /**
     * Test SANS optimisation : provoque le probleme N+1.
     */
    public void testPerformanceSansCacheFaz() {
        EntityManager emFaz = emfFaz.createEntityManager();
        try {
            long debutFaz = System.currentTimeMillis();

            TypedQuery<AuteurFaz> queryFaz = emFaz.createQuery("SELECT a FROM AuteurFaz a", AuteurFaz.class);
            List<AuteurFaz> auteursFaz = queryFaz.getResultList();

            // Acces aux livres pour provoquer le chargement (N+1)
            for (AuteurFaz auteurFaz : auteursFaz) {
                auteurFaz.getLivresFaz().size();
            }

            long finFaz = System.currentTimeMillis();
            System.out.println("Durée sans optimisation (N+1) : " + (finFaz - debutFaz) + " ms");
        } finally {
            emFaz.close();
        }
    }

    /**
     * Test avec JOIN FETCH : une seule requete SQL.
     */
    public void testPerformanceAvecJoinFetchFaz() {
        EntityManager emFaz = emfFaz.createEntityManager();
        try {
            long debutFaz = System.currentTimeMillis();

            TypedQuery<AuteurFaz> queryFaz = emFaz.createQuery(
                    "SELECT DISTINCT a FROM AuteurFaz a JOIN FETCH a.livresFaz", AuteurFaz.class);
            List<AuteurFaz> auteursFaz = queryFaz.getResultList();

            for (AuteurFaz auteurFaz : auteursFaz) {
                auteurFaz.getLivresFaz().size();
            }

            long finFaz = System.currentTimeMillis();
            System.out.println("Durée avec JOIN FETCH : " + (finFaz - debutFaz) + " ms");
        } finally {
            emFaz.close();
        }
    }

    /**
     * Test avec Entity Graph : charge les auteurs et leurs livres
     * en une seule requete, sans modifier le JPQL.
     */
    public void testPerformanceAvecEntityGraphFaz() {
        EntityManager emFaz = emfFaz.createEntityManager();
        try {
            long debutFaz = System.currentTimeMillis();

            // 1. Creer l'Entity Graph (type <?> car la methode retourne EntityGraph<?>)
            EntityGraph<?> graphFaz = emFaz.createEntityGraph("AuteurFaz.livres");

            // 2. Creer la requete JPQL (inchangee)
            TypedQuery<AuteurFaz> queryFaz = emFaz.createQuery(
                    "SELECT a FROM AuteurFaz a", AuteurFaz.class);

            // 3. Appliquer l'Entity Graph via un hint
            queryFaz.setHint("javax.persistence.fetchgraph", graphFaz);

            // 4. Executer la requete
            List<AuteurFaz> auteursFaz = queryFaz.getResultList();

            // 5. Acceder aux livres (aucune requete supplementaire)
            for (AuteurFaz auteurFaz : auteursFaz) {
                auteurFaz.getLivresFaz().size();
            }

            long finFaz = System.currentTimeMillis();
            System.out.println("Durée avec Entity Graph : " + (finFaz - debutFaz) + " ms");
        } finally {
            emFaz.close();
        }
    }

    public void fermerFaz() {
        emfFaz.close();
    }
}