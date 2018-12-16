package ChatFrontEnd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatUi extends JFrame {

    private List<String> topic;

    private List<String> topicSubbed;

    private JTextField viewName;


    public ChatUi(String title) {
        super("Chat");

        topic = new ArrayList<>();
        JPanel panelLeft = new JPanel(new BorderLayout());
        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelRight = new JPanel();

        JPanel userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints  constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        viewName = new JTextField();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        userPanel.add(viewName);

        panelLeft.add(userPanel, BorderLayout.NORTH);

        userPanel.setBorder(new LineBorder(Color.GRAY));
        userPanel.setPreferredSize(new Dimension(50,50));
        userPanel.setSize(50, 50);

        JButton addTopicButton = new JButton("Add Topic");

        JList topicJlist = new JList(topic.toArray());

        addTopicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameTopic = JOptionPane.showInputDialog("Create topic");
                topic.add(nameTopic);
                topicJlist.setListData(topic.toArray());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTopicButton);

        panelCenter.add(buttonPanel, BorderLayout.NORTH);

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

    public String getViewName() {
        return viewName.getText().trim();
    }

    public void setViewName(String viewName) {
       viewName = this.viewName.getText();
    }


}
