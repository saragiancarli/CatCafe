

import bean.LoginBean;
import dao.GenericDao;
import entity.User;
import entity.Client;
import entity.Staf;
import Exception.WrongLoginCredentialsException;

import controllerApplicativi.ValidateLogin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test sviluppatato da Matteo Amato
 * Test di integrazione su { ValidateLogin}.
 * Usiamo DAO mockati, così NON tocchiamo il DB vero.
 */
@ExtendWith(MockitoExtension.class)
class ValidateTestLogin {

    /* =========== MOCK =========== */
    @Mock  private GenericDao<User>  userDao;
    @Mock  private GenericDao<Staf>  stafDao;

    /* =========== SUT (System Under Test) =========== */
    @InjectMocks
    private ValidateLogin validator;

    /* =========== fixture =========== */
    private LoginBean validUserLogin;
    private LoginBean invalidLogin;
    private LoginBean blankLogin;
    private User      mockUser;

    @BeforeEach
    void setUp() throws SQLException {

        /* -------- utente e credenziali “buone” -------- */
        String pass  = "password123";
        String wrongpass="1";
       

        mockUser = new User();
        mockUser.setFirstName("Mario");
        mockUser.setLastName("Rossi");
        mockUser.setEmail("user@example.com");
        mockUser.setPhoneNumber("3201234567");
        
        mockUser.setPassword(pass);

        /* login bean corretto */
        validUserLogin = new LoginBean();
        validUserLogin.setEmail("user@example.com");
        validUserLogin.setPassword(pass);
        validUserLogin.setUserType("user");
        
       

        /* login bean errato */
        invalidLogin = new LoginBean();
        invalidLogin.setEmail("wrong@example");
        invalidLogin.setPassword(wrongpass);
        
        invalidLogin.setUserType("user");

        /* bean con campi vuoti */
        blankLogin = new LoginBean(); 
        
        blankLogin.setUserType("user");// tutti null / blank
      

        /* -------- stubbing DAO -------- */
        lenient().when(userDao.read("user@example.com")).thenReturn(mockUser);
        lenient().when(userDao.read("")).thenReturn(null);
       
       
        
        
    }

    /* ------------------------------------------------
       TEST #1  : credenziali corrette                 */
    @Test
    void testValidCredentials() throws WrongLoginCredentialsException, Exception{

       Client result = validator.authenticate(validUserLogin);

        assertNotNull(result, "L'utente deve essere trovato");
        assertEquals(mockUser.getEmail(),  result.getEmail());
        assertEquals(mockUser.getPassword(), result.getPassword());
        verify(userDao).read("user@example.com");   // verifica la chiamata al DAO
    }

    /* ------------------------------------------------
       TEST #2 : credenziali errate                    */
    @Test
    void testInvalidCredentials() {

    	Exception exception = assertThrows(WrongLoginCredentialsException.class, () -> {
            validator.authenticate(invalidLogin);
        });

        assertEquals("Campi mancanti o non validi", exception.getMessage());
    }

    /* ------------------------------------------------
       TEST #3 : campi vuoti / null                    */
    @Test
    void testEmptyFields() {
    	assertNull(validator.authenticate(blankLogin));
       
    }
}