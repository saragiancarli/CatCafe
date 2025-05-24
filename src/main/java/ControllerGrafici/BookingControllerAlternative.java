package ControllerGrafici;

import javafx.scene.layout.VBox;

import java.util.logging.Logger;

import Bean.BookingBean;
import ControllerApplicativi.BookingService;
import DAO.DaoFactory;
import DAO.GenericDao;
import Entity.Booking;
import Entity.Client;
import Facade.ApplicationFacade;
import View.BookingViewAlternative;

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
        view.getConfirmButton().setOnAction(e -> handleConfirm());
        view.getCancelButton() .setOnAction(e ->
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
        GenericDao<Booking> dao = DaoFactory.getInstance().getBookingDao();
        
        switch (esito) {
            case "success" ->
            
                 navigation.navigateToHomePage(navigation, "user");

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