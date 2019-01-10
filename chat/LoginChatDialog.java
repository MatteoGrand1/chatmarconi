package chat;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginChatDialog extends JDialog {

    private JTextField textUsername;
    private JLabel labelUsername;
    private JButton buttonLogin;
    private Boolean completed;
    public String a;

    public LoginChatDialog (Frame parent) {
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints  constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        labelUsername = new JLabel("User: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(labelUsername, constraints);

        textUsername = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(textUsername, constraints);
        panel.setBorder(new LineBorder(Color.GRAY));

        buttonLogin = new JButton("Join");

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getTextUsername() != null && !getTextUsername().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginChatDialog.this,
                            "Username Confirmed", "Login", JOptionPane.INFORMATION_MESSAGE);

                    completed = true;
                    setCompleted(completed);
                    buttonLogin.getModel().setPressed(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginChatDialog.this,
                            "Invalid username",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    textUsername.setText("");
                    completed = false;
                    setCompleted(completed);
                    buttonLogin.getModel().setPressed(true);
                }
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonLogin);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getTextUsername() {
        return textUsername.getText().trim();
    }

    public JButton getButtonLogin() {
        return buttonLogin;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


}
