package controller_applicativi;
import bean.AdoptionBean;
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

    public String requestAdoption(AdoptionBean bean) {

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
        Adoption adoptionEntity = new Adoption();
        adoptionEntity.setName(bean.getName());
        adoptionEntity.setSurname(bean.getSurname());
        adoptionEntity.setPhoneNumber(bean.getPhoneNumber());
        adoptionEntity.setEmail(bean.getEmail());
        adoptionEntity.setAddress(bean.getAddress());
        adoptionEntity.setNameCat(bean.getNameCat());
        adoptionEntity.setStateAdoption(bean.getStateAdoption());



        /* ---------- check duplicates ---------- */

        if (adoptionDao instanceof RequestAdoptionDaoDB daoDB) {
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
            adoptionDao.create(adoptionEntity);
            ApplicationFacade.sendAdoptionConfirmationEmail(adoptionEntity);
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

