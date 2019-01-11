/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author 17398
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatClient extends JFrame {

    InputStream in;
    OutputStream dOut;
    JTextField jTextField = new JTextField();
    JTextArea textArea = new JTextArea();
    private JTextField viewName;
    String name;
    String currentTopic = "";
    byte zero = 0;
    byte sub = 1;
    byte unsub = 2;
    byte mes = 3;
    byte tlrq = 4;
    byte tl = 5;
    ArrayList<String> topicList;
    ArrayList<String> subbedTopicList = new ArrayList<>();

    static LoginChatDialog loginChat;

    /**
     * Constructs the client by laying out the GUI and registering a listener
     * with the textfield so that pressing Return in the listener sends the
     * textfield contents to the server. Note however that the textfield is
     * initially NOT editable, and only becomes editable AFTER the client
     * receives the NAMEACCEPTED message from the server.
     */
    public ChatClient() {
       

        topicList = new ArrayList<>();
        JPanel panelLeft = new JPanel(new BorderLayout());
        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelRight = new JPanel();

        JPanel userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints  constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        viewName = new JTextField();
        viewName.setText(name);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        userPanel.add(viewName);

        panelLeft.add(userPanel, BorderLayout.NORTH);

        userPanel.setBorder(new LineBorder(Color.GRAY));
        userPanel.setPreferredSize(new Dimension(50,50));
        userPanel.setSize(50, 50);

        JButton addTopicButton = new JButton("Add Topic");

        JList topicJlist = new JList(topicList.toArray());
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent listSelectionEvent) {
           int[] sel = topicJlist.getSelectedIndices();
                    for(int i = 0; i < sel.length; i++){
                        Object selected = topicJlist.getModel().getElementAt(sel[i]);
                        currentTopic=String.valueOf(selected);
                        textArea.setText(null);
                    }
      }
     };
         topicJlist.addListSelectionListener(listSelectionListener);
         
        addTopicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameTopic = JOptionPane.showInputDialog("Create topic");
                topicList.add(nameTopic);
                topicJlist.setListData(topicList.toArray());
                try {
                    sub(nameTopic);
                } catch (IOException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                currentTopic= nameTopic;
            }
        });
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTopicButton);

        panelCenter.add(buttonPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textArea = new JTextArea(30,50);
        textArea.setBorder(new LineBorder(Color.BLACK));
        textArea.setEditable(false);

        jTextField = new JTextField(50);
        jTextField.setSize(300,200);
        jTextField.setBounds(6, 78, 68, 14);

        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = jTextField.getText();
                    byte[]fs = msgSend(currentTopic, name, text);
                    byte[]fdgdf= Arrays.copyOfRange(fs, 0, 1 +currentTopic.length()+ 1+name.length()+1+text.length()+1);
                    dOut.write(fdgdf);
                    tlrq();
                    
                    //Arrays.toString(msgSend(currentTopic, name, text));
                    //dOut.flush();
                    jTextField.setText(null);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        textPanel.add(textArea);
        textPanel.add(new JScrollPane(jTextField) , BorderLayout.PAGE_END);




        panelCenter.add(textPanel, BorderLayout.CENTER);

        panelLeft.add(new JScrollPane (topicJlist), BorderLayout.CENTER);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelLeft, BorderLayout.WEST);
        getContentPane().add(panelCenter, BorderLayout.CENTER);
        getContentPane().add(panelRight, BorderLayout.EAST);

        panelLeft.setBorder(new LineBorder(Color.GRAY));
        panelLeft.setPreferredSize(new Dimension(150,150));
        panelLeft.setSize(100, 100);

        panelRight.setBorder(new LineBorder(Color.GRAY));
        panelRight.setPreferredSize(new Dimension(100,100));
        panelRight.setSize(100, 100);


    }



    /**
     * Prompt for and return the address of the server.
     */
 /*   private String getServerAddress() {
        return JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Chatter",
                JOptionPane.QUESTION_MESSAGE);
    }*/

    /**
     * Prompt for and return the desired screen name.
     */
/*    private String getNameC() {
        return JOptionPane.showInputDialog(
                frame,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
    }*/

    public void sub(String t) throws IOException {
        byte[] s = new byte[512];
        int i = 0;
        s[i++] = sub;
        for (byte b : t.getBytes("UTF-8")) {
            s[i++] = b;
        }
        s[i++] = zero;
        subbedTopicList.add(t);
        dOut.write(s, 0, 1 + t.length() + 1);
    }

    public void unsub(String t) throws IOException {
        byte[] s = new byte[512];
        int i = 0;
        s[i++] = unsub;
        for (byte b : t.getBytes("UTF-8")) {
            s[i++] = b;
        }
        s[i++] = zero;
        subbedTopicList.remove(t);
        dOut.write(s, 0, 1 + t.length() + 1);
    }

    public byte[] msgSend(String t, String u, String s) throws UnsupportedEncodingException {
        byte[] p = new byte[2048];
        int i = 0;
        p[i++] = mes;
        t= t.replaceAll("[^\\p{ASCII}]", "X");
        u= u.replaceAll("[^\\p{ASCII}]", "X");
        s= s.replaceAll("[^\\p{ASCII}]", "X");
        for (byte b : t.getBytes("UTF-8")) {
            p[i++] = b;
        }
        p[i++] = zero;
        for (byte b1 : u.getBytes("UTF-8")) {
            p[i++] = b1;
        }
        p[i++] = zero;
        for (byte b2 : s.getBytes("UTF-8")) {
            p[i++] = b2;
        }
        p[i++] = zero;
        return p;
    }

    public void tlrq() throws IOException {
        byte[] c9 = new byte[1];
        c9[0] = tlrq;
        dOut.write(c9);
    }

    public void DoS(String s) throws IOException {
        while (true) {
            sub("a");
            unsub("a");
            sub("b");
            unsub("b");
            sub("c");
            unsub("c");
            sub("d");
            unsub("d");
        }
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    private void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, 1502);
        this.dOut = socket.getOutputStream();
        topicList.add("");
        sub("");
        this.name = loginChat.getTextUsername();
        tlrq();
        
        //dOut.flush();

        while (true) {
            this.in = socket.getInputStream();
            byte b1 = (byte) this.in.read();
            if (b1 == mes) {
                int current;
                ByteArrayOutputStream d = new ByteArrayOutputStream();
                while ((current = this.in.read()) != 0) {
                    d.write(current);
                }
                String topic = new String(d.toByteArray());
                d = new ByteArrayOutputStream();
                while ((current = this.in.read()) != 0) {
                    d.write(current);
                }
                String username = new String(d.toByteArray());
                d = new ByteArrayOutputStream();
                while ((current = this.in.read()) != 0) {
                    d.write(current);
                }
                String message = new String(d.toByteArray());

                textArea.append(username + ": " + message);
                textArea.append("\n");
                //d.flush();

            } else if (b1 == zero) {
                System.out.println("Version: " + Byte.toString((byte) this.in.read()));
            } else if (b1 == tl) {
                while (this.in.available() > 0) {
                    ByteArrayOutputStream d1 = new ByteArrayOutputStream();
                    byte b2 = (byte) this.in.read();
                    if (b2 == zero) {
                        topicList.add(new String(d1.toByteArray()));
                        continue;
                    } else if (b2 != zero && b2 != tlrq){
                        d1.write(b2);
                    } else if(b2==tlrq){
                        break;
                    }
                }
            }
        }
    }


    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        //chatFrame = new ChatUi("Chat");
        
        loginChat = new LoginChatDialog(client);
        loginChat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loginChat.setVisible(true);

        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setSize(1020, 860);
        String n;
        n = loginChat.getTextUsername();
        if (loginChat.getButtonLogin().getModel().isPressed()) {
            client.setVisible(true);
        } else {
            client.dispose();
        }
        client.run();
        //client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //client.frame.setVisible(true);
        //client.run();

    }

}
