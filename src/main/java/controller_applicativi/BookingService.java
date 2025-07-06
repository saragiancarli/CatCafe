package controller_applicativi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.BookingBean;

import dao.DaoFactory;
import dao.GenericDao;
import entity.Activity;
import entity.Booking;






public class BookingService {
	private List<Activity> selectedActivities = new ArrayList<>();

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
    public String book( BookingBean bean) {

        /* ---------- 1. VALIDAZIONE lato business ---------- */
        if (!bean.hasValidTitle()  ||
            !bean.hasValidDates()  ||
            !bean.hasValidSeats()  ||
            bean.getConfirmationEmail() == null ||
            bean.getConfirmationEmail().isBlank())
        {
            return "error:validation";
        }

       
        
       

        /* ---------- 3. MAPPING Bean → Entity ---------- */
        Booking b = new Booking();
        b.setTitle(bean.getTitle());
        b.setDate (bean.getDate());
        b.setTime (bean.getTime());
        b.setSeats(bean.getSeats());
        b.setConfirmationEmail(bean.getConfirmationEmail());  
        b.setFreeActivities(bean.getFreeActivities());                                          

        /* ---------- 4. PERSISTENZA ---------- */
        try {
            genericDao.create(b);
            return "success";

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Errore DB durante insert booking", ex);
            return "error:database_error";
        }
    }
    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }
    public List<Activity> getAvailableActivities() {
        return DaoFactory.getAvailableActivities();
    }
}