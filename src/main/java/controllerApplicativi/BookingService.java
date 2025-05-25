package controllerApplicativi;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.BookingBean;
import dao.BookingDaoDB;
import dao.DaoFactory;
import dao.GenericDao;
import Entity.Booking;
import Entity.Client;


import java.time.LocalDate;



public class BookingService {

    private static final Logger LOG =
            Logger.getLogger(BookingService.class.getName());

    /* DAO generico (CRUD) */
    private final GenericDao<Booking> genericDao =
            DaoFactory.getInstance().getBookingDao();

    /* ============================================================= */
    /**
     * Crea una prenotazione.
     *
     * @return "success" | "error:validation" | "error:duplicate"
     *         | "error:database_error"
     */
    public String book(Client user, BookingBean bean) {

        /* ---------- 1. VALIDAZIONE lato business ---------- */
        if (!bean.hasValidTitle()  ||
            !bean.hasValidDates()  ||
            !bean.hasValidSeats()  ||
            bean.getConfirmationEmail() == null ||
            bean.getConfirmationEmail().isBlank())
        {
            return "error:validation";
        }

       
        LocalDate giorno = bean.getDate();

        // pattern-matching cast – funziona solo se il DAO reale è BookingDaoDB
        if (genericDao instanceof BookingDaoDB db &&
            db.existsByUserAndCheckIn(user.getEmail(), giorno))
        {
            return "error:duplicate";
        }

        /* ---------- 3. MAPPING Bean → Entity ---------- */
        Booking b = new Booking();
        b.setTitle(bean.getTitle());
        b.setDate (bean.getDate());
        b.setTime (bean.getTime());
        b.setSeats(bean.getSeats());
        b.setConfirmationEmail(bean.getConfirmationEmail());  // <-- ESSENZIALE
                                                  

        /* ---------- 4. PERSISTENZA ---------- */
        try {
            genericDao.create(b);
            return "success";

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Errore DB durante insert booking", ex);
            return "error:database_error";
        }
    }
}