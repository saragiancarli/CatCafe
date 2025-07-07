package controller;

import controller_applicativi.RequestAdoptionController;
import dao.BeanDao;
import dao.DaoFactory;
import entity.Adoption;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test  sviluppatato da Sara Giancarli
 * Test di integrazione su {RequestAdoptionController}.
 * Usiamo DAO mockati, così NON tocchiamo il DB vero.
 */
@ExtendWith(MockitoExtension.class)
class RequestAdoptionControllerTest {

    /* ------------ DAO mock ------------ */
    @Mock
    private BeanDao<Adoption> adoptionDao;

    /* ------------ static mock ---------- */
    private MockedStatic<DaoFactory> daoFactoryStatic;

    /* ------------ SUT ------------------ */
    private RequestAdoptionController controller;

    /* ------------ dati ----------------- */
    private Adoption validAdoption;
    private Adoption invalidAdoption;   // phone vuoto

    @BeforeEach
    void setUp()  {

        /* 1. static-mock di DaoFactory */
        daoFactoryStatic = mockStatic(DaoFactory.class);
        DaoFactory factoryMock = mock(DaoFactory.class);
        when(factoryMock.getRequestAdoptionDao()).thenReturn(adoptionDao);
        daoFactoryStatic.when(DaoFactory::getInstance).thenReturn(factoryMock);

        /* 2. Istanzia il controller (userà il DAO mock) */
        controller = new RequestAdoptionController();

       

        /* ---------- richiesta valida ---------- */
        validAdoption = new Adoption();
        validAdoption.setName("Alice");
        validAdoption.setSurname("Wonder");
        validAdoption.setPhoneNumber("3331234567");
        validAdoption.setEmail("alice@example.com");
        validAdoption.setAddress("Via dei Gatti 1");
        validAdoption.setNameCat("Birba");
        validAdoption.setStateAdoption(false);   // default

        /* ---------- richiesta non valida ---------- */
        invalidAdoption = new Adoption();
        invalidAdoption.setName("Bob");
        invalidAdoption.setSurname("Bad");
        invalidAdoption.setPhoneNumber("");        // <- errore
        invalidAdoption.setEmail("bob@example.com");
        invalidAdoption.setAddress("Via Cani 2");
        invalidAdoption.setNameCat("Fuffy");
        invalidAdoption.setStateAdoption(false);
    }

    @AfterEach
    void tearDown() {
        daoFactoryStatic.close();
    }

    /* ===================================================================== */

    @Test
    void testRichiestaValida() throws SQLException {

        String esito = controller.requestAdoption(validAdoption);

        assertEquals("success", esito);
        verify(adoptionDao, times(1)).create(any());
    }

    @Test
    void testRichiestaNonValida() throws SQLException {

        String esito = controller.requestAdoption(invalidAdoption);

        assertEquals("error:validation", esito);
        verify(adoptionDao, never()).create(any());
    }
}