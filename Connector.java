package WellbeingCounter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Connector {
    public void dbStarter() {
        try {
            Runtime.getRuntime().exec("C:/Program Files/MySQL/MySQL Server 8.0/bin/mysqld");  // Replace with the full path to the mysqld executable if needed
            System.out.println("MySQL server started successfully.");
        } catch (IOException e) {
            System.err.println("Error starting MySQL server: " + e.getMessage());
        }

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/college"; // Replace with your database URL
        String username = "root"; // Replace with your database username
        System.out.println("Enter Password for the user " + username);
        String password = new Scanner(System.in).nextLine(); // Replace with your database password

        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT * FROM class"; // Replace with your desired query


        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}