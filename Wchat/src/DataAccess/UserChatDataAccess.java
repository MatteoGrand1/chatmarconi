package DataAccess;

import models.UserchatEntity;

public interface UserChatDataAccess {

    public void persit (UserchatEntity userchatEntity);

    public void merge (UserchatEntity userchatEntity);
}
