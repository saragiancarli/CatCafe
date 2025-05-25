package Facade;


import javafx.scene.control.Alert;

import java.util.logging.Logger;

import bean.LoginBean;
import bean.ModelBeanFactory;
import controllerApplicativi.ValidateLogin;
import dao.DaoFactory;
import dao.DaoFactory.Store;
import entity.Booking;
import entity.Client;
import Exception.WrongLoginCredentialsException;

public final class ApplicationFacade {

    /* ------------------------------------------------------------ */
    private static final Logger LOG = Logger.getLogger(ApplicationFacade.class.getName());

         // inizializzata da init()
    private static ValidateLogin validator;  // idem

    private ApplicationFacade() {}           // utility-class: no istanze

    
    /* ============================================================ */
    public static void init(Store storageMode) {
        

        DaoFactory.getInstance();
        DaoFactory.setStorageOption(storageMode);

        validator = new ValidateLogin();

        LOG.info(() -> "ApplicationFacade inizializzata in modalità " + storageMode);
    }

    

    /** Controlla se le credenziali sono valide. */
    public static boolean isLoginValid(LoginBean bean) {
        ensureInit();
        try {
            return validator.authenticate(bean) != null;
        } catch (WrongLoginCredentialsException e) {
            return false;
        }
    }

    /** Ritorna l’oggetto Client della sessione corrente, oppure null. */
    public static Client getUserFromLogin() {
        ensureInit();
        try {
            return validator.authenticate(ModelBeanFactory.loadLoginBean());
        } catch (WrongLoginCredentialsException e) {
            return null;
        }
    }

    /** Ritorna "user", "staf", … se l’utente in sessione è ancora valido. */
    public static String checkLoginStatus() {
        ensureInit();
        try {
            LoginBean bean = ModelBeanFactory.loadLoginBean();
            if (bean == null) return null;
            if (validator.authenticate(bean) == null) return null;
            return bean.getUserType();
        } catch (WrongLoginCredentialsException e) {
            LOG.info("Credenziali non valide in sessione"); return null;
        }
    }
    public static void sendBookingConfirmationEmail(Booking b) {
   	 
        /* destinatario = indirizzo inserito in fase di prenotazione   */
        String to = b.getConfirmationEmail();
                // niente mail
        
        String subject;
        String body;
        
      
        subject = "Prenotazione #" + b.getId() + " confermata!";
        body = """
               Ciao %s,

               siamo lieti di confermare la tua prenotazione:

               • ID prenotazione : %d
               • Nome            : %s
               • Data            : %s
               • Ora             : %s
               • Partecipanti    : %d

               Ti aspettiamo! 😺
               """.formatted(
                      b.getTitle(), b.getId(),
                      b.getTitle(), b.getDate(),
                      b.getTime(),  b.getSeats());
                EmailService.sendEmail(to, subject, body);
            }
public static void sendBookingCancelledEmail(Booking b) {
	 
    /* destinatario = indirizzo inserito in fase di prenotazione   */
    String to = b.getConfirmationEmail();
          // niente mail
    
    String subject;
    String body;
                subject = "Prenotazione #" + b.getId() + " annullata";
                body = """
                       Ciao %s,

                       la tua prenotazione  del %s alle %s è stata
                       annullata.

                       Per dubbi o nuove richieste contattaci pure!
                       """.formatted(
                              b.getTitle(), b.getId(),
                              b.getDate(), b.getTime());
                EmailService.sendEmail(to, subject, body);
            
            
        }
    /* ===================  Helper grafici  ======================= */
   
    public static void showErrorMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    
    /* =======================  private debug  ========================== */
    
    private static void ensureInit() {
        if (validator == null)
            throw new IllegalStateException("ApplicationFacade.init(...) non è stato chiamato!");
    }
}