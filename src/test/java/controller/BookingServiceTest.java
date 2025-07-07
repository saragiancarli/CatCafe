package controller;

import bean.BookingBean;
import controller_applicativi.BookingService;
import dao.DaoFactory;
import dao.GenericDao;
import entity.Booking;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test sviluppatato da Matteo Amato
 * Test di integrazione su {BookingService}.
 * Usiamo DAO mockati, così NON tocchiamo il DB vero.
 */
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    /* ---------- mock DAO ----------- */
    @Mock
    private GenericDao<Booking> bookingDao;

    /* ---------- static mock --------- */
    private MockedStatic<DaoFactory> daoFactoryStatic;

    /* ---------- SUT ----------- */
    private BookingService service;

    /* ---------- dati ----------- */
    private BookingBean validBean;
    private BookingBean invalidBean;   // seats = 0

    @BeforeEach
    void setUp()  {

        /* 1. static-mock di DaoFactory */
        daoFactoryStatic = mockStatic(DaoFactory.class);

        DaoFactory factoryMock = mock(DaoFactory.class);
        when(factoryMock.getBookingDao()).thenReturn(bookingDao);

        daoFactoryStatic.when(DaoFactory::getInstance).thenReturn(factoryMock);

        /* 2. Istanzia il service (userà il DAO mock grazie allo static-mock) */
        service = new BookingService();

        
        /* -------- bean valido -------- */
        validBean = new BookingBean();
        validBean.setTitle("Tavolo Alice");
        validBean.setDate (LocalDate.of(2026, 8, 30));
        validBean.setTime (LocalTime.of(18, 30));
        validBean.setSeats(2);
        validBean.setConfirmationEmail("alice@example.com");
        validBean.setFreeActivities(List.of("Aperitivo"));

        /* -------- bean non valido -------- */
        invalidBean = new BookingBean();
        invalidBean.setTitle("Errore");
        invalidBean.setDate (LocalDate.of(2025, 5, 30));
        invalidBean.setTime (LocalTime.of(18, 30));
        invalidBean.setSeats(0);                         // errore sintattico
        invalidBean.setConfirmationEmail("alice@example.com");
        invalidBean.setFreeActivities(List.of("Aperitivo"));
    }

    @AfterEach
    void tearDown() {
        daoFactoryStatic.close();   // IMPORTANTISSIMO: libera lo static-mock
    }

    /* ===================================================================== */

    @Test
    void testPrenotazioneValida() throws SQLException {

        String esito = service.book(validBean);

        assertEquals("success", esito);
        verify(bookingDao, times(1)).create(any());
    }

    @Test
    void testPrenotazioneNonValida() throws SQLException {

        String esito = service.book(invalidBean);

        assertEquals("error:validation", esito);
        verify(bookingDao, never()).create(any());
    }
}
