package br.com.empresa.medbatch.factory;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class EMFactory {

    private EntityManagerFactory emf;

    private EntityManager factory() {
        if (emf == null) {
            emf = this.getEntityManagerFactory();
        }
        return emf.createEntityManager();
    }

    private void close(EntityManager em) {
        em.close();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("back");
    }

    public EntityManager getEntityManager() {
        return factory();
    }
}
