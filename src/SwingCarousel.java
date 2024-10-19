import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;


public class SwingCarousel extends JFrame implements ActionListener {


    final private String[] imagePaths = {
            "C:/Users/kristoffer/Desktop/image1.png",
            "C:/Users/kristoffer/Desktop/image3.jpg",
            "C:/Users/kristoffer/Desktop/image4.jpg",
            "C:/Users/kristoffer/Desktop/image2.png"
    };


    private int currentIndex = 0;

    final private JLabel imageLabel;
    final public JButton prevButton;
    final public JButton nextButton;
//    public static JLabel result;


    final private Timer autoSlideTimer;


    public static JTextField textField = new JTextField("Username");

    public static JPasswordField passField = new JPasswordField("Password");

    JLabel bankName;

    public static JButton login,create;

        int attempt = 5;
        final static Connection connection = DatabaseConnection.getConnection();
    public SwingCarousel() {

        textField.setVisible(true);
        textField.setBounds(125, 130, 150, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setCaretColor(Color.GRAY);
        textField.setCaretPosition(textField.getText().length());
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
            }
        });



        bankName= new JLabel("MayMal");
        bankName.setVisible(true);
        bankName.setFont(new Font("Arial", Font.BOLD, 20));
        bankName.setBounds(155, 50, 150, 40);
        bankName.setForeground(Color.YELLOW);


        passField.setVisible(true);
        passField.setBounds(125, 170, 150, 30);
        passField.setFont(new Font("Arial", Font.PLAIN, 12));
        passField.setCaretColor(Color.GRAY);
        passField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passField.setText("");
            }

        });


        login = new JButton("Log in");
        login.setFocusable(false);
        login.setVisible(true);
        login.setBounds(150, 250, 100, 30);
        login.addActionListener(this);


        create = new JButton("Create");
        create.setFocusable(false);
        create.setVisible(true);
        create.setBounds(150, 290, 100, 30);
        create.addActionListener(this);



        JPanel panel = new JPanel();
        panel.setSize(400, 500);
        panel.setBackground(new Color(28, 35, 112));
        panel.setLayout(null);
        panel.setVisible(true);
        panel.add(bankName);
        panel.add(textField);
        panel.add(passField);
        panel.add(login);
        panel.add(create);


        setTitle("My Bank");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(panel);



        // Create and configure the JLabel for displaying images
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        loadImage(currentIndex);

        // Create and configure buttons
        prevButton = new JButton("<");
        prevButton.setVisible(false);


        nextButton = new JButton(">");
        nextButton.setVisible(false);

        // Add action listeners for buttons
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAutoSlide();  // Stop auto-slide when user interacts
                currentIndex = (currentIndex - 1 + imagePaths.length) % imagePaths.length-1;
                loadImage(currentIndex);
                restartAutoSlide();  // Restart after manual interaction
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAutoSlide();  // Stop auto-slide when user interacts
                currentIndex = (currentIndex + 1) % imagePaths.length;
                loadImage(currentIndex);
                restartAutoSlide();  // Restart after manual interaction
            }
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        // Add components to the frame
        add(imageLabel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create a timer to automatically slide images every 3 seconds (3000ms)
        autoSlideTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % imagePaths.length;
                loadImage(currentIndex);
            }
        });

        // Start the auto-slide timer
        autoSlideTimer.start();

        // Make the frame visible
        setVisible(true);
    }

    // Helper method to load an image at a given index
    private void loadImage(int index) {
        ImageIcon icon = new ImageIcon(imagePaths[index]);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);  // Resizing the image
        icon = new ImageIcon(scaledImage);
        imageLabel.setIcon(icon);
    }

    // Method to stop the auto-slide
    private void stopAutoSlide() {
        if (autoSlideTimer != null) {
            autoSlideTimer.stop();
        }
    }

    // Method to restart the auto-slide after manual interaction
    private void restartAutoSlide() {
        if (autoSlideTimer != null) {
            autoSlideTimer.restart();
        }
    }

    public static void frontUIDisable(){
        login.setEnabled(false);
        create.setEnabled(false);
        textField.setEnabled(false);
        passField.setEnabled(false);
    }

    public static void frontUIEnable(){
        login.setEnabled(true);
        create.setEnabled(true);
        textField.setEnabled(true);
        passField.setEnabled(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {



        //Login
        if (e.getSource() == login) {
            if (textField.getText().equals("admin") && String.valueOf(passField.getPassword()).equals("admin")) {
                    dispose();
                    new AdminDashboard();
                    DatabaseConnection.showRegReqs();
                    DatabaseConnection.showAccounts();
                    DatabaseConnection.showTransact();
            } else {
                if (textField.getText().equals("") || String.valueOf(passField.getPassword()).equals("")) {
                    JOptionPane.showMessageDialog(null, "All fields must be fill", "Missing field",
                            JOptionPane.WARNING_MESSAGE);
                } else {

                    DatabaseConnection.login();

                    if (DatabaseConnection.userExist) {
                        this.dispose();
                        new Dashboard();
                    } else {

                        //failure to login
                        attempt--;
                        textField.setText(attempt + " more attempt/s");
                        passField.setText("");
                        if (attempt == 0) {
                            this.dispose();

                        }
                    }
                }
            }
        }if (e.getSource()==create){
            frontUIDisable();
            new RegisterReq();

        }
    }
}