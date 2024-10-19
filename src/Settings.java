import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings extends JFrame implements ActionListener {
    public JLabel label , currPLabel, newPLabel,conPLabel;
    JButton change ,cancel;
    JPasswordField currentPass, newPass, confirmPass;
    public static String currP, newP,conP;



    Settings(){
        this.setSize(300,300);
        this.setVisible(true);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Setting");

        label = new JLabel("Change Password");
        label.setVisible(true);
        label.setBounds(70,20,200,25);
        label.setFont(new Font("Arial", Font.PLAIN, 20));

        currPLabel = new JLabel("Current Password");
        currPLabel.setVisible(true);
        currPLabel.setBounds(50,57,150,12);
        currPLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        newPLabel = new JLabel("New Password");
        newPLabel.setVisible(true);
        newPLabel.setBounds(50,107,150,13);
        newPLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        conPLabel = new JLabel("Confirm Password");
        conPLabel.setVisible(true);
        conPLabel.setBounds(50,157,150,12);
        conPLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        currentPass = new JPasswordField("currpass");
        currentPass.setVisible(true);
        currentPass.setBounds(50, 70, 200, 30);
        currentPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentPass.setText("");
            }

        });

        newPass = new JPasswordField("password");
        newPass.setVisible(true);
        newPass.setBounds(50, 120, 200, 30);
        newPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newPass.setText("");
            }

        });

        confirmPass = new JPasswordField("drowssap");
        confirmPass.setVisible(true);
        confirmPass.setBounds(50, 170, 200, 30);
        confirmPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmPass.setText("");
            }

        });

        change = new JButton("Confirm");
        change.setVisible(true);
        change.setFocusable(false);
        change.addActionListener(this);
        change.setBounds(190, 220, 80, 30);

        cancel = new JButton("Cancel");
        cancel.setVisible(true);
        cancel.setFocusable(false);
        cancel.addActionListener(this);
        cancel.setBounds(30, 220, 80, 30);



        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(currPLabel);
        this.add(currentPass);
        this.add(newPLabel);
        this.add(newPass);
        this.add(conPLabel);
        this.add(confirmPass);
        this.add(change);
        this.add(cancel);
        this.add(label);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==change){

            currP = new String(currentPass.getPassword());
            newP = new String(newPass.getPassword());
            conP = new String(confirmPass.getPassword());

            if(currP.equals("") || newP.equals("") || conP.equals("")){
                JOptionPane.showMessageDialog(null, "All fields must be fill", "Missing field",
                        JOptionPane.WARNING_MESSAGE);

            }else {
                if (!currP.equals(DatabaseConnection.password)){
                    currPLabel.setForeground(Color.red);
                    JOptionPane.showMessageDialog(null, "Wrong Password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    currPLabel.setForeground(Color.green);
                    if (newP.equals(conP)){
                        currPLabel.setForeground(Color.black);
                        newPLabel.setForeground(Color.black);
                        conPLabel.setForeground(Color.black);
                        DatabaseConnection.updatePass();
                        Dashboard.enableButn();
                        this.dispose();

                    }else {
                        newPLabel.setForeground(Color.red);
                        conPLabel.setForeground(Color.red);
                        JOptionPane.showMessageDialog(null, "Password don't Match", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }


        }if(e.getSource()==cancel){
            Dashboard.enableButn();
            this.dispose();
        }
    }
}
