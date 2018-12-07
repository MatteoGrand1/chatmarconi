package models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


public class ConnectDB {

    private static final String PERSISTENCE_UNIT_NAME = "DEFAULT-PU";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory
                (PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        UserchatEntity userchatEntity = new UserchatEntity();
        userchatEntity.setUsername("mio");
        userchatEntity.setUsernameH("tuo");

        em.persist(userchatEntity);
        em.flush();
        em.clear();

        em.getTransaction().commit();
        em.close();

    }
}
