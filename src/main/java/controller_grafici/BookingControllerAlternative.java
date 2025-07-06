package controller_grafici;

import bean.BookingBean;
import controller_applicativi.BookingService;
import entity.Activity;
import entity.Client;
import facade.ApplicationFacade;
import javafx.scene.layout.VBox;
import view.BookingViewAlternative;

import java.util.List;
import java.util.logging.Logger;

public class BookingControllerAlternative {

    private static final Logger LOG = Logger.getLogger(BookingControllerAlternative.class.getName());

    private final BookingViewAlternative view = new BookingViewAlternative();
    private final NavigationService     nav;
    private final BookingService        service = new BookingService();
    private final String                typeOfLogin;

    public BookingControllerAlternative(NavigationService nav, String typeOfLogin) {
        this.nav = nav;
        this.typeOfLogin = typeOfLogin;
        addEventHandlers();
        loadActivities();                     // <-- carica la tendina
    }

    public VBox getRoot(){ return view.getRoot(); }

    /* -------------------- eventi GUI -------------------- */
    private void addEventHandlers(){
        view.getConfirmButton().setOnAction(_ -> handleConfirm());
        view.getCancelButton() .setOnAction(_ -> nav.navigateToHomePage(nav, typeOfLogin));
    }

    /* -------------------- conferma ---------------------- */
    private void handleConfirm(){

        BookingBean bean = new BookingBean();
        bean.setTitle            (view.getNomePrenotazione());
        bean.setDate             (view.getDate());
        bean.setTime             (view.getTime());
        bean.setSeats            (view.getParticipants());
        bean.setConfirmationEmail(view.getConfirmationEmail());

        /* attività scelta (può essere null) */
        String selected = view.getSelectedActivity();
        bean.setFreeActivities(selected == null ? List.of() : List.of(selected));

        /* --- validazione GUI minima --- */
        boolean ok = true; view.hideAllErrors();
        if (!bean.hasValidTitle()){ view.setNomeError("Titolo obbligatorio"); ok=false; }
        if (!bean.hasValidDates()){ view.setDataError("Data/ora non valide");  ok=false; }
        if (!bean.hasValidSeats()){ view.setPartecipantiError("Posti 1-50");   ok=false; }
        if (!ok) return;

        /* --- business layer --- */
        Client current = ApplicationFacade.getUserFromLogin();
        String esito  = service.book(current, bean);

        switch (esito){
            case "success"            -> nav.navigateToHomePage(nav, typeOfLogin);
            case "error:duplicate"    -> view.setDataError("Prenotazione già presente quel giorno");
            case "error:validation"   -> view.setNomeError("Dati non validi");
            default -> view.setNomeError("Errore di sistema, riprova");
            
        }
    }

    /* ----------------- carica attività ------------------ */
    private void loadActivities(){
        List<Activity> acts = service.getAvailableActivities();
        List<String>   names = acts.stream().map(Activity::getName).toList();
        view.loadActivities(names);
    }
}