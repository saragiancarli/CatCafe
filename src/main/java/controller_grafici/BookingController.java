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

/** Controller grafico della schermata di prenotazione. */
public class BookingController {

    

    private final NavigationService nav;
    private final BookingView       view = new BookingView();
    private final BookingService    service = new BookingService();
    private final String            typeOfLogin;

    public BookingController(NavigationService nav, String typeOfLogin) {
        this.nav = nav;
        this.typeOfLogin = typeOfLogin;
        addEventHandlers();
        loadAvailableActivities();          // <-- carica i RadioButton
    }

    /* ------------------------- EVENTI GUI ------------------------ */
    private void addEventHandlers() {
        view.getConfirmButton().setOnAction(_ -> handleConfirm());
        view.getCancelButton() .setOnAction(_ -> nav.navigateToHomePage(nav, typeOfLogin));
    }

    /* -------------------- CONFERMA PRENOTAZIONE ------------------ */
    private void handleConfirm() {
        BookingBean bean = ModelBeanFactory.getBookingBean(view);

        /* validazione base GUI */
        boolean ok = true; view.hideAllErrors();
        if (!bean.hasValidTitle())  { view.setNomeError("Titolo obbligatorio"); ok=false; }
        if (!bean.hasValidDates())  { view.setDataError("Data/ora non valide"); ok=false; }
        if (!bean.hasValidSeats())  { view.setPartecipantiError("Posti 1-50");   ok=false; }
        if (!ok) return;

        /* attività scelta (può essere null) */
        String selected = view.getSelectedActivityName();
        bean.setFreeActivities(selected == null ? List.of()
                                                : List.of(selected));

        /* --- business layer --- */
        Client current = ApplicationFacade.getUserFromLogin();
        String esito   = service.book(current, bean);

        switch (esito) {
            case "success"           -> nav.navigateToHomePage(nav, typeOfLogin);
            case "error:duplicate"   -> view.setDataError("Prenotazione già presente per quel giorno");
            case "error:validation"  -> view.setNomeError ("Campi non validi");
            default -> view.setNomeError("Errore di sistema, riprova");
            
        }
    }

    /* ------------------- CARICA LE ATTIVITÀ ---------------------- */
    private void loadAvailableActivities() {
        List<Activity> activities = service.getAvailableActivities();
        List<String> names = activities.stream().map(Activity::getName).toList();
        view.addActivityRadios(names);   // la View crea i RadioButton
    }

    /* ---------------------------- API per Navigation ------------ */
    public VBox getRoot() { return view.getRoot(); }
}