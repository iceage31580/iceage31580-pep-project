package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account insertAccount(Account account)
    {
       Connection connection = ConnectionUtil.getConnection();

       try {

           String sql = "INSERT INTO account (username,password) VALUES(?,?)"; 
           PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

           preparedStatement.setString(1, account.getUsername()); 
           preparedStatement.setString(2, account.getPassword()); 
           
           preparedStatement.executeUpdate();
           ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

           if(pkeyResultSet.next()){
               int generated_account_id = (int) pkeyResultSet.getLong(1);
               return new Account(generated_account_id, account.getUsername(), account.getPassword());
           }
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return null;

       }

       public Account LoginAccount(String username, String password)
       {
           Connection connection = ConnectionUtil.getConnection();
          try {
           String sql = "SELECT * FROM account WHERE username = ? AND password = ? "; 
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, username); 
           preparedStatement.setString(2, password); 
           System.out.println(username+password);
           ResultSet rs = preparedStatement.executeQuery(); 
          
           while(rs.next())
           {
               Account account = new Account(rs.getInt("account_id"),
               rs.getString("username"),
               rs.getString("password"));
               return account;
           }
           }catch(SQLException e)
          {
           System.out.println(e.getMessage()); 
          } 
          
          return null; 
       }


    public List<Account> getAccountById(int accountId) {
        List<Message> messages = new ArrayList<>();
        List<Account> accounts = new ArrayList<>(); 
    
        Connection connection = ConnectionUtil.getConnection();
    
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                Message message = new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return accounts;
    }
    
    
}
