package controller;

import bean.ClientRegistrationBean;
import controller_applicativi.ClientRegistrationController;
import dao.DaoFactory;
import dao.GenericDao;
import entity.Staf;
import entity.User;
import exception.UserAlreadyInsertedException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test per {@link ClientRegistrationController}.
 */
@ExtendWith(MockitoExtension.class)
class ClientRegistrationControllerTest {

    /* ---------- DAO mock ---------- */
    @Mock
    private GenericDao<User> userDao;
    @Mock
    private GenericDao<Staf> stafDao;

    /* ---------- static mock ---------- */
    private MockedStatic<DaoFactory> daoFactoryStatic;

    /* ---------- SUT ---------- */
    private ClientRegistrationController controller;

    /* ---------- bean di supporto ---------- */
    private ClientRegistrationBean validUserBean;
    private ClientRegistrationBean invalidPwdBean;

    @BeforeEach
    void setUp() {

        /* 1. static-mock di DaoFactory */
        daoFactoryStatic = mockStatic(DaoFactory.class);
        DaoFactory factoryMock = mock(DaoFactory.class);
        when(factoryMock.getUserDao()).thenReturn(userDao);
        when(factoryMock.getStafDao()).thenReturn(stafDao);
        daoFactoryStatic.when(DaoFactory::getInstance).thenReturn(factoryMock);

        /* 2. SUT */
        controller = new ClientRegistrationController();

       
        /* 4. Bean valido */
        validUserBean = new ClientRegistrationBean();
        validUserBean.setFirstName("Alice");
        validUserBean.setLastName("Wonder");
        validUserBean.setEmail("alice@example.com");
        validUserBean.setPhoneNumber("3331234567");
        validUserBean.setPassword("pwd12311111111");
        validUserBean.setRepeatPassword("pwd12311111111");
        validUserBean.setUserType("user");

        /* 5. Bean con password non coincidenti */
        invalidPwdBean = new ClientRegistrationBean();
        invalidPwdBean.setFirstName("Bob");
        invalidPwdBean.setLastName("Bad");
        invalidPwdBean.setEmail("bob@example.com");
        invalidPwdBean.setPhoneNumber("111222333");
        invalidPwdBean.setPassword("x");
        invalidPwdBean.setRepeatPassword("y");   // non coincide
        invalidPwdBean.setUserType("user");
    }

    @AfterEach
    void tearDown() {
        daoFactoryStatic.close();   // libera lo static-mock
    }

    /* ===================================================================== */

    @Test
    void testRegistrazioneValida() throws SQLException {

        String esito = controller.registerUser(validUserBean);

        assertEquals("success", esito);
        verify(userDao, times(1)).create(any());   // DAO chiamato
        verify(stafDao, never()).create(any());
    }

    @Test
    void testErroreValidazione() throws SQLException {

        String esito = controller.registerUser(invalidPwdBean);

        assertEquals("error:validation", esito);
        verify(userDao, never()).create(any());
        verify(stafDao, never()).create(any());
    }

    @Test
    void testUtenteEsistente() throws SQLException {

        /* il DAO lancia eccezione di duplicato */
        doThrow(new UserAlreadyInsertedException("Esiste già"))
                .when(userDao).create(any());

        String esito = controller.registerUser(validUserBean);

        assertEquals("error:user_already_exists", esito);
        verify(userDao, times(1)).create(any());
    }
}
