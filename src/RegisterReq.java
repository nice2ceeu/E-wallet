import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterReq extends JFrame implements ActionListener {

    JLabel regLabel,firstNameLabel, lastNameLabel, emailLabel, userNameLabel, passwordLabel, ageLabel, sexLabel;


    JTextField fNameField, lNameField, emailField, uNameField, ageField ;
    JPasswordField passwordField;

    ButtonGroup sexRadio;
    JRadioButton male = new JRadioButton("Male");
    JRadioButton female = new JRadioButton("Female");
    JButton submit, cancel;

    public static String fName,lName,eMail ,uName,pWord;
    public static int age;
    public static String sex = "";

    JPanel panel = new JPanel();
    RegisterReq(){
        this.setSize(500,430);

        panel.setVisible(true);
        panel.setBackground(new Color(28, 35, 112));
        panel.setBounds(40,35,400,345);
        panel.setLayout(null);

        regLabel = new JLabel("Creating Account");
        regLabel.setVisible(true);
        regLabel.setForeground(new Color(28,35,112));
        regLabel.setFont(new Font("arial", Font.PLAIN, 20));
        regLabel.setBounds(150, 10, 200, 20);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setVisible(true);
        firstNameLabel.setForeground(Color.yellow);
        firstNameLabel.setFont(new Font("arial", Font.PLAIN, 12));
        firstNameLabel.setBounds(40, 0, 150, 20);

        fNameField = new JTextField("Juan");
        fNameField.setVisible(true);
        fNameField.setBounds(40, 20, 150, 30);
        fNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        fNameField.setCaretColor(Color.GRAY);
        fNameField.setCaretPosition(fNameField.getText().length());
        fNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fNameField.setText("");
            }
        });


        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setVisible(true);
        lastNameLabel.setForeground(Color.yellow);
        lastNameLabel.setFont(new Font("arial", Font.PLAIN, 12));
        lastNameLabel.setBounds(215, 0, 150, 20);

        lNameField = new JTextField("Dela Cruz");
        lNameField.setVisible(true);
        lNameField.setBounds(215, 20, 150, 30);
        lNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        lNameField.setCaretColor(Color.GRAY);
        lNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lNameField.setText("");
            }
        });


        emailLabel = new JLabel("Email");
        emailLabel.setVisible(true);
        emailLabel.setForeground(Color.yellow);
        emailLabel.setFont(new Font("arial", Font.PLAIN, 12));
        emailLabel.setBounds(40, 50, 150, 20);

        emailField = new JTextField("JuanDelaCruz@gmail.com");
        emailField.setVisible(true);
        emailField.setBounds(40, 70, 320, 30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 12));
        emailField.setCaretColor(Color.GRAY);
        emailField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                emailField.setText("");
            }
        });


        userNameLabel = new JLabel("Username");
        userNameLabel.setVisible(true);
        userNameLabel.setForeground(Color.yellow);
        userNameLabel.setFont(new Font("arial", Font.PLAIN, 12));
        userNameLabel.setBounds(40, 100, 150, 20);

        uNameField = new JTextField("JDelaCruz123");
        uNameField.setVisible(true);
        uNameField.setBounds(40, 120, 320, 30);
        uNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        uNameField.setCaretColor(Color.GRAY);
        uNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uNameField.setText("");
            }
        });

        passwordLabel = new JLabel("Password");
        passwordLabel.setVisible(true);
        passwordLabel.setForeground(Color.yellow);
        passwordLabel.setFont(new Font("arial", Font.PLAIN, 12));
        passwordLabel.setBounds(40, 150, 150, 20);

        passwordField = new JPasswordField("password");
        passwordField.setVisible(true);
        passwordField.setBounds(40, 170, 320, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.setCaretColor(Color.GRAY);
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setText("");
            }

        });

        ageLabel = new JLabel("Age");
        ageLabel.setVisible(true);
        ageLabel.setForeground(Color.yellow);
        ageLabel.setFont(new Font("arial", Font.PLAIN, 12));
        ageLabel.setBounds(40, 200, 150, 20);

        ageField = new JTextField("18");
        ageField.setVisible(true);
        ageField.setBounds(40, 220, 320, 30);
        ageField.setFont(new Font("Arial", Font.PLAIN, 12));
        ageField.setCaretColor(Color.GRAY);
        ageField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ageField.setText("");
            }
        });

        sexLabel = new JLabel("Sex");
        sexLabel.setVisible(true);
        sexLabel.setForeground(Color.yellow);
        sexLabel.setFont(new Font("arial", Font.PLAIN, 12));
        sexLabel.setBounds(40, 250, 150, 20);



        sexRadio = new ButtonGroup();
        male.setBounds(40,270,100,30);
        male.setFocusable(false);
        male.setForeground(Color.yellow);
        male.addActionListener(this);
        male.setBackground(new Color(28, 35, 112));

        female.setBounds(155,270,100,30);
        female.setFocusable(false);
        female.setForeground(Color.yellow);
        female.addActionListener(this);
        female.setBackground(new Color(28, 35, 112));
        sexRadio.add(male);
        sexRadio.add(female);



        submit = new JButton("Submit");
        submit.setFocusable(false);
        submit.setVisible(true);
        submit.setBounds(260, 300, 100, 30);
        submit.addActionListener(this);

        cancel = new JButton("Exit");
        cancel.setFocusable(false);
        cancel.setVisible(true);
        cancel.setBounds(40, 300, 100, 30);
        cancel.addActionListener(this);


        this.setTitle("Registration");
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(regLabel);

        panel.add(firstNameLabel);
        panel.add(lastNameLabel);
        panel.add(emailLabel);
        panel.add(userNameLabel);
        panel.add(passwordLabel);
        panel.add(ageLabel);
        panel.add(sexLabel);

        panel.add(fNameField);
        panel.add(lNameField);
        panel.add(emailField);
        panel.add(uNameField);
        panel.add(passwordField);
        panel.add(ageField);
        panel.add(male);
        panel.add(female);

        panel.add(submit);
        panel.add(cancel);
        this.add(panel);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);




    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==submit){

            fName = fNameField.getText();
            lName = lNameField.getText();
            eMail = emailField.getText();
            uName = uNameField.getText();
            pWord=String.valueOf(passwordField.getPassword());
            ageField.getText();


            if (fName.equals("") ||lName.equals("") || eMail.equals("") || uName.equals("") || ageField.getText().equals("") || pWord.equals("") || sex.equals("") ){
                JOptionPane.showMessageDialog(null, "All fields must be fill", "Missing field",
                        JOptionPane.WARNING_MESSAGE);
            }else {
                if (!eMail.contains("@")) {
                    JOptionPane.showMessageDialog(null, "Email is not valid", "Invalid Email",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        age = Integer.parseInt(ageField.getText());
                        DatabaseConnection.registrationRequest();
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Age must be number", "Input Invalid",
                                JOptionPane.WARNING_MESSAGE);
                    }


                }
            }


        }else if (e.getSource()==cancel){
            fName = "";
            lName = "";
            eMail = "";
            uName = "";
            age = 0;
            pWord = "";
            sex = "";

            SwingCarousel.frontUIEnable();
            this.dispose();

        }if (e.getSource()==male){
            sex = "Male";
        }else if (e.getSource()==female){
            sex = "Female";

        }
    }
}
