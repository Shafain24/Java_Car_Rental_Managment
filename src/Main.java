import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;


class Validations {
    public static int validation1(Scanner sc) {
        while (true) {

            System.out.print("Enter your Choice: ");
            String input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number.");
                continue;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid input. Please enter a valid number.");
                } else {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


    public static int validation2(Scanner sc) {
        while (true) {
            System.out.print("Enter your choice: ");
            String input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Please enter a value.");
                continue;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > 4) {
                    System.out.println("Please enter a valid value");
                } else {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


    public static int validation3(Scanner sc) {
        while (true) {
            System.out.print("Enter your ID: ");
            String id = sc.nextLine();
            if (id.isEmpty()) {
                System.out.println("Please enter a value");
                continue;
            }
            try {
                int verify_emp_id = Integer.parseInt(id);
                return verify_emp_id;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid value");
            }
        }
    }
}
class Rental_System {
    Scanner sc = new Scanner(System.in);
    Connection connection;
    Statement statement;

    void connect_to_Database() {
        String url = "jdbc:mysql://localhost:3306/test3";
        String username = "root";
        String password = "ihatemysql";
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void create_Table() {
        String Create_Employee_Table = "CREATE TABLE IF NOT EXISTS Employee(Id INT PRIMARY KEY, Name VARCHAR(30), Password VARCHAR(20))";
        String Create_Customer_Table = "CREATE TABLE IF NOT EXISTS Customer(Name VARCHAR(50), Password VARCHAR(20))";
        String Create_Car_Table = "CREATE TABLE IF NOT EXISTS CARS(Car_ID INT AUTO_INCREMENT PRIMARY KEY, Brand VARCHAR(20), Model VARCHAR(25), Year INT(4), Price INT(6), Available INT(1) DEFAULT 1)";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(Create_Employee_Table);
            statement.executeUpdate(Create_Customer_Table);
            statement.executeUpdate(Create_Car_Table);
            System.out.println("Tables created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean Employee_Login() {
        int verify_emp_id = Validations.validation3(sc);
        boolean account_found = false;
        try {
            String query = "SELECT * FROM Employee WHERE Id=" + verify_emp_id;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                account_found = true;
                System.out.print("Enter your password: ");
                String verify_emp_password = sc.nextLine();
                if (resultSet.getString("Password").equals(verify_emp_password)) {
                    System.out.println("You are an employee");
                    return true; // Login successful
                } else {
                    System.out.println("Password is incorrect!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!account_found) {
            System.out.println("Account not found");
        }

        return false;
    }

    void Employee_Signup() {
        System.out.print("Enter your Name: ");
        String emp_name = sc.nextLine();
        System.out.print("Enter ID: ");
        int signup_id = sc.nextInt();
        sc.nextLine();
        boolean is_duplicate_id = false;
        try {
            String query = "SELECT * FROM Employee WHERE Id=" + signup_id;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                is_duplicate_id = true;
            }

            if (is_duplicate_id) {
                System.out.println("This Id is already in use");
            } else {
                System.out.print("Enter your password: ");
                String signup_password = sc.nextLine();
                System.out.print("Confirm your Password: ");
                String signup_reconfirm_password = sc.nextLine();
                if (signup_password.equals(signup_reconfirm_password)) {
                    String insertQuery = "INSERT INTO Employee(Id, Name, Password) VALUES(" + signup_id + ", '"
                            + emp_name + "', '" + signup_password + "')";
                    statement.executeUpdate(insertQuery);
                    System.out.println("Employee signed up successfully!");
                } else {
                    System.out.println("Passwords do not match");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean Customer_Login() {
        System.out.print("Enter your name: ");
        String verify_customer_name = sc.nextLine();
        boolean account_found = false;

        try {
            String query = "SELECT * FROM Customer WHERE Name='" + verify_customer_name + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                account_found = true;
                System.out.print("Enter your password: ");
                String verify_customer_password = sc.next();
                if (resultSet.getString("Password").equals(verify_customer_password)) {
                    return true;
                } else {
                    System.out.println("Password is incorrect!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!account_found) {
            System.out.println("Account not found");
        }

        return false;
    }

    void customer_Signup() {
        System.out.print("Enter your name: ");
        String customer_name = sc.next();
        System.out.print("Enter your password: ");
        String customer_password = sc.next();
        System.out.print("Confirm your Password: ");
        String customer_reconfirm_password = sc.next();

        if (customer_password.equals(customer_reconfirm_password)) {
            try {
                String insertQuery = "INSERT INTO Customer(Name, Password) VALUES('" + customer_name + "', '" + customer_password + "')";
                statement.executeUpdate(insertQuery);
                System.out.println("Customer signed up successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Passwords do not match");
        }
        sc.nextLine();
    }

    void add_car() {
        System.out.print("Enter name of Brand: ");
        String brand = sc.nextLine();
        System.out.print("Enter Model: ");
        String model = sc.nextLine();
        System.out.print("Enter year: ");
        int year = sc.nextInt();
        System.out.print("Enter price: ");
        int price = sc.nextInt();
        sc.nextLine();

        try (Statement statement = connection.createStatement()) {
            String insertQuery = "INSERT INTO CARS(Brand, Model, Year, Price) VALUES('" + brand + "', '" + model + "', " + year + ", " + price + ")";
            statement.executeUpdate(insertQuery);
            System.out.println("Car added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void update_car_price() {
        System.out.print("Enter the Car ID to update price: ");
        int carId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the new price: ");
        int newPrice = sc.nextInt();
        sc.nextLine();

        try (Statement statement = connection.createStatement()) {
            String q = "UPDATE CARS SET Price = " + newPrice + " WHERE Car_ID = " + carId;
            int rowsAffected = statement.executeUpdate(q);

            if (rowsAffected > 0) {
                System.out.println("Car with ID " + carId + " price updated successfully!");
            } else {
                System.out.println("No car found with ID " + carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void delete_car() {
        System.out.print("Enter the Car ID to delete: ");
        int carId = sc.nextInt();
        sc.nextLine();

        try (Statement statement = connection.createStatement()) {
            String deleteQuery = "DELETE FROM CARS WHERE Car_ID = " + carId;
            int rowsAffected = statement.executeUpdate(deleteQuery);

            if (rowsAffected > 0) {
                System.out.println("Car with ID " + carId + " deleted successfully!");
            } else {
                System.out.println("No car found with ID " + carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void rentCar() {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM CARS";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            System.out.println("Available Cars:");
            System.out.println("-------------------------");
            System.out.println("Car_ID | Brand | Model | Year | Price");

            while (resultSet.next()) {
                int carId = resultSet.getInt("Car_ID");
                String brand = resultSet.getString("Brand");
                String model = resultSet.getString("Model");
                int year = resultSet.getInt("Year");
                int price = resultSet.getInt("Price");

                System.out.printf("%-7d| %-6s| %-5s| %-4d| $%d\n", carId, brand, model, year, price);
            }

            System.out.print("\nEnter the Car ID to rent: ");
            int carId = sc.nextInt();
            sc.nextLine();

            String selectCarQuery = "SELECT * FROM CARS WHERE Car_ID = " + carId;
            ResultSet selectedCar = statement.executeQuery(selectCarQuery);

            if (selectedCar.next()) {
                String brand = selectedCar.getString("Brand");
                String model = selectedCar.getString("Model");
                int year = selectedCar.getInt("Year");
                int price = selectedCar.getInt("Price");

                System.out.println("\nCar Details:");
                System.out.println("-------------------------");
                System.out.println("\nRental Information:");
                System.out.println("Brand: " + brand);
                System.out.println("Model: " + model);
                System.out.println("Year: " + year);
                System.out.println("Price: $" + price);

                System.out.println("-------------------------");
                System.out.print("Enter the duration of the rental (in days): ");
                int rentalDuration = sc.nextInt();
                sc.nextLine();
                int discount = 0;
                if (rentalDuration >= 7) {
                    discount = 10;
                } else if (rentalDuration >= 3) {
                    discount = 5;
                }

                int totalCost = price * rentalDuration;
                int discountAmount = (totalCost * discount) / 100;
                int finalCost = totalCost - discountAmount;

                System.out.println("Total Cost: $" + finalCost);
            } else {
                System.out.println("Car not found. Please enter a valid Car ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void Run() {
        while (true) {

            System.out.println("1.Administrator");
            System.out.println("2.Customer");
            System.out.println("3.Exit");
            int choice = Validations.validation1(sc);

            if (choice == 1) {
                while (true) {
                    System.out.println("1.Login");
                    System.out.println("2.Signup");
                    System.out.println("3.Exit");
                    int choice2 = Validations.validation2(sc);
                    if (choice2 == 1) {
                        if (Employee_Login()) {
                            while (true) {
                                System.out.println("1.Add Car");
                                System.out.println("2.Update Car");
                                System.out.println("3.Remove Car");
                                System.out.println("4.Exit");
                                int choice3 = Validations.validation2(sc);
                                if (choice3 == 1) {
                                    this.add_car();
                                } else if (choice3 == 2) {
                                    this.update_car_price();
                                } else if (choice3 == 3) {
                                    this.delete_car();
                                } else if (choice3 == 4) {
                                    break;
                                }
                            }
                        }
                    } else if (choice2 == 2) {
                        this.Employee_Signup();
                    } else if (choice2 == 3) {
                        break;
                    }
                }
            } else if (choice == 2) {
                while (true) {
                    System.out.println("1.Login");
                    System.out.println("2.Signup");
                    System.out.println("3.Exit");
                    int choice2 = Validations.validation1(sc);

                    if (choice2 == 1) {
                        if (Customer_Login()) {
                            this.rentCar();
                        }
                    } else if (choice2 == 2) {
                        this.customer_Signup();
                    } else if (choice2 == 3) {
                        break;
                    }
                }
            } else if (choice == 3) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        {

            Rental_System interface1 = new Rental_System();
            interface1.connect_to_Database();
            interface1.create_Table();
            interface1.Run();
            interface1.closeConnection();
        }
    }
}
