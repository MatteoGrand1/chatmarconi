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
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClient {

    BufferedReader in;
    OutputStream dOut;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    String name;
    String currentTopic ="";
    byte zero = 0;
    byte sub = 1;
    byte unsub = 2;
    byte mes = 3;
    byte tlrq = 4;
    byte tl = 5;
    ArrayList<String> topicList = new ArrayList<>();

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public ChatClient() {

        // Layout GUI
        textField.setEditable(true);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    String sdvb= textField.getText();
                    dOut.write(msgSend(currentTopic, name, sdvb));
                    dOut.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                textField.setText("");
            }
        });
    }

    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Prompt for and return the desired screen name.
     */
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
    private String getMess() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
    
    public void sub(String t) throws IOException{
        byte[] s = new byte[512];
        int i = 0;
        s[i++]= sub;
        for (byte b: t.getBytes()){
            s[i++] = b;
        }
        s[i++]=zero;
        dOut.write(s);
    }
    
    
    public void unsub(String t) throws IOException{
        byte[] s = new byte[512];
        int i = 0;
        s[i++]= unsub;
        for (byte b: t.getBytes()){
            s[i++] = b;
        }
        s[i++]=zero;
        dOut.write(s);
    }
    
    
    public byte[] msgSend(String t, String u, String s) {
        byte[] p = new byte[1024];
        int i = 0;
        p[i++] = mes;
        for (byte b : t.getBytes()) {
            p[i++] = b;
        }
        p[i++] = zero;
        for (byte b1 : u.getBytes()) {
            p[i++] = b1;
        }
        p[i++] = zero;
        for (byte b2 : s.getBytes()) {
            p[i++] = b2;
        }
        p[i++] = zero;
        return p;
    }
    
    public void tlrq() throws IOException{
        byte[] c9 = new byte[512];
        int i = 0;
        c9[i++]= tlrq;
        dOut.write(c9);
    }
    
    public void DoS(String s) throws IOException{
        byte[] c9 = new byte[512];
        int i = 0;
        c9[i++]= tlrq;
        while(true){
            dOut.write(c9);
        }
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    private void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 1502);
        this.dOut = socket.getOutputStream();
        sub("");
        this.name = getName();
        String mess=getMess();
        for(int i =0; i<100; i++){
        dOut.write(msgSend(currentTopic, this.name, mess));
        dOut.flush();
        }
        
        while (true) {
            this.in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            byte b1 = (byte) this.in.read();
            if(b1 == mes){
                int current;
                ByteArrayOutputStream d = new ByteArrayOutputStream();
                while((current = this.in.read())!=0){
                    d.write(current);
                }
                new String(d.toByteArray());
                while((current = this.in.read())!=0){
                    d.write(current);
                }
                new String(d.toByteArray());
                while((current = this.in.read())!=0){
                    d.write(current);
                }
                new String(d.toByteArray());
                messageArea.append(new String(d.toByteArray()));
                messageArea.append("\n");
                this.in.read();
                
            }
            else if (b1 == zero){
                System.out.println("Version: " + Byte.toString((byte)this.in.read()));
            }
            else if (b1 == tl){                
                for(int i =1; b1.length>i; i++){
                    if (b1 == zero){
                        continue;
                    }else{
                        topicList.add(Byte.toString(b[i]));
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
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
        
    }
    


}