//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.*;
//
//
//public class GUI {
//    private JFrame frame;
//    private JPanel mainPanel;
//    private JButton administratorButton;
//    private JButton customerButton;
//    private JButton exitButton;
//    private Rental_System rentalSystem;
//    public GUI() {
//        rentalSystem = new Rental_System();
//
//        administratorButton = new JButton("Administrator");
//        customerButton = new JButton("Customer");
//        exitButton = new JButton("Exit");
//
//        administratorButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                rentalSystem.administrator_Login();
//            }
//        });
//
//        customerButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                rentalSystem.customer_Login();
//            }
//        });
//
//        exitButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                GUI rentalGUI = new GUI();
//                rentalGUI.rentalSystem.connect_to_Database();
//                rentalGUI.createAndShowGUI();
//            }
//        });
//    }
//
//    private void createAndShowGUI() {
//        frame = new JFrame("Rental System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(3, 1));
//
//        mainPanel.add(administratorButton);
//        mainPanel.add(customerButton);
//        mainPanel.add(exitButton);
//
//        frame.getContentPane().add(mainPanel);
//        frame.pack();
//        frame.setVisible(true);
//    }
//}
//
//class Rental_System {
//    private Connection connection;
//    private Statement statement;
//    void connect_to_Database() {
//        String url = "jdbc:mysql://localhost:3306/test22";
//        String user = "root";
//        String password = "ihatemysql";
//
//
//        try {
//
//            connection = DriverManager.getConnection(url, user, password);
//            statement = connection.createStatement();
//
//            // Create the employees and customers tables if they don't exist
//            String createEmployeesTableQuery = "CREATE TABLE IF NOT EXISTS employees (emp_id VARCHAR(20), password VARCHAR(20))";
//            statement.executeUpdate(createEmployeesTableQuery);
//
//            String createCustomersTableQuery = "CREATE TABLE IF NOT EXISTS customers (customer_id VARCHAR(20), password VARCHAR(20))";
//            statement.executeUpdate(createCustomersTableQuery);
//
//            String createCarTableQuery = "CREATE TABLE IF NOT EXISTS cars (ID int(5) auto_increment PRIMARY KEY, Model varchar(50), Price int(50))";
//            statement.executeUpdate(createCarTableQuery);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    void Run() {
//        connect_to_Database();
//    }
//
//    void administrator_Login() {
//        JFrame loginFrame = new JFrame("Administrator Login");
//        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel loginPanel = new JPanel();
//        loginPanel.setLayout(new GridLayout(4, 2));
//
//        JLabel adminIdLabel = new JLabel("Administrator ID:");
//        JTextField adminIdField = new JTextField();
//
//        JLabel passwordLabel = new JLabel("Password:");
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton loginButton = new JButton("Login");
//        loginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String adminId = adminIdField.getText();
//                char[] passwordChars = passwordField.getPassword();
//                String password = new String(passwordChars);
//
//                boolean loggedIn = verifyAdministratorLogin(adminId, password);
//
//                if (loggedIn) {
//                    // Proceed with administrator functionality
//                    JOptionPane.showMessageDialog(loginFrame, "Administrator logged in successfully");
//                    loginFrame.dispose();
//
//                    // Show options to add a car, delete a car, and update a car's price
//                    JFrame optionsFrame = new JFrame("Administrator Options");
//                    optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//                    JPanel optionsPanel = new JPanel();
//                    optionsPanel.setLayout(new GridLayout(3, 1));
//
//                    JButton addCarButton = new JButton("Add a Car");
//                    addCarButton.addActionListener(new ActionListener() {
//                        public void actionPerformed(ActionEvent e) {
//                            // Call the method to add a car here
//                            addCar();
//                        }
//                    });
//
//                    JButton deleteCarButton = new JButton("Delete a Car");
//                    deleteCarButton.addActionListener(new ActionListener() {
//                        public void actionPerformed(ActionEvent e) {
//                            // Call the method to delete a car here
//                            deleteCar();
//                        }
//                    });
//
//                    JButton updatePriceButton = new JButton("Update Car's Price");
//                    updatePriceButton.addActionListener(new ActionListener() {
//                        public void actionPerformed(ActionEvent e) {
//                            // Call the method to update a car's price here
//                            updateCarPrice();
//                        }
//                    });
//
//                    optionsPanel.add(addCarButton);
//                    optionsPanel.add(deleteCarButton);
//                    optionsPanel.add(updatePriceButton);
//
//                    optionsFrame.getContentPane().add(optionsPanel);
//                    optionsFrame.pack();
//                    optionsFrame.setVisible(true);
//                } else {
//                    JOptionPane.showMessageDialog(loginFrame, "Invalid administrator ID or password");
//                }
//            }
//        });
//
//        JButton signUpButton = new JButton("Sign Up");
//        signUpButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                administrator_SignUp();
//            }
//        });
//
//        loginPanel.add(adminIdLabel);
//        loginPanel.add(adminIdField);
//        loginPanel.add(passwordLabel);
//        loginPanel.add(passwordField);
//        loginPanel.add(signUpButton);
//        loginPanel.add(loginButton);
//
//        loginFrame.getContentPane().add(loginPanel);
//        loginFrame.pack();
//        loginFrame.setVisible(true);
//    }
//
//
//    boolean verifyAdministratorLogin(String adminId, String password) {
//        connect_to_Database(); // Make sure the connection is established
//
//        // Perform administrator login verification logic here using the adminId and password
//        // For example, you can query the database to check if the adminId and password match
//
//        // Assuming you have a valid connection and statement objects
//        String query = "SELECT COUNT(*) FROM employees WHERE emp_id = ? AND password = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, adminId);
//            preparedStatement.setString(2, password);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            int count = resultSet.getInt(1);
//            return count > 0; // Return true if the adminId and password match in the database
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false; // Return false by default (if there's an error or adminId and password don't match)
//    }
//
//    void administrator_SignUp() {
//        connect_to_Database(); // Make sure the connection is established
//
//        JFrame signUpFrame = new JFrame("Administrator Sign Up");
//        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel signUpPanel = new JPanel();
//        signUpPanel.setLayout(new GridLayout(3, 2));
//
//        JLabel adminIdLabel = new JLabel("Administrator ID:");
//        JTextField adminIdField = new JTextField();
//
//        JLabel passwordLabel = new JLabel("Password:");
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton signUpButton = new JButton("Sign Up");
//        signUpButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String adminId = adminIdField.getText();
//                char[] passwordChars = passwordField.getPassword();
//                String password = new String(passwordChars);
//
//                boolean signedUp = registerAdministrator(adminId, password);
//
//                if (signedUp) {
//                    // Proceed with administrator functionality
//                    JOptionPane.showMessageDialog(signUpFrame, "Administrator signed up successfully");
//                    signUpFrame.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(signUpFrame, "Failed to sign up administrator");
//                }
//            }
//        });
//
//        signUpPanel.add(adminIdLabel);
//        signUpPanel.add(adminIdField);
//        signUpPanel.add(passwordLabel);
//        signUpPanel.add(passwordField);
//        signUpPanel.add(new JLabel());
//        signUpPanel.add(signUpButton);
//
//        signUpFrame.getContentPane().add(signUpPanel);
//        signUpFrame.pack();
//        signUpFrame.setVisible(true);
//    }
//
//    boolean registerAdministrator(String adminId, String password) {
//        connect_to_Database(); // Make sure the connection is established
//
//        String query = "INSERT INTO employees (emp_id, password) VALUES (?, ?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, adminId);
//            preparedStatement.setString(2, password);
//            int rowsAffected = preparedStatement.executeUpdate();
//            return rowsAffected > 0; // Return true if the insertion was successful
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Return false by default (if there's an error or insertion failed)
//    }
//
//    void customer_Login() {
//        connect_to_Database(); // Make sure the connection is established
//
//        JFrame loginFrame = new JFrame("Customer Login");
//        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel loginPanel = new JPanel();
//        loginPanel.setLayout(new GridLayout(3, 2));
//
//        JLabel customerIdLabel = new JLabel("Customer Name:");
//        JTextField customerIdField = new JTextField();
//
//        JLabel passwordLabel = new JLabel("Password:");
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton loginButton = new JButton("Login");
//        loginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String customerId = customerIdField.getText();
//                char[] passwordChars = passwordField.getPassword();
//                String password = new String(passwordChars);
//
//                boolean loggedIn = verifyCustomerLogin(customerId, password);
//
//                if (loggedIn) {
//                    // Proceed with customer functionality
//                    JOptionPane.showMessageDialog(loginFrame, "Customer logged in successfully");
//                    loginFrame.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(loginFrame, "Invalid customer ID or password");
//                }
//            }
//        });
//
//        JButton signUpButton = new JButton("Sign Up");
//        signUpButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                customer_SignUp();
//            }
//        });
//
//        loginPanel.add(customerIdLabel);
//        loginPanel.add(customerIdField);
//        loginPanel.add(passwordLabel);
//        loginPanel.add(passwordField);
//        loginPanel.add(signUpButton);
//        loginPanel.add(loginButton);
//
//        loginFrame.getContentPane().add(loginPanel);
//        loginFrame.pack();
//        loginFrame.setVisible(true);
//    }
//
//    boolean verifyCustomerLogin(String customerId, String password) {
//        connect_to_Database(); // Make sure the connection is established
//
//        // Perform customer login verification logic here using the customerId and password
//        // For example, you can query the database to check if the customerId and password match
//
//        // Assuming you have a valid connection and statement objects
//        String query = "SELECT COUNT(*) FROM customers WHERE customer_id = ? AND password = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, customerId);
//            preparedStatement.setString(2, password);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            int count = resultSet.getInt(1);
//            return count > 0; // Return true if the customerId and password match in the database
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false; // Return false by default (if there's an error or customerId and password don't match)
//    }
//
//    void customer_SignUp() {
//        connect_to_Database(); // Make sure the connection is established
//
//        JFrame signUpFrame = new JFrame("Customer Sign Up");
//        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel signUpPanel = new JPanel();
//        signUpPanel.setLayout(new GridLayout(3, 2));
//
//        JLabel customerIdLabel = new JLabel("Customer Name:");
//        JTextField customerIdField = new JTextField();
//
//        JLabel passwordLabel = new JLabel("Password:");
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton signUpButton = new JButton("Sign Up");
//        signUpButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String customerId = customerIdField.getText();
//                char[] passwordChars = passwordField.getPassword();
//                String password = new String(passwordChars);
//
//                boolean signedUp = registerCustomer(customerId, password);
//
//                if (signedUp) {
//                    // Proceed with customer functionality
//                    JOptionPane.showMessageDialog(signUpFrame, "Customer signed up successfully");
//                    signUpFrame.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(signUpFrame, "Failed to sign up customer");
//                }
//            }
//        });
//
//        signUpPanel.add(customerIdLabel);
//        signUpPanel.add(customerIdField);
//        signUpPanel.add(passwordLabel);
//        signUpPanel.add(passwordField);
//        signUpPanel.add(new JLabel());
//        signUpPanel.add(signUpButton);
//
//        signUpFrame.getContentPane().add(signUpPanel);
//        signUpFrame.pack();
//        signUpFrame.setVisible(true);
//    }
//
//    boolean registerCustomer(String customerId, String password) {
//        connect_to_Database(); // Make sure the connection is established
//
//        String query = "INSERT INTO customers (customer_id, password) VALUES (?, ?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, customerId);
//            preparedStatement.setString(2, password);
//            int rowsAffected = preparedStatement.executeUpdate();
//            return rowsAffected > 0; // Return true if the insertion was successful
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Return false by default (if there's an error or insertion failed)
//    }
//    void addCar() {
//        JFrame addCarFrame = new JFrame("Add a Car");
//        addCarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel addCarPanel = new JPanel();
//        addCarPanel.setLayout(new GridLayout(4, 2));
//
//        JLabel carNameLabel = new JLabel("Car Name:");
//        JTextField carNameField = new JTextField();
//
//        JLabel carPriceLabel = new JLabel("Car Price:");
//        JTextField carPriceField = new JTextField();
//
//        JButton addButton = new JButton("Add");
//        addButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                String carName = carNameField.getText();
//                double carPrice = Double.parseDouble(carPriceField.getText());
//
//                boolean added = addCarToDatabase(carName, carPrice);
//
//                if (added) {
//                    JOptionPane.showMessageDialog(addCarFrame, "Car added successfully");
//                    addCarFrame.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(addCarFrame, "Failed to add car");
//                }
//            }
//        });
//
//        addCarPanel.add(carNameLabel);
//        addCarPanel.add(carNameField);
//        addCarPanel.add(carPriceLabel);
//        addCarPanel.add(carPriceField);
//        addCarPanel.add(new JLabel());
//        addCarPanel.add(addButton);
//
//        addCarFrame.getContentPane().add(addCarPanel);
//        addCarFrame.pack();
//        addCarFrame.setVisible(true);
//    }
//
//    boolean addCarToDatabase(String carName, double carPrice) {
//        connect_to_Database(); // Make sure the connection is established
//
//        String query = "INSERT INTO cars (Model, Price) VALUES (?, ?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, carName);
//            preparedStatement.setDouble(2, carPrice);
//            int rowsAffected = preparedStatement.executeUpdate();
//            return rowsAffected > 0; // Return true if the insertion was successful
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Return false by default (if there's an error or insertion failed)
//    }
//
//    void deleteCar() {
//        JFrame deleteCarFrame = new JFrame("Delete a Car");
//        deleteCarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel deleteCarPanel = new JPanel();
//        deleteCarPanel.setLayout(new GridLayout(2, 2));
//
//        JLabel carIdLabel = new JLabel("Car ID:");
//        JTextField carIdField = new JTextField();
//
//        JButton deleteButton = new JButton("Delete");
//        deleteButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String carId = carIdField.getText();
//
//                boolean deleted = deleteCarFromDatabase(carId);
//
//                if (deleted) {
//                    JOptionPane.showMessageDialog(deleteCarFrame, "Car deleted successfully");
//                    deleteCarFrame.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(deleteCarFrame, "Failed to delete car");
//                }
//            }
//        });
//
//        deleteCarPanel.add(carIdLabel);
//        deleteCarPanel.add(carIdField);
//        deleteCarPanel.add(new JLabel());
//        deleteCarPanel.add(deleteButton);
//
//        deleteCarFrame.getContentPane().add(deleteCarPanel);
//        deleteCarFrame.pack();
//        deleteCarFrame.setVisible(true);
//    }
//
//    boolean deleteCarFromDatabase(String carId) {
//        connect_to_Database(); // Make sure the connection is established
//
//        String query = "DELETE FROM cars WHERE car_id = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, carId);
//            int rowsAffected = preparedStatement.executeUpdate();
//            return rowsAffected > 0; // Return true if the deletion was successful
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Return false by default (if there's an error or deletion failed)
//    }
//
//    void updateCarPrice() {
//        JFrame updatePriceFrame = new JFrame("Update Car's Price");
//        updatePriceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel updatePricePanel = new JPanel();
//        updatePricePanel.setLayout(new GridLayout(3, 2));
//
//        JLabel carIdLabel = new JLabel("Car ID:");
//        JTextField carIdField = new JTextField();
//
//        JLabel newPriceLabel = new JLabel("New Price:");
//        JTextField newPriceField = new JTextField();
//
//        JButton updateButton = new JButton("Update");
//        updateButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String carId = carIdField.getText();
//                double newPrice = Double.parseDouble(newPriceField.getText());
//
//                boolean updated = updateCarPriceInDatabase(carId, newPrice);
//
//                if (updated) {
//                    JOptionPane.showMessageDialog(updatePriceFrame, "Car's price updated successfully");
//                    updatePriceFrame.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(updatePriceFrame, "Failed to update car's price");
//                }
//            }
//        });
//
//        updatePricePanel.add(carIdLabel);
//        updatePricePanel.add(carIdField);
//        updatePricePanel.add(newPriceLabel);
//        updatePricePanel.add(newPriceField);
//        updatePricePanel.add(new JLabel());
//        updatePricePanel.add(updateButton);
//
//        updatePriceFrame.getContentPane().add(updatePricePanel);
//        updatePriceFrame.pack();
//        updatePriceFrame.setVisible(true);
//    }
//
//    boolean updateCarPriceInDatabase(String carId, double newPrice) {
//        connect_to_Database(); // Make sure the connection is established
//
//        String query = "UPDATE cars SET car_price = ? WHERE car_id = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setDouble(1, newPrice);
//            preparedStatement.setString(2, carId);
//            int rowsAffected = preparedStatement.executeUpdate();
//            return rowsAffected > 0; // Return true if the update was successful
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Return false by default (if there's an error or update failed)
//    }
//
//
//}