package ControllerApplicativi;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAO.DaoFactory;
import DAO.GenericDao;
import Entity.Booking;
import Entity.BookingStatus;
import Facade.ApplicationFacade;

public class ManageBookingService {

    private static final Logger LOG =
            Logger.getLogger(ManageBookingService.class.getName());

    private final GenericDao<Booking> bookingDao =
            DaoFactory.getInstance().getBookingDao();

    /* ----- lettura di tutte le prenotazioni ----- */
    public List<Booking> loadAll() {
        return bookingDao.readAll();           // DB  o Memory – è uguale
    }

    /* ----- conferma (-> BOOKED) ------------------ */
    public void confirm(int id) {
    	try {
            Booking b = bookingDao.read(id);
            if (b == null || b.getStatus() != BookingStatus.PENDING) 
            	 LOG.log(Level.SEVERE, "Nessuna Prenotazione rilevata");
            else {
           
            ApplicationFacade.sendBookingConfirmationEmail(b);
            changeStatus(id, BookingStatus.BOOKED);}

            

            /* avvisa il cliente */
           

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "DB error in confirm()", e);
        }
    }

    /* ----- cancella  (-> CANCELED) --------------- */
    public void cancel(int id) {
    	try {
            Booking b = bookingDao.read(id);
            if (b == null || b.getStatus() != BookingStatus.PENDING) return;
            changeStatus(id, BookingStatus.CANCELLED);

            

            /* avvisa il cliente */
            ApplicationFacade.sendBookingCancelledEmail(b);

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "DB error in confirm()", e);
        }
    }

    /* ============================================================= */
    private void changeStatus(int id, BookingStatus newStatus) {
        try {
            Booking b = bookingDao.read(id);
            if (b == null) return;

            if (newStatus == BookingStatus.BOOKED)  b.confirm();
            if (newStatus == BookingStatus.CANCELLED) b.cancel();

            bookingDao.update(b);               // persiste la modifica
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Errore DB durante l’aggiornamento", e);
        }
    }
}