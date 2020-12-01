package Level2.Chat;

import javafx.application.Application;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Gui extends JFrame {
    private JTextArea history = new JTextArea();
    private JTextField edit = new JTextField();
    private JButton submit = new JButton("submit");

    public Gui() {
        setTitle("Uber chat via network");
        setBounds(300, 300 , 600, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.add(edit, BorderLayout.CENTER);
        bottom.add(submit, BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane(history);
        history.setLineWrap(true);
        history.setBorder(new BevelBorder(BevelBorder.LOWERED));
        history.setEditable(false);

        ActionListener upendingAction = x -> {
            history.setText(history.getText() + edit.getText() + "\n\n");
            edit.setText("");
        };
        submit.addActionListener(upendingAction);
        edit.addActionListener(upendingAction);
        submit.setFocusable(false);

        add(bottom, BorderLayout.SOUTH);
        add(scroll, BorderLayout.CENTER);
        setJMenuBar(makeMainMenu());

        setVisible(true);
        edit.requestFocusInWindow();
    }

    private JMenuBar makeMainMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("?");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(x -> System.out.println("Думай что всплыло модальное окно"));
        menu1.add(about);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(x -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        menuBar.add(exit);
        menuBar.add(menu1);
        return menuBar;
    }


}
