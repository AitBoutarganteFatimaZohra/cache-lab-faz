package com.faz.cache.service;

import com.faz.cache.modele.AuteurFaz;
import com.faz.cache.modele.LivreFaz;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Service pour initialiser les donnees de test.
 * Cree 10 auteurs, chacun avec 5 livres.
 */
public class DataInitFazService {

    private final EntityManagerFactory emfFaz;
    private final EntityManager gestionnaireEntiteFaz;

    public DataInitFazService() {
        this.emfFaz = Persistence.createEntityManagerFactory("UniteCacheFaz");
        this.gestionnaireEntiteFaz = emfFaz.createEntityManager();
    }

    public void initDataFaz() {
        EntityTransaction txFaz = gestionnaireEntiteFaz.getTransaction();
        try {
            txFaz.begin();

            for (int i = 1; i <= 10; i++) {
                AuteurFaz auteurFaz = new AuteurFaz("Auteur " + i);

                for (int j = 1; j <= 5; j++) {
                    LivreFaz livreFaz = new LivreFaz("Livre " + j + " de l'auteur " + i);
                    auteurFaz.ajouterLivreFaz(livreFaz);
                }

                gestionnaireEntiteFaz.persist(auteurFaz);
            }

            txFaz.commit();
            System.out.println("✅ 10 auteurs et 50 livres crees.");

        } catch (RuntimeException e) {
            if (txFaz.isActive()) txFaz.rollback();
            throw e;
        }
    }

    public void fermerFaz() {
        gestionnaireEntiteFaz.close();
        emfFaz.close();
    }
}