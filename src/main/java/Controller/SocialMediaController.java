package Controller; 

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List; 

import javax.lang.model.util.ElementScanner6; 

import org.eclipse.jetty.websocket.core.internal.messages.MessageSink; 

import com.fasterxml.jackson.core.JsonProcessingException; 
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account; 
import Model.Message; 
import io.javalin.Javalin; 
import io.javalin.http.Context; 

import Service.AccountService; 
import Service.MessageService; 


public class SocialMediaController{
    AccountService accountService; 
    MessageService messageService; 

    public Javalin startAPI()
    {
        Javalin app = Javalin.create();
        app.post("/register", this::AddedAccountHandler); 
        app.post("/login", this::LoginHandler); //changed this, 
        app.get("/messages", this::getAllMessagesHandler); 
        app.post("/messages", this::insertMessage); 
        app.delete("/messages/{message_id}", this::deleteResponseHandler); 
        app.get("/message/{message_id}", this::getMessagebyIdHandler); 
        app.patch("/messages/{message_id}", this::updateResponseHandler);
        app.get("/account/{account_id}/messages", this::getMessagebyAccountIdHandler); 

        return app; 
    }

    private void AddedAccountHandler(Context ctx) throws JsonProcessingException 
    {
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(),Account.class); 
        Account newAccount = accountService.addAccount(account);
        if(newAccount != null)
            {
                ctx.json(mapper.writeValueAsString(newAccount)); 
                ctx.status(200); 
            }
            else
            {
                ctx.status(400); 
            }
        


    }

    private void LoginHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class); 
        Account existingAccount  = accountService.LoginAccount(account.getUsername(), account.getPassword()); 
        if(existingAccount != null)
            {
                ctx.json(mapper.writeValueAsString(existingAccount)); 
                ctx.status(200); 
            }
            else
            {
                ctx.status(400); 
            }
        

    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException
    {
        List<Message> message = messageService.getAllMessages(); 
        ctx.json(message); 

        ObjectMapper mapper = new ObjectMapper(); 
        ctx.json(mapper.writeValueAsString(message)); 
        ctx.status(200); 


    }

    private void insertMessage(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class);
        if(message.getMessage_text().isBlank())
        {
            ctx.status(400); 
            return; 
        }
        Message newMessage = messageService.persistMessage(message); 
        if(newMessage != null)
        {
            ctx.json(mapper.writeValueAsString(newMessage)); 
            ctx.status(200); 
        }
        else 
        {
            ctx.status(400); 
        }
    }

    private void getMessagebyIdHandler(Context ctx) throws JsonProcessingException
    {
        int Message_id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message existingMessage = messageService.getMessageById(Message_id); 
        if(existingMessage != null)
        {
            ctx.json(existingMessage); 
            ctx.status(200); 
        }
        else
        {
            ctx.status(400); 
        }
    }

    private void updateResponseHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class); 
        int updatedMessage = Integer.parseInt(ctx.pathParam("message_id")); 
        Message existingMessage = messageService.updateMessages(updatedMessage, message); 
        if(existingMessage != null)
        {
            ctx.json(mapper.writeValueAsString(existingMessage)); 
            ctx.status(200); 
        }
        else
        {
            ctx.status(400); 
        }

        

    }

    private void deleteResponseHandler(Context ctx) throws JsonProcessingException
        {
            ObjectMapper mapper = new ObjectMapper(); 
            int messageID = Integer.parseInt(ctx.pathParam("message_id")); 
            Message deletedMessage = messageService.deleteMessage(messageID); 
            mapper.writeValueAsString(deletedMessage); 

            if(deletedMessage != null)
            {
                ctx.json(mapper.writeValueAsString(deletedMessage)); 
                ctx.status(200); 
            }
            else
            {
                ctx.status(400); 
            }
        }

        private void getMessagebyAccountIdHandler(Context ctx) throws JsonProcessingException
        {
            int accountId = Integer.parseInt(ctx.pathParam("account_id")); 
            List<Message> messages = messageService.gettingMessagesByAccountId(accountId); 
            if(messages != null)
            {
                ctx.json(messages); 
                ctx.status(200); 
            }
            else
            {
                ctx.status(400); 
            }

        }
 
   

}