package controller_applicativi;
import dao.GenericDao;
import entity.Adoption;
import dao.DaoFactory;
import dao.RequestAdoptionDaoDB;
import entity.Cat;
import facade.ApplicationFacade;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestAdoptionController{

    private static final Logger LOG = Logger.getLogger(RequestAdoptionController.class.getName());

    private final GenericDao<Adoption> adoptionDao = DaoFactory.getInstance().getRequestAdoptionDao();

    /**
     * Crea una richiesta di adozione.
     * @param a dati della richiesta
     * @return "success" | "error:validation" | "error:duplicate" | "error:database_error"
     */

    public String requestAdoption(Adoption a) {

        /* ---------- validation ---------- */
        if (!a.hasValidName() ||
                !a.hasValidSurname() ||
                !a.hasValidPhoneNumber() ||
                !a.hasValidEmail() ||
                !a.hasValidAddress() ||
                !a.hasValidStatus() ||
                a.getNameCat() == null || a.getNameCat().isBlank()) {
            return "error:validation";
        }

        /* ---------- check duplicates ---------- */

        if (adoptionDao instanceof RequestAdoptionDaoDB daoDB) {
            try {
                boolean exists = daoDB.existsByEmailAndCat(a.getEmail(), a.getNameCat());
                if (exists) {
                    return "error:duplicate";
                }
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, "Errore DB durante check duplicati", ex);
                return "error:database_error";
            }
        }

        /* ---------- PERSISTENZA ---------- */
        try {
            adoptionDao.create(a);
            ApplicationFacade.sendAdoptionConfirmationEmail(a);
            return "success";
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Errore DB durante insert adozione", ex);
            return "error:database_error";
        }
    }
    public List<Cat> readAdoptableCats() {
        GenericDao<Cat> catGenericDao = DaoFactory.getInstance().getCatDao();
        return catGenericDao.readAll();
    }
    
}

