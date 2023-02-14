package Service;
//import java.util.ArrayList;
import java.util.List;

//import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.MessageDAO;
import Model.Message;


public class MessageService extends Message{
    private MessageDAO messageDAO;
 
    
    public MessageService()
    {
        messageDAO = new MessageDAO(); 

    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO; 
    }

    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    public Message persistMessage(Message message)
    {
        return messageDAO.persistMessage(message); 

    }

    public Message deleteMessage(int message_id)
    {
        if(messageDAO.getMessageById(message_id) != null){

        Message message = messageDAO.getMessageById(message_id);

        messageDAO.deleteMessages(message_id);
        return message;
         }
         return null;
    }

    public Message getMessageById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        if (message.getMessage_text().isEmpty()) {
            return null;
        } else {
            return message;
        }
    }
    
    
    public Message updateMessages(int message_id, Message message){
        if(getMessageById(message_id) != null && message.message_text != "" && message.message_text.length() <= 255)
        {
            return messageDAO.updateMessage(message_id, message); 
        }
        return null; 
       
        
    }
  
    public List<Message> gettingMessagesByAccountId(int account_id)
    {
         return this.messageDAO.returnMessagebyAccountID(account_id);
    }
}

    
    

