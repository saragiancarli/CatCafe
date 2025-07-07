package controller_grafici;

import bean.BookingBean;
import bean.ModelBeanFactory;
import controller_applicativi.BookingService;
import entity.Activity;
import entity.Client;
import facade.ApplicationFacade;
import javafx.scene.layout.VBox;
import view.BookingView;

import java.util.List;

/**
 * Controller GUI per la view “standard” di prenotazione.
 */
public class BookingController {

    private final NavigationService nav;
    private final String            typeOfLogin;

    private final BookingView    view   = new BookingView();
    private final BookingService service = new BookingService();
    private final Client         currentUser = ApplicationFacade.getUserFromLogin();

    public BookingController(NavigationService nav, String typeOfLogin) {
        this.nav         = nav;
        this.typeOfLogin = typeOfLogin;
        addEventHandlers();
        loadAvailableActivities();
    }

    /* ------------------------- EVENTI ------------------------- */
    private void addEventHandlers() {
        view.getConfirmButton().setOnAction(_ -> handleConfirm());
        view.getCancelButton() .setOnAction(_ -> nav.navigateToHomePage(nav, typeOfLogin));
    }

    /* --------------- CONFERMA PRENOTAZIONE -------------------- */
    private void handleConfirm() {

        BookingBean bean = ModelBeanFactory.getBookingBean(view, currentUser);

        /* --- mini-check di PRESENZA per UX --- */
        boolean ok = true; view.hideAllErrors();
        if (bean.getTitle() == null || bean.getTitle().isBlank())
            { view.setNomeError("Titolo obbligatorio"); ok = false; }
        if (bean.getDate() == null || bean.getTime() == null)
            { view.setDataError("Data/ora mancanti"); ok = false; }
        if (bean.getSeats() <= 0)
            { view.setPartecipantiError("Inserisci i posti"); ok = false; }
        if (!ok) return;

        /* attività scelta (facoltativa) */
        String sel = view.getSelectedActivityName();
        bean.setFreeActivities(sel == null ? List.of() : List.of(sel));

        /* --- delega al service --- */
        switch (service.book(bean)) {
            case "success"          -> nav.navigateToHomePage(nav, typeOfLogin);
            case "error:duplicate"  -> view.setDataError("Prenotazione già presente quel giorno");
            case "error:validation" -> view.setNomeError("Dati non validi");
            default                 -> view.setNomeError("Errore di sistema, riprova");
        }
    }

    /* --------------- CARICA ATTIVITÀ --------------------------- */
    private void loadAvailableActivities() {
        List<Activity> acts = service.getAvailableActivities();
        view.addActivityRadios(acts.stream().map(Activity::getName).toList());
    }

    /* --------------- API Navigation ---------------------------- */
    public VBox getRoot() { return view.getRoot(); }
}