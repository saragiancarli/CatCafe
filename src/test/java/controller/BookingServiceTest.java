package controller;


import bean.BookingBean;
import controller_applicativi.BookingService;
import dao.GenericDao;
import entity.Booking;

import entity.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    /* ──────────────────── mock & SUT ─────────────────── */

    @Mock
    private GenericDao<Booking> bookingDao;          // finto DAO

    @InjectMocks
    private BookingService service;                  // SUT

    /* ──────────────────── dati di supporto ───────────── */

    private User user;
    private BookingBean validBean;
    private BookingBean invalidBean;

    @BeforeEach
    void setUp() throws SQLException {

        /*  • utente corrente  */
        user = new User();
        user.setEmail("alice@example.com");

        /*  • prenotazione valida  */
        validBean = new BookingBean();
        validBean.setTitle("Tavolo Alice");
        validBean.setDate(LocalDate.of(2025, 8, 30));
        validBean.setTime(LocalTime.of(18, 30));
        validBean.setSeats(2);
        validBean.setConfirmationEmail("alice@example.com");

        /*  • prenotazione NON valida (0 posti) */
        invalidBean = new BookingBean();
        invalidBean.setTitle("Niente");
        invalidBean.setDate(LocalDate.of(2025, 5, 30));
        invalidBean.setTime(LocalTime.of(18, 30));
        invalidBean.setSeats(0);                      // <- errore
        invalidBean.setConfirmationEmail("alice@example.com");

        /*  • stubbing default: nessun duplicato, create() ok */
        lenient().when(bookingDao.readAll()).thenReturn(java.util.List.of()); // se usato
        lenient().doNothing().when(bookingDao).create(any(Booking.class));
    }

    /* ════════════════════════════════════════════════════ */
    @Test
    void testPrenotazione()  {
        /* given – nessuna prenotazione esistente */
    	lenient().when(bookingDao.readAll()).thenReturn(List.of());     // lista vuota

        /* when */
        String esito = service.book(user, validBean);

        /* then */
        assertEquals("success", esito);       
    }

    @Test
    void testDuplicato() throws SQLException {
        /* given – c'è già una prenotazione dello stesso utente / stessa data */
        Booking dup = new Booking();
        dup.setConfirmationEmail("alice@example.com");
        dup.setDate(LocalDate.of(2025, 5, 30));

        lenient().when(bookingDao.readAll()).thenReturn(List.of(dup));

        /* when */
        String esito = service.book(user, validBean);

        /* then */
        assertEquals("success", esito);
        verify(bookingDao, never()).create(any());
    }
    @Test
    void testValidazione() throws SQLException {
        // dati non validi (seats = 0)
        String esito = service.book(user, invalidBean);

        assertEquals("error:validation", esito);
        verify(bookingDao, never()).create(any());
    }
}