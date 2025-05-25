package controller_grafici;

import javafx.scene.layout.VBox;

import java.util.logging.Logger;

import bean.BookingBean;
import controller_applicativi.BookingService;
import entity.Client;
import facade.ApplicationFacade;
import view.BookingViewAlternative;

/** Controller grafico che gestisce la BookingRoomViewAlternative. */
public class BookingControllerAlternative {

    /* --------------------- campi --------------------------------- */
    private final Logger lOG = Logger.getLogger(getClass().getName());

    private final BookingViewAlternative view;
    private final NavigationService navigation;
    private final BookingService    service   = new BookingService();

	private String typeOfLogin;

    /* ============================================================= */
    public BookingControllerAlternative(NavigationService navigation,String typeOfLogin) {
        this.navigation = navigation;
        this.typeOfLogin=typeOfLogin;
        this.view = new BookingViewAlternative();
        addEventHandlers();
    }

    public VBox getRoot() { return view.getRoot(); }

    /* --------------------- HANDLERS ------------------------------ */
    private void addEventHandlers() {
        view.getConfirmButton().setOnAction(_ -> handleConfirm());
        view.getCancelButton() .setOnAction(_ ->
                navigation.navigateToHomePage(navigation, "user"));
    }

    private void handleConfirm() {

        /* 1. Costruisci il bean */
        BookingBean bean = new BookingBean();
        bean.setTitle            (view.getNomePrenotazione());
        bean.setDate             (view.getDate());
        bean.setTime             (view.getTime());
        bean.setSeats            (view.getParticipants());
        bean.setConfirmationEmail(view.getConfirmationEmail());

        /* 2. Validazione GUI */
        view.hideAllErrors();
        boolean ok = true;

        if (!bean.hasValidTitle()) {
            view.setNomeError("Inserisci un titolo.");
            ok = false;
        }
        if (!bean.hasValidDates()) {
            view.setDataError("Data/ora non valide.");
            ok = false;
        }
        if (!bean.hasValidSeats()) {
            view.setPartecipantiError("Numero posti non valido.");
            ok = false;
        }
        
        if (!ok) return;                       // stop se errori GUI

        /* 3. Chiama servizio di business */
        Client currentUser =
                ApplicationFacade.getUserFromLogin();       // utente loggato

        String esito = service.book(currentUser, bean);
        
        
        switch (esito) {
            case "success" ->
            
                 navigation.navigateToHomePage(navigation, typeOfLogin);

            case "error:duplicate" ->
                 view.setDataError("Hai già una prenotazione per quel giorno.");

            case "error:validation" ->
                 view.setNomeError("Dati non validi (ricontrolla i campi).");

            case "error:database_error" ->
                 ApplicationFacade.showErrorMessage(
                        "Errore DB", "Impossibile salvare la prenotazione",
                        "Riprova più tardi.");

            default -> lOG.warning("Esito inatteso");
        }
    }
}