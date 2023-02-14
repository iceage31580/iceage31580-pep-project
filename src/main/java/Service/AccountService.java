package Service;
//import java.util.List;

//import org.eclipse.jetty.util.security.Password;

//import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AccountDAO;
import Model.Account;
//import io.javalin.http.Context; 

public class AccountService {
    AccountDAO accountDAO;


    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
    this.accountDAO = accountDAO;
    }

    
    public Account addAccount(Account account)
    {
        if(account.username != "" && account.password.length() >= 4)
        {
            return accountDAO.insertAccount(account); 
        }
        return null; 

       

    }

    public Account LoginAccount(String username, String password)
    {
        return accountDAO.LoginAccount(username, password);
    }



    }
