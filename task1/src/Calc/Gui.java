package Calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    JTextField inputText;
    JLabel pushedValueText;
    double pushedValue = Double.NaN;
    CalcOperation operation = null;

    public Gui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculator");
        setBounds(100, 100, 500, 500);
        setLayout(new BorderLayout());

        JPanel topBox = new JPanel();
        topBox.setLayout(new BoxLayout(topBox, BoxLayout.Y_AXIS));
        add(topBox, BorderLayout.NORTH);

        inputText = new JTextField("");
        Font inputFont = new Font(Font.DIALOG_INPUT, 0,24);
        inputText.setFont(inputFont);
        inputText.setEditable(false);
        topBox.add(inputText);

        pushedValueText = new JLabel("NaN");
        Font pushedFont = new Font(Font.DIALOG_INPUT, 0, 16);
        pushedValueText.setFont(pushedFont);
        pushedValueText.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topBox.add(pushedValueText, BorderLayout.NORTH);


        Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 24);
        JPanel numPad = new JPanel();
        numPad.setLayout(new GridLayout(4, 3));
        add(numPad, BorderLayout.CENTER);

        ActionListener numPadAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputText.setText(inputText.getText() + ((JButton)e.getSource()).getLabel());
            }
        };

        String[] numNames = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "", "0", ""
        };

        for (String name: numNames) {
            JButton button = new JButton(name);
            button.setFont(buttonFont);
            button.setPreferredSize(new Dimension(70, 70));
            button.setEnabled(!name.isEmpty());
            button.addActionListener(numPadAction);
            numPad.add(button);
        }

        ActionListener operAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callOp(((JButton)e.getSource()).getLabel().charAt(0));
            }
        };

        JPanel operPad = new JPanel();
        add(operPad, BorderLayout.EAST);
        String[] opNames = {"C", "+", "-", "*", "/", "="};
        operPad.setLayout(new GridLayout(opNames.length, 1));

        for (String name : opNames) {
            JButton opButton = new JButton(name);
            opButton.setFont(buttonFont);
            opButton.setPreferredSize(new Dimension(70, 70));
            opButton.addActionListener(operAction);
            operPad.add(opButton);
        }

        ClearGui();
        setVisible(true);
    }



    private void callOp(char operId) {
        if (operId == 'C') {
            ClearGui();
            return;
        }

        double b = Double.NaN;
        try {
            b = Double.valueOf(inputText.getText());
        } catch (Exception e) {
        }

        if (Double.isNaN(b)) return;

        double currentValue = pushedValue;
        if (operation != null&&!Double.isNaN(currentValue)) {
            currentValue = operation.perform(currentValue, b);
        } else {
            currentValue = b;
        }
        UpdateGui(currentValue, CalcOperation.getOperationById(operId));
    }

    private void ClearGui() {
        UpdateGui(Double.NaN, null);
    }


    private void UpdateGui(double pushed, CalcOperation operation) {
        inputText.setText("");
        pushedValue = pushed;
        pushedValueText.setText(String.valueOf(pushedValue));
        this.operation = operation;
    }
}
