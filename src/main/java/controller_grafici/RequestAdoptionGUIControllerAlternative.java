package controller_grafici;

import bean.RequestAdoptionBean;
import controller_applicativi.RequestAdoptionController;
import dao.CatDaoMemory;
import entity.Client;
import facade.ApplicationFacade;
import javafx.scene.layout.VBox;
import view.RequestAdoptionAlternative;

import java.util.logging.Logger;

public class RequestAdoptionGUIControllerAlternative {
    private final Logger lOG = Logger.getLogger(getClass().getName());
    private final NavigationService nav;
    private final RequestAdoptionAlternative view;
    private final RequestAdoptionController adoption = new RequestAdoptionController();

    private final String typeOfLogin;

    public RequestAdoptionGUIControllerAlternative(NavigationService navigation,String typeOfLogin) {
        this.nav = navigation;
        this.typeOfLogin=typeOfLogin;
        this.view = new RequestAdoptionAlternative();
        addEventHandlers();
        populateCats();
    }
    public VBox getRoot() { return view.getRoot(); }
    private void addEventHandlers() {
        view.getConferma().setOnAction(_ -> handleConfirm());
        view.getAnnulla().setOnAction(_ -> handleCancel());
    }
    private void handleConfirm() {

        RequestAdoptionBean bean = new RequestAdoptionBean();
        bean.setName(view.getName());
        bean.setSurname(view.getSurname());
        bean.setPhoneNumber(view.getPhoneNumber());
        bean.setEmail(view.getEmail());
        bean.setAddress(view.getAddress());
        bean.setNameCat(view.getSelectedCatName());
        view.hideAllErrors();
        boolean ok = true;

        if (!bean.hasValidName()) {
            view.setNomeError("Nome obbligatorio");
            ok = false;
        }
        if (!bean.hasValidSurname()) {
            view.setCognomeError("Cognome obbligatorio");
            ok = false;
        }
        if (!bean.hasValidPhoneNumber()) {
            view.setTelefonoError("Numero di telefono non valido");
            ok = false;
        }
        if (!bean.hasValidEmail()) {
            view.setEmailError("Email non valida");
            ok = false;
        }
        if (!bean.hasValidAddress()) {
            view.setIndirizzoError("Indirizzo obbligatorio");
            ok = false;
        }
        if (bean.getNameCat() == null || bean.getNameCat().isBlank()) {
            view.setnomeGattoError("Seleziona un gatto");
            ok = false;
        }

        if (!ok) return;

        Client currentUser = ApplicationFacade.getUserFromLogin();
        if (currentUser == null) {
            view.setNomeError("Utente non loggato - effettua il login");
            return;
        }
        String esito = adoption.requestAdoption(bean);

        switch (esito) {
            case "success" -> {
                lOG.info("Richiesta adozione inviata con successo");
                nav.navigateToHomePage(nav, typeOfLogin);
            }
            case "error:duplicate" ->
                    view.setnomeGattoError("Hai già una richiesta per questo gatto");
            case "error:validation" ->
                    view.setNomeError("Dati non validi - ricontrolla i campi");
            case "error:database_error" ->
                    view.setNomeError("Errore di sistema. Riprova più tardi");
            default ->
                    view.setNomeError("Errore sconosciuto");
        }
    }
    private void populateCats() {
        CatDaoMemory catDao = new CatDaoMemory(); // usa i dati "in memoria"
        var cats = catDao.readAdoptableCats();    // ottieni solo quelli adottabili
        view.setAvailableCats(cats);              // passerai questi dati alla ComboBox nella vista
    }

    private void handleCancel() {
        nav.navigateToHomePage(nav, typeOfLogin);
    }
}




