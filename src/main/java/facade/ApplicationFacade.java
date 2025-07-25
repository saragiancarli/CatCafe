package facade;


import entity.Adoption;
import javafx.scene.control.Alert;

import java.util.logging.Logger;

import bean.LoginBean;
import bean.ModelBeanFactory;
import controller_applicativi.ValidateLogin;
import dao.DaoFactory;
import dao.DaoFactory.Store;
import entity.Booking;
import entity.Client;
import entity.Review;
import exception.WrongLoginCredentialsException;

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
        } catch (WrongLoginCredentialsException _) {
            return false;
        }
    }

    /** Ritorna l’oggetto Client della sessione corrente, oppure null. */
    public static Client getUserFromLogin() {
        ensureInit();
        try {
            return validator.authenticate(ModelBeanFactory.loadLoginBean());
        } catch (WrongLoginCredentialsException _) {
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
        } catch (WrongLoginCredentialsException _) {
            LOG.info("Credenziali non valide in sessione"); return null;
        }
    }
    
    public static void sendReviewConfirmationEmail(Review r) {

        String to = r.getEmail();
        String subject = "Grazie per la tua recensione!";
        String body = """
                      Ciao,

                      abbiamo ricevuto la tua recensione del %s alle %s.

                      Valutazione: %d stelle
                      Servizio speciale: %s

                      Testo della recensione:
                      %s

                      A presto! 😺
                      """
                .formatted(r.getDate(), r.getTime(),
                           r.getStars(), 
                           r.getSpecialService() == null ? "-" : r.getSpecialService(),
                           r.getBody());

        EmailService.sendEmail(to, subject, body);
    }
    public static void sendBookingReceivedEmail(Booking b) {

        /* destinatario = mail dell’utente loggato (già presente nel bean) */
        String to = b.getConfirmationEmail();

        String subject = "Abbiamo ricevuto la tua prenotazione!";
        String body = """
                      Ciao %s,

                      abbiamo ricevuto la tua richiesta di prenotazione.

                      • ID provvisorio     : %d
                      • Nome prenotazione  : %s
                      • Data               : %s
                      • Ora                : %s
                      • Partecipanti       : %d

                      Il nostro staff la verificherà al più presto: riceverai
                      un’altra e-mail di conferma non appena sarà approvata. 😺
                      """
                .formatted(b.getTitle(),
                           b.getId(),
                           b.getTitle(),
                           b.getDate(),
                           b.getTime(),
                           b.getSeats());

        EmailService.sendEmail(to, subject, body);
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
    public static void sendAdoptionConfirmationEmail(Adoption a) {
        String to = a.getEmail();
        String subject;
        String body;


        subject = "La richiesta di adozione di #" + a.getNameCat() + " confermata!";
        body = """
               Ciao %s %s,

               siamo lieti di confermare la tua adozione, facciamo un recap:

               • Gatto che si sta adottando: %s
               • Il numero di telefono in cui vi contatteremo        : %s
               • L' indirizzo in cui i nostri collaboratori terranno il sopraluogo    : %s

               Ci vediamo presto! 😺
               """.formatted(
                a.getName(), a.getSurname(),
                a.getNameCat(),
                a.getPhoneNumber(), a.getAddress());
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