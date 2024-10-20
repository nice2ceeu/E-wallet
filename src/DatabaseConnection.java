import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:mysql://localhost:3306/dbase";
            String user = "root";
            String pass = "Lazarte2002";

            connection = DriverManager.getConnection(url, user, pass);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static String user;
    public static String pass;


    public static String fname;
    public static String lname;
    public static int age;
    public static String sex;
    public static int u_id;
    public static int newId = -1;
    public static String password;

    public static String email;
    public static boolean userExist;
    public static int balance;
    public static int newBalance;

    public static void login() {


        String url = "select * from accounts where user_name=? and pass_word=?";
        String url1 = "select balance from bank where user_name=?";

        user = SwingCarousel.textField.getText();
        pass = String.valueOf(SwingCarousel.passField.getPassword());

        try {
            PreparedStatement pst = SwingCarousel.connection.prepareStatement(url);
            PreparedStatement pst1 = SwingCarousel.connection.prepareStatement(url1);

            pst.setString(1, user);
            pst.setString(2, pass);


            pst1.setString(1, user);


            pst1.executeQuery();
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                fname = rs.getString("fname");
                lname = rs.getString("lname");
                u_id += rs.getInt("user_id");
                email = rs.getString("email");
                age = rs.getInt("age");
                sex = rs.getString("sex");
                password = rs.getString("pass_word");

                userExist = true;


            } else {
                userExist = false;
                JOptionPane.showMessageDialog(null, "Incorrect Credentials",
                        "Failed to log in", JOptionPane.ERROR_MESSAGE);
            }

            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                balance = rs1.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateTransact() {

        String fullName = lname+ ","+" "+fname;

        String sql = "update bank set balance  = ? where user_id=?";


        String sql1 = "insert into transact_tbl(operation,user_id,date_time,amount,full_name) values(?,?,now(),?,?)";


        try {
            PreparedStatement pst = SwingCarousel.connection.prepareStatement(sql);
            PreparedStatement pst1 = SwingCarousel.connection.prepareStatement(sql1);


            pst.setInt(1, balance);
            pst.setInt(2, u_id);


            pst1.setString(1, Operation.transactType);
            pst1.setInt(2, u_id);
            pst1.setInt(3, Operation.inputAmount);
            pst1.setString(4,fullName);


            pst.executeUpdate();
            pst1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }


    public static boolean userExist1 = false;
    public static String newFullName;

    public static void checkUsers() {

        String sql1 = "select balance,user_id from bank where user_name=?";

        String sql2 = "select fname,lname from accounts where user_name=?";
        try {
            Operation.inputRecipient = Operation.receiver.getText();
            PreparedStatement pst1 = SwingCarousel.connection.prepareStatement(sql1);
            pst1.setString(1, Operation.inputRecipient);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {

                newBalance = rs.getInt("balance");
                newId = rs.getInt("user_id");
                userExist1 = true;
            } else {
                JOptionPane.showMessageDialog(null, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
                Operation.inputRecipient = "";
            }

            PreparedStatement pst2 = SwingCarousel.connection.prepareStatement(sql2);
            pst2.setString(1, Operation.inputRecipient);

            ResultSet rs1 = pst2.executeQuery();

            if (rs1.next()){
                newFullName = rs1.getString("lname") +", "+rs1.getString("fname");

            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in Database", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }


    public static void checkSelf() {
        if (newId != u_id) {


            try {
                Operation.inputAmount = Integer.parseInt(Operation.input.getText());


                if (Operation.inputAmount > DatabaseConnection.balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance", "Not Enough", JOptionPane.WARNING_MESSAGE);
                    Operation.inputAmount = 0;
                } else if (Operation.inputAmount < 100) {
                    JOptionPane.showMessageDialog(null, "Must exceed 100 Php", "Minimum", JOptionPane.WARNING_MESSAGE);
                    Operation.inputAmount = 0;
                } else {
                    DatabaseConnection.balance -= Operation.inputAmount;
                    DatabaseConnection.updateTransfer();
                    Operation.input.setText("Enter Amount");
                    Operation.receiver.setText("Enter Recipient");
                    Dashboard.balanceLabel.setText("Balance: " + DatabaseConnection.balance + " Php");


                    JOptionPane.showMessageDialog(null, "Transfer Success", "Transfer", JOptionPane.INFORMATION_MESSAGE);

                    Operation.inputRecipient = "";
                    Operation.inputAmount = 0;
                    DatabaseConnection.userExist1 = false;
                }

            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                newId = 0;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Can't transfer to self", "Error", JOptionPane.ERROR_MESSAGE);
            newId = 0;

        }
    }


    public static void updateTransfer() {
        String fullName = lname+ ","+" "+fname;

        String sql = "update bank set balance  = ? where user_id=?";

        String sql2 = "update bank set balance  = ? where user_name=?";

        String sql3 = "insert into transact_tbl(operation,user_id,date_time,amount,full_name) values(?,?,now(),?,?)";

        try {

            PreparedStatement pst = SwingCarousel.connection.prepareStatement(sql);
            PreparedStatement pst2 = SwingCarousel.connection.prepareStatement(sql2);
            PreparedStatement pst3 = SwingCarousel.connection.prepareStatement(sql3);


            pst.setInt(1, balance);
            pst.setInt(2, u_id);
            pst.executeUpdate();


            pst3.setString(1, "Transfer");
            pst3.setInt(2, u_id);
            pst3.setInt(3, Operation.inputAmount);
            pst3.setString(4, "to: "+newFullName);
            pst3.executeUpdate();


            newBalance += Operation.inputAmount;
            pst2.setInt(1, newBalance);
            pst2.setString(2, Operation.inputRecipient);
            pst2.executeUpdate();


            pst3.setString(1, "Receive");
            pst3.setInt(2, newId);
            pst3.setString(4, "from: "+fullName);
            pst3.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in Database", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public static void showHistory() {
        Connection connection = getConnection();

        String sql = "select * from transact_tbl where user_id = ? order by date_time desc";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, u_id);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) Dashboard.table.getModel();
            model.setRowCount(0);

            boolean hasData = true;

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(4) + " Php", rs.getString(3)});

            }
            if (!hasData) {
                JOptionPane.showMessageDialog(null, "No transactions found for your account.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching transaction history.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updatePass() {

        Connection connection = getConnection();

        String sql = "update accounts set pass_word = ? where user_id = ?";


        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, Settings.conP);
            pst.setInt(2, u_id);

            pst.executeUpdate();


            JOptionPane.showMessageDialog(null, "Changed Successfully", "Change Pass", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching transaction history.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void registrationRequest() {


        String sql = "insert into request(fname,lname,email,uname,pass,age,sex,dateRequest) values(?,?,?,?,?,?,?,now())";

        String sql1 = "select user_name from accounts where user_name=?";

        try {
            PreparedStatement pst = SwingCarousel.connection.prepareStatement(sql);

            PreparedStatement pst1 = SwingCarousel.connection.prepareStatement(sql1);

            pst1.setString(1, RegisterReq.uName);

            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                rs.getString("user_name");
                JOptionPane.showMessageDialog(null, "Username is take", "Username", JOptionPane.WARNING_MESSAGE);
            } else {

                pst.setString(1, RegisterReq.fName);
                pst.setString(2, RegisterReq.lName);
                pst.setString(3, RegisterReq.eMail);
                pst.setString(4, RegisterReq.uName);
                pst.setString(5, RegisterReq.pWord);
                pst.setInt(6, RegisterReq.age);
                pst.setString(7, RegisterReq.sex);
                pst.executeUpdate();


                JOptionPane.showMessageDialog(null, "Creation Request", "Submitted", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Server Error.", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public static void approved() {

        String sql = "insert into accounts(fname, lname, email,user_name,pass_word,age,sex,date_created) SELECT fname, lname, email,uname,pass,age,sex,dateRequest from request WHERE request_id = ?";
        String sql1 = "delete from request where request_id = ?";
        String sql2 = "insert into bank(balance, user_name)values (0,?)";

        try{
            PreparedStatement pst = SwingCarousel.connection.prepareStatement(sql);
            PreparedStatement pst1 = SwingCarousel.connection.prepareStatement(sql1);
            PreparedStatement pst2 = SwingCarousel.connection.prepareStatement(sql2);
            pst.setInt(1, Integer.parseInt(AdminDashboard.id));
            pst1.setInt(1,Integer.parseInt(AdminDashboard.id));
            pst2.setString(1,AdminDashboard.user_name);

            pst.executeUpdate();
            pst1.executeUpdate();
            pst2.executeUpdate();


            showRegReqs();
            showAccounts();
            JOptionPane.showMessageDialog(null, "Account Approved", "Submitted", JOptionPane.INFORMATION_MESSAGE);
            AdminDashboard.id = "";

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Server Error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static DefaultTableModel model1;
    public static void showRegReqs() {
        Connection connection = getConnection();
        String sql = "select * from request order by dateRequest desc";


        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            model1 = (DefaultTableModel) AdminDashboard.regReqTable.getModel();
            model1.setRowCount(0);

            boolean hasData = true;
            // Requests
            while (rs.next()) {
                model1.addRow(new Object[]{rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(7)
                        ,rs.getString(6), rs.getString(8), rs.getString(9)});

            }
            if (!hasData) {
                JOptionPane.showMessageDialog(null, "No Request found .", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching transaction history.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }public static void showAccounts() {
        Connection connection = getConnection();

        String sql1 = "select * from accounts order by date_created desc";

        try {
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            ResultSet rs1 = pst1.executeQuery();
            DefaultTableModel model2 = (DefaultTableModel) AdminDashboard.accountsTable.getModel();
            model2.setRowCount(0);

            boolean hasData = true;


            // Accounts
            while (rs1.next()) {
                model2.addRow(new Object[]{rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4),
                        rs1.getString(5), rs1.getString(7), rs1.getString(6), rs1.getString(8), rs1.getString(9)});
            }
            if (!hasData) {
                JOptionPane.showMessageDialog(null, "No Request found .", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching transaction history.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void showTransact() {
        Connection connection = getConnection();

        String sql2 = "select * from transact_tbl order by date_time desc";

        try {
            PreparedStatement pst2 = connection.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();

            DefaultTableModel model3 = (DefaultTableModel) AdminDashboard.transactionTable.getModel();
            model3.setRowCount(0);


            boolean hasData = true;

            // Transactions
            while (rs2.next()) {
                model3.addRow(new Object[]{rs2.getString(1), rs2.getString(2), rs2.getString( 5), rs2.getString(4), rs2.getString(3)});
            }
            if (!hasData) {
                JOptionPane.showMessageDialog(null, "No Request found .", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching transaction history.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void deleteFromDatabase(){

        String sql = "delete from request where request_id = ?";

        try{
            PreparedStatement pst = SwingCarousel.connection.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(AdminDashboard.id));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Request Rejected.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            DatabaseConnection.showRegReqs();
            AdminDashboard.id = "";


        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Server Error.", "Error", JOptionPane.ERROR_MESSAGE);


        }
    }public static void search(){

        String sql = "select * from transact_tbl where user_id = ?";

        try{
            PreparedStatement pst = SwingCarousel.connection.prepareStatement(sql);
            pst.setInt(1,AdminDashboard.searchId);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model3 = (DefaultTableModel) AdminDashboard.transactionTable.getModel();
            model3.setRowCount(0);

            // Transactions
            while (rs.next()) {
                model3.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString( 5), rs.getString(4), rs.getString(3)});
            }

            if (model3.getRowCount()==0) {
                JOptionPane.showMessageDialog(null, "User not found/No transaction .", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Server Error.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}


