package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO{
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message; ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //so the next function should "post" the messages hmmmm
    //insertMessage

    public Message persistMessage(Message message)//saves to database 
    {
        Connection connection = ConnectionUtil.getConnection();
        //List<Message> messages = new ArrayList<>();
        try{
            String sql = "INSERT INTO message (posted_by, message_text ,time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, message.getPosted_by()); 
        preparedStatement.setString(2, message.getMessage_text()); 
        preparedStatement.setLong(3, message.getTime_posted_epoch()); 

        preparedStatement.executeUpdate(); 

        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

        if(pkeyResultSet.next()){
            int generated_message_id = (int) pkeyResultSet.getLong(1);
            return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
    }
        
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; 

    }
   
    public Message deleteMessages(int message_id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "DELETE FROM message WHERE message_id = ? "; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 

           
            preparedStatement.setInt(1,message_id); 
            


            preparedStatement.executeUpdate(); 

        }

        catch(SQLException e)
        {
            System.out.println(e.getMessage()); 
        }
        return null; 

    }
  

    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            } else {

                
                // return an empty message to indicate that the requested message does not exist
                return new Message(0, 0, "", 0);

                

                //return null; 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());       
        }
        return null; 
    }
    

    public Message updateMessage(int message_id, Message message )
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            if(getMessageById(message_id) == null)
            {
                return null; 
            }
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            //getPosted_by =?, getMessage_text = ?, getTime_posted_epoch = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            /* 
            preparedStatement.setInt(1,message.getPosted_by()); 
            preparedStatement.setString(2,message.getMessage_text()); 
            preparedStatement.setLong(3,message.getTime_posted_epoch()); 
            */
            
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2,message_id); 
            


            preparedStatement.executeUpdate(); 
            return getMessageById(message_id); 
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage()); 
        }
        return null;
        
    }


 


    //public List<Flight> getAllFlightsFromCityToCity(String departure_city, String arrival_city)
    //
    public List<Message> returnMessagebyAccountID(int account_id)
    {
        Connection connection = ConnectionUtil.getConnection();//need a list here 
        List<Message> messages = new ArrayList<>(); 
        try
        {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 

            //Account account;
            preparedStatement.setInt(1,account_id); 
            //preparedStatement.setInt(2, message.getPosted_by());

            ResultSet rs = preparedStatement.executeQuery();
            /*Account username;
            if(username != null && getPosted_by != null)
            {
                return message; 
            }
            */
            while(rs.next())
            {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message); 

            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage()); 
        }
        return null; 
    
}

}



  

