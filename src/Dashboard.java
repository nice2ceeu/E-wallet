import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Dashboard extends JFrame implements ActionListener {


    JPanel profilePanel;
    JLabel imageContainer;

    JLabel name, email,age,sex;
    public static JButton logout, settings;


    JPanel balancePanel;
    public static JLabel balanceLabel;
    public static JButton deposit, withdraw, transfer;


    JLabel transactLabel;
    public static JButton retrieve;


    JPanel historyPanel;



    public static String[] cols = {"Transact ID", "Operation","Amount", "Date and Time"};


    JScrollPane scrollPane;

    public static JTable table;


    Dashboard() {

        DefaultTableModel model = new DefaultTableModel(cols,0);


        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 515, 260);

        table.setModel(model);


        ImageIcon icon = new ImageIcon("C://Users/kristoffer/Desktop/Pik.jpg");
        Image image = icon.getImage();
        Image resized = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resized);


        transactLabel = new JLabel("Transaction History");
        transactLabel.setForeground(Color.yellow);
        transactLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        transactLabel.setVisible(true);
        transactLabel.setBounds(10, 10, 250, 30);


        retrieve = new JButton("History");
        retrieve.setVisible(true);
        retrieve.setFocusable(false);
        retrieve.addActionListener(this);
        retrieve.setBounds(435, 10, 100, 30);


        balanceLabel = new JLabel("Balance: " + DatabaseConnection.balance + " Php");
        balanceLabel.setForeground(Color.yellow);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        balanceLabel.setVisible(true);
        balanceLabel.setBounds(10, 10, 400, 70);


        deposit = new JButton("Deposit");
        deposit.setVisible(true);
        deposit.setFocusable(false);
        deposit.addActionListener(this);
        deposit.setBounds(20, 160, 100, 30);


        withdraw = new JButton("Withdraw");
        withdraw.setVisible(true);
        withdraw.setFocusable(false);
        withdraw.addActionListener(this);
        withdraw.setBounds(140, 160, 100, 30);


        transfer = new JButton("Transfer");
        transfer.setVisible(true);
        transfer.setFocusable(false);
        transfer.addActionListener(this);
        transfer.setBounds(260, 160, 100, 30);


        imageContainer = new JLabel();
        imageContainer.setIcon(icon);
        imageContainer.setForeground(Color.gray.darker());
        imageContainer.setVisible(true);
        imageContainer.setBounds(100, 40, 100, 100);


        name = new JLabel("Name: " + DatabaseConnection.fname + " " + DatabaseConnection.lname);
        name.setForeground(Color.black);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        name.setVisible(true);
        name.setBounds(15, 175, 250, 25);


        email = new JLabel("Email: " + DatabaseConnection.email);
        email.setForeground(Color.black);
        email.setFont(new Font("Arial", Font.BOLD, 15));
        email.setVisible(true);
        email.setBounds(15, 210, 250, 25);

        age = new JLabel("Age: " + DatabaseConnection.age);
        age.setForeground(Color.black);
        age.setFont(new Font("Arial", Font.BOLD, 15));
        age.setVisible(true);
        age.setBounds(15, 245, 250, 25);

        sex = new JLabel("Email: " + DatabaseConnection.sex);
        sex.setForeground(Color.black);
        sex.setFont(new Font("Arial", Font.BOLD, 15));
        sex.setVisible(true);
        sex.setBounds(15, 280, 250, 25);


        logout = new JButton("Logout");
        logout.setVisible(true);
        logout.setFocusable(false);
        logout.addActionListener(this);
        logout.setBounds(20, 500, 100, 35);


        settings = new JButton("Settings");
        settings.setVisible(true);
        settings.setFocusable(false);
        settings.addActionListener(this);
        settings.setBounds(180, 500, 100, 35);


        profilePanel = new JPanel();
        profilePanel.setVisible(true);
        profilePanel.setBounds(10, 10, 300, 545);
        profilePanel.setLayout(null);
        profilePanel.setBackground(new Color(28, 35, 112));
        profilePanel.add(imageContainer);
        profilePanel.add(name);
        profilePanel.add(email);
        profilePanel.add(age);
        profilePanel.add(sex);
        profilePanel.add(logout);
        profilePanel.add(settings);


        balancePanel = new JPanel();
        balancePanel.setVisible(true);
        balancePanel.setBounds(320, 10, 555, 200);
        balancePanel.setBackground(new Color(28, 35, 112));
        balancePanel.setLayout(null);
        balancePanel.add(balanceLabel);
        balancePanel.add(withdraw);
        balancePanel.add(deposit);
        balancePanel.add(transfer);

        historyPanel = new JPanel();
        historyPanel.setVisible(true);
        historyPanel.setBounds(320, 220, 555, 335);
        historyPanel.setLayout(null);
        historyPanel.setBackground(new Color(28, 35, 112));
        historyPanel.add(scrollPane);
        historyPanel.add(transactLabel);
        historyPanel.add(retrieve);



        this.setSize(900, 600);
        this.setTitle("Dashboard");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(historyPanel);
        this.add(balancePanel);
        this.add(profilePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.lightGray);
    }
    public static void disableButn(){

        deposit.setEnabled(false);
        withdraw.setEnabled(false);
        transfer.setEnabled(false);
        logout.setEnabled(false);
        retrieve.setEnabled(false);
        settings.setEnabled(false);
    }
    public static void enableButn(){

        deposit.setEnabled(true);
        withdraw.setEnabled(true);
        transfer.setEnabled(true);
        logout.setEnabled(true);
        retrieve.setEnabled(true);
        settings.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logout) {
            DatabaseConnection.userExist = false;
            DatabaseConnection.u_id = 0;
            this.dispose();

            SwingCarousel.textField.setText("Username");
            SwingCarousel.passField.setText("Password");
            SwingCarousel swingCarousel = new SwingCarousel();
            swingCarousel.setSize(800, 400);


        } else if (e.getSource() == settings) {
            disableButn();
            new Settings();

        } else if (e.getSource() == deposit) {
            disableButn();
            new Operation();
            Operation.opr.setText("Deposit");
            disableButn();

        } else if (e.getSource() == withdraw) {
            disableButn();
            new Operation();
            Operation.opr.setText("Withdraw");

        } else if (e.getSource() == transfer) {
            disableButn();
            new Operation();
            Operation.receiver.setVisible(true);
            Operation.opr.setText("Transfer");

        } else if (e.getSource() == retrieve) {
                DatabaseConnection.showHistory();

        }
    }
}


