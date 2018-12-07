package Manager;

import DataAccess.TopicChatDataAccess;
import DataAccess.UserChatDataAccess;
import ObjectContainer.ChatContainerObject;
import models.TopicchatEntity;
import models.UserchatEntity;

public class ChatManager {

    private UserChatDataAccess userChatDataAccess;

    private TopicChatDataAccess topicChatDataAccess;


    public void save(ChatContainerObject chatContainerObject) {
        UserchatEntity userchatEntity = new UserchatEntity();
        if (userchatEntity == null ) {
            userchatEntity.setUsername(chatContainerObject.getUsername());
            userchatEntity.setUsernameH(chatContainerObject.getUsername_h());
            userChatDataAccess.persit(userchatEntity);
            chatContainerObject.setUserId(userchatEntity.getId());
        } else {
            userchatEntity.setUsername(chatContainerObject.getUsername());
            userChatDataAccess.merge(userchatEntity);
        }

        TopicchatEntity topicchatEntity = new TopicchatEntity();
        if (topicchatEntity == null) {
            topicchatEntity.setTopicname(chatContainerObject.getUsername());
            topicchatEntity.setUserid(userchatEntity.getId());
            topicChatDataAccess.persit(topicchatEntity);
        }
    }
}
