package Level2.Chat;

import Level2.InfallibleCodeChat.Client.ChatClientEvents;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.javafx.scene.text.TextLine;
import javafx.application.Application;
import sun.awt.InputMethodSupport;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Date;
import java.util.function.Consumer;

public class ChatGui extends JFrame implements ChatLog, ChatClientEvents {
    private JTextArea history = new JTextArea();
    private JTextField edit = new JTextField();
    private JButton submit = new JButton("submit");
    private Consumer<String> doOnSubmit;
    private BufferedWriter logFile;

    public ChatGui(Consumer<String> doOnSubmit) {
        this.doOnSubmit = doOnSubmit;
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
            doOnSubmit.accept(edit.getText());
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

        JMenu menuFile = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        menuFile.add(exit);
        exit.addActionListener(x -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        menuBar.add(menuFile);
        menuBar.add(menu1);
        return menuBar;
    }

    public void onIncoming(String message) {
        String newText = String.format("[%s]\n%s\n\n", new Date(), message);
        SwingUtilities.invokeLater(() -> history.append(newText));
        if (logFile != null) {
            try {
                logFile.write(newText);
                logFile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileData(FileReader file) {
        StringBuilder sb = new StringBuilder(1024);
        char buffer[] = new char[1024];

        try {
            do {
                int count = file.read(buffer);
                if (count <= 0) break;
                sb.append(buffer, 0, count);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public void onLoggedIn(String login, String nickname) {
        String logFileName = "history_" + login + ".txt";

        try(FileReader logReader = new FileReader(logFileName)) {
            String logText = getFileData(logReader);
            SwingUtilities.invokeLater(() -> {
                history.setText(logText);
                history.setCaretPosition(logText.length());
            });

            logFile = new BufferedWriter(new FileWriter(logFileName, true));
        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
        }

        SwingUtilities.invokeLater(() -> setTitle(nickname));
    }

    public void onError(Throwable e) {
        onIncoming(String.format("[exception] %s, %s", e.getClass().getSimpleName(), e.getMessage()));
    }
}
