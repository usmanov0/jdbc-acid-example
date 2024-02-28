package org.example;

import java.sql.*;

public class AtomicityACIDExample {
    public static void main(String[] args) {
        Connection connection = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","postgres","root1");
            connection.setAutoCommit(false);

            PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
            stmt.setInt(1,1);
            ResultSet rs1 = stmt.executeQuery();
            rs1.next();
            int balance1 = rs1.getInt("balance");
            rs1.close();

            stmt.setInt(1,2);
            ResultSet rs2 = stmt.executeQuery();
            rs2.next();
            int balance2 = rs2.getInt("balance");
            rs2.close();


            int transformAmount = 100;
            if (balance1 >= transformAmount){
                PreparedStatement updateStmt = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
                updateStmt.setInt(1,balance1 - transformAmount);
                updateStmt.setInt(2,1);
                updateStmt.executeQuery();

                updateStmt.setInt(1,balance2 + transformAmount);
                updateStmt.setInt(2,2);
                updateStmt.executeQuery();
                connection.commit();
                System.out.println("Money transfered Successfully");
            }else {
                System.out.println("Insufficient Funds");
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
}
