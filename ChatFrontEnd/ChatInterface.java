package ChatFrontEnd;

import javax.swing.*;
import java.awt.*;

public class ChatInterface extends JFrame{

    public static void main(String[] args) {
        ChatUi chatFrame = new ChatUi("Chat");
        LoginChatDialog loginChat = new LoginChatDialog(chatFrame);
        loginChat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loginChat.setVisible(true);

        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(600, 600);
        String n;
        n = loginChat.getTextUsername();
        if (loginChat.getButtonLogin().getModel().isPressed()) {
            chatFrame.setVisible(true);
        } else {
            chatFrame.dispose();
        }

    }
}
