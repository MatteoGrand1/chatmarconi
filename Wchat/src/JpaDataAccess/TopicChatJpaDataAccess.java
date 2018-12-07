package JpaDataAccess;

import DataAccess.TopicChatDataAccess;
import models.TopicchatEntity;

import javax.persistence.*;

public class TopicChatJpaDataAccess implements TopicChatDataAccess {

    private static final String PERSISTENCE_UNIT_NAME = "DEFAULT-PU";

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory
            (PERSISTENCE_UNIT_NAME);
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void persit(TopicchatEntity topicchatEntity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(topicchatEntity);
            int id = topicchatEntity.getId();
            topicchatEntity.setId(id);
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

}
