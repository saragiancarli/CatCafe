package controller_applicativi;

import entity.Adoption;
import dao.BeanDao;
import dao.DaoFactory;

import dao.RequestAdoptionDaoDB;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestAdoptionController{

    private static final Logger LOG = Logger.getLogger(RequestAdoptionController.class.getName());

    private final BeanDao<Adoption> beanDao = DaoFactory.getInstance().getRequestAdoptionDao();

    /**
     * Crea una richiesta di adozione.
     * @param bean dati della richiesta
     * @return "success" | "error:validation" | "error:duplicate" | "error:database_error"
     */
    public String requestAdoption(Adoption bean) {

        /* ---------- validation ---------- */
        if (!bean.hasValidName() ||
                !bean.hasValidSurname() ||
                !bean.hasValidPhoneNumber() ||
                !bean.hasValidEmail() ||
                !bean.hasValidAddress() ||
                !bean.hasValidStatus() ||
                bean.getNameCat() == null || bean.getNameCat().isBlank()) {
            return "error:validation";
        }

        /* ---------- check duplicates ---------- */

        if (beanDao instanceof RequestAdoptionDaoDB daoDB) {
            try {
                boolean exists = daoDB.existsByEmailAndCat(bean.getEmail(), bean.getNameCat());
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
            beanDao.create(bean);
            return "success";
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Errore DB durante insert adozione", ex);
            return "error:database_error";
        }
    }
}



