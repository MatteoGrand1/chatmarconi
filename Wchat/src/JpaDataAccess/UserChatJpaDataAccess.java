package JpaDataAccess;

import DataAccess.UserChatDataAccess;
import models.UserchatEntity;

import javax.persistence.*;

public class UserChatJpaDataAccess implements UserChatDataAccess {

    private static final String PERSISTENCE_UNIT_NAME = "DEFAULT-PU";

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory
            (PERSISTENCE_UNIT_NAME);
    private EntityManager entityManager = entityManagerFactory.createEntityManager();


    @Override
    public void persit(UserchatEntity userchatEntity) {

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(userchatEntity);
            userchatEntity.setId(userchatEntity.getId());
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
        } catch (EntityExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void merge(UserchatEntity userchatEntity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(userchatEntity);
            userchatEntity.setId(userchatEntity.getId());
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
        } catch (EntityExistsException e) {
            e.printStackTrace();
        }
    }
}
