package org.example;

import java.sql.*;

public class IsolationACIDExample {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","postgres","root1");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acid-db","postgres","root1");
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
            stmt.setInt(1, 1);
            ResultSet rs1 = stmt.executeQuery();
            rs1.next();
            int balance1 = rs1.getInt("balance");
            rs1.close();

            stmt.setInt(1, 2);
            ResultSet rs2 = stmt.executeQuery();
            rs2.next();
            int balance2 = rs2.getInt("balance");
            rs2.close();

            int transferAmount = 100;
            if (balance1 >= transferAmount) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
                updateStmt.setInt(1, balance1 - transferAmount);
                updateStmt.setInt(2, 1);
                updateStmt.executeUpdate();

                Thread.sleep(5000);

                updateStmt.setInt(1, balance2 + transferAmount);
                updateStmt.setInt(2, 2);
                updateStmt.executeUpdate();

                conn.commit();
                System.out.println("Money transferred successfully");
            } else {
                System.out.println("Insufficient funds");
            }
        } catch (SQLException | InterruptedException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
