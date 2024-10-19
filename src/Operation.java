import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Operation extends JFrame implements ActionListener {


    public static JLabel opr;
    JButton cancel, confirm;

    public static JTextField input;
    public static JTextField receiver;
    public static String transactType;
    public static Integer inputAmount;
    public static String inputRecipient;

    Operation() {
        DatabaseConnection.getConnection();

        opr = new JLabel("Operation");
        opr.setForeground(Color.black);
        opr.setFont(new Font("Arial", Font.PLAIN, 20));
        opr.setVisible(true);
        opr.setBounds(100, 30, 100, 30);


        cancel = new JButton("Cancel");
        cancel.setVisible(true);
        cancel.setFocusable(false);
        cancel.addActionListener(this);
        cancel.setBounds(10, 220, 80, 30);

        confirm = new JButton("Confirm");
        confirm.setVisible(true);
        confirm.setFocusable(false);
        confirm.addActionListener(this);
        confirm.setBounds(195, 220, 80, 30);


        input = new JTextField("Enter Amount");
        input.setVisible(true);
        input.setBounds(50, 130, 200, 30);
        input.setCaretPosition(input.getText().length());
        input.setHorizontalAlignment(SwingConstants.CENTER);
        input.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                input.setText("");
            }

        });

        receiver = new JTextField("Name of recipient");
        receiver.setVisible(false);
        receiver.setBounds(50, 70, 200, 30);
        receiver.setCaretPosition(receiver.getText().length());
        receiver.setHorizontalAlignment(SwingConstants.CENTER);
        receiver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                receiver.setText("");
            }

        });


        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("Transaction Operation");
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);


        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.add(opr);
        this.add(cancel);
        this.add(confirm);
        this.add(input);

        this.add(receiver);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            Dashboard.enableButn();
            this.dispose();


        } else if (e.getSource() == confirm) {
            transactType = opr.getText();

            switch (transactType) {
                case "Deposit":
                    int response = JOptionPane.showConfirmDialog(null, "Confirm Transaction", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (response == 1) {
                        input.setText("Set Amount");
                    } else {
                        try {
                            inputAmount = Integer.parseInt(input.getText());
                            if (inputAmount < 100) {
                                JOptionPane.showMessageDialog(null, "Must exceed 100 Php", "Doesn't meet Minimum",
                                        JOptionPane.WARNING_MESSAGE);

                            } else {

                                DatabaseConnection.balance += inputAmount;
                                DatabaseConnection.updateTransact();
                                Dashboard.balanceLabel.setText("Balance: " + DatabaseConnection.balance + " Php");
                                input.setText("Set Amount");
                                JOptionPane.showMessageDialog(null, "Deposit Success", "Transaction",
                                        JOptionPane.INFORMATION_MESSAGE);
                                Dashboard.enableButn();
                                this.dispose();
                            }
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "Invalid Inputs", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    break;

                case "Withdraw":
                    int response1 = JOptionPane.showConfirmDialog(null, "Confirm Transaction", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (response1 == 1) {
                        input.setText("Set Amount");
                    } else {
                        try {
                            inputAmount = Integer.parseInt(input.getText());
                            if (inputAmount > DatabaseConnection.balance) {
                                JOptionPane.showMessageDialog(null, "Insufficient Balance", "Not Enough", JOptionPane.WARNING_MESSAGE);
                            } else if (inputAmount < 100) {
                                JOptionPane.showMessageDialog(null, "Must exceed 100 Php", "Minimum", JOptionPane.WARNING_MESSAGE);

                            } else {
                                DatabaseConnection.balance -= inputAmount;
                                DatabaseConnection.updateTransact();
                                Dashboard.balanceLabel.setText("Balance: " + DatabaseConnection.balance + " Php");
                                input.setText("Set Amount");
                                JOptionPane.showMessageDialog(null, "Withdrawal Success", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                                Dashboard.enableButn();
                                this.dispose();
                            }
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "Invalid Inputs", "Error", JOptionPane.ERROR_MESSAGE);

                        }

                    }
                    break;
                case "Transfer":
                    if (receiver.getText().equals("") || input.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Fields must be filled", "Field/s Missing", JOptionPane.WARNING_MESSAGE);

                    } else {
                        int response2 = JOptionPane.showConfirmDialog(null, "Confirm Transaction", "Confirm", JOptionPane.YES_NO_OPTION);

                        if (response2 == 1) {
                            receiver.setText("Name of Recipient");
                            input.setText("Enter Amount");
                        } else {
                            DatabaseConnection.checkUsers();
                            if (DatabaseConnection.userExist1){
                                DatabaseConnection.checkSelf();

                            }

                        }

                    }
            }
        }
    }
}

