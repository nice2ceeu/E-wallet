import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame implements ActionListener {
    JPanel nav,regReq,accounts,transactions;

    JLabel req, acc, trans;
    JButton exit, approve , reject,search, showAll;
    JTextField searchBar;


    public static JTable regReqTable, accountsTable, transactionTable;
    JScrollPane sp,sp1,sp2;
    public static String[] columns ={"ID","First Name","Surname","Email","Username","Age","Password","Sex","Date of request"};
    public static String[] columns1 ={"User ID","First Name","Surname","Email","Username","Age","Password","Sex","Date Created"};
    public static String[] columns2 ={"Transact ID","Operation","Full Name","Amount","Date/Time"};
    AdminDashboard(){
        this.setSize(1200,600);
        this.getContentPane().setBackground(Color.lightGray);
        this.setVisible(true);
        this.setTitle("ADMIN");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        searchBar = new JTextField("Input User ID");
        searchBar.setVisible(true);
        searchBar.setBounds(885, 10, 150, 30);
        searchBar.setFont(new Font("Arial", Font.PLAIN, 12));
        searchBar.setCaretColor(Color.GRAY);
        searchBar.setCaretPosition(searchBar.getText().length());
        searchBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchBar.setText("");
            }
        });

        exit = new JButton("Exit");
        exit.setVisible(true);
        exit.setFocusable(false);
        exit.setBorder(new MetalBorders.ButtonBorder());
        exit.setBounds(10,10,100,30);
        exit.addActionListener(this);

        approve = new JButton("Approve");
        approve.setVisible(true);
        approve.setFocusable(false);
        approve.setBorder(new MetalBorders.ButtonBorder());
        approve.setBounds(410,10,100,30);
        approve.addActionListener(this);

        reject = new JButton("Reject");
        reject.setVisible(true);
        reject.setFocusable(false);
        reject.setBorder(new MetalBorders.ButtonBorder());
        reject.setBounds(530,10,100,30);
        reject.addActionListener(this);

        search = new JButton("Search");
        search.setVisible(true);
        search.setFocusable(false);
        search.setBorder(new MetalBorders.ButtonBorder());
        search.setBounds(1040,10,100,30);
        search.addActionListener(this);

        showAll = new JButton("Show All");
        showAll.setVisible(true);
        showAll.setFocusable(false);
        showAll.setBorder(new MetalBorders.ButtonBorder());
        showAll.setBounds(660,10,100,30);
        showAll.addActionListener(this);


        req = new JLabel("Register Requests");
        req.setVisible(true);
        req.setFont(new Font("Arial", Font.ITALIC,14));
        req.setForeground(Color.red);
        req.setBounds(240,60,140,20);

        acc = new JLabel("Registered Accounts");
        acc.setVisible(true);
        acc.setFont(new Font("Arial", Font.ITALIC,14));
        acc.setForeground(Color.red);
        acc.setBounds(240,310,140,20);

        trans = new JLabel("Transaction History");
        trans.setVisible(true);
        trans.setFont(new Font("Arial", Font.ITALIC,14));
        trans.setForeground(Color.red);
        trans.setBounds(860,60,140,20);

        nav = new JPanel();
        nav.setBackground(new Color(28, 35, 112));
        nav.setLayout(null);
        nav.setBounds(20,10,1150,50);
        nav.add(exit);
        nav.add(approve);
        nav.add(reject);
        nav.add(searchBar);
        nav.add(search);
        nav.add(showAll);


        regReq = new JPanel();
        regReq.setBackground(new Color(28, 35, 112));
        regReq.setLayout(null);
        regReq.setBounds(20,80,640,230);

        accounts = new JPanel();
        accounts.setBackground(new Color(28, 35, 112));
        accounts.setLayout(null);
        accounts.setBounds(20,330,640,220);

        transactions = new JPanel();
        transactions.setBackground(new Color(28, 35, 112));
        transactions.setLayout(null);
        transactions.setBounds(670,80,500,470);

        //requestCreation fetching
        DefaultTableModel model1 = new DefaultTableModel(columns,0);

        regReqTable = new JTable(model1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        regReqTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sp = new JScrollPane(regReqTable);
        sp.setBounds(10, 10, 620, 210);

        regReqTable.setModel(model1);
        regReq.add(sp);
        //accounts fetching


        DefaultTableModel model2 = new DefaultTableModel(columns1,0);


        accountsTable = new JTable(model2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        sp1 = new JScrollPane(accountsTable);
        sp1.setBounds(10, 10, 620, 200);

        accountsTable.setModel(model2);
        accounts.add(sp1);

        //fetching * transactions

        DefaultTableModel model3 = new DefaultTableModel(columns2,0);

        transactionTable = new JTable(model3) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        transactionTable.setFocusable(false);
//        transactionTable.setRowSelectionAllowed(false);
//
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sp2 = new JScrollPane(transactionTable);
        sp2.setBounds(10, 10, 480, 450);

        transactionTable.setModel(model3);
        transactions.add(sp2);





        this.add(nav);
        this.add(regReq);
        this.add(accounts);
        this.add(transactions);
        this.add(req);
        this.add(acc);
        this.add(trans);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public static String id;
    public static String user_name;
    public static int searchId;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==exit){
            SwingCarousel.frontUIEnable();
            this.dispose();
            SwingCarousel.textField.setText("Username");
            SwingCarousel.passField.setText("Password");
            SwingCarousel swingCarousel = new SwingCarousel();

            swingCarousel.setSize(800, 400);
        }else if (e.getSource()==approve){
            int selectedRow = regReqTable.getSelectedRow();

            if (selectedRow != -1){
                id = (String) DatabaseConnection.model1.getValueAt(selectedRow,0);
                user_name = String.valueOf(DatabaseConnection.model1.getValueAt(selectedRow,4));
                DatabaseConnection.approved();

            } else {
                JOptionPane.showMessageDialog(null, "Please select row .", "No row", JOptionPane.INFORMATION_MESSAGE);
            }
        }else if (e.getSource()==reject){
            int selectedRow = regReqTable.getSelectedRow();

            if (selectedRow != -1){

                id = (String) DatabaseConnection.model1.getValueAt(selectedRow, 0);
                DatabaseConnection.deleteFromDatabase();
            }else {
                JOptionPane.showMessageDialog(null, "Please select row .", "No row", JOptionPane.INFORMATION_MESSAGE);
            }

        }if(e.getSource()==search){
            if (searchBar.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please User ID .", "Missing", JOptionPane.INFORMATION_MESSAGE);
            }else {

                try{
                    searchId = Integer.parseInt(searchBar.getText());
                    DatabaseConnection.search();
                    searchBar.setText("Enter User ID");

                }catch (NumberFormatException n ){
                    JOptionPane.showMessageDialog(null, "Enter a valid ID", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        }if (e.getSource()==showAll){
            DatabaseConnection.showTransact();
            searchBar.setText("Enter User ID");

        }
    }
}
