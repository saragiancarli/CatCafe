package controllerGrafici;


import javafx.collections.*;
import javafx.scene.layout.VBox;


import controllerApplicativi.ManageBookingService;
import entity.Booking;
import entity.BookingStatus;
import View.ManageBookingView;

public class ManageBookingController {

    

    private final ManageBookingView    view    = new ManageBookingView();
    private final ManageBookingService service = new ManageBookingService();

	private final NavigationService navigationService;

	private final String typeOfLogin;

    public ManageBookingController(NavigationService navigationService, String typeOfLogin) {
    	this.navigationService=navigationService;
    	this.typeOfLogin=typeOfLogin;
        addEventHandlers();
        refreshTable();                         // carica subito
    }

    private void addEventHandlers() {

        /* selezione riga -> abilita/disabilita bottoni */
        view.getTable().getSelectionModel().selectedItemProperty().addListener(
            (_, _, sel) -> updateButtonState(sel)
        );
        updateButtonState(null);                // stato iniziale

        /* CONFERMA */
        view.getBtnConfirm().setOnAction(_ -> {
            Booking sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) {
                service.confirm(sel.getId());
                sel.confirm();                  // refresh via property
                view.getTable().refresh();      // sicurezza
            }
        });

        /* CANCELLA */
        view.getBtnCancel().setOnAction(_ -> {
            Booking sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) {
                service.cancel(sel.getId());
                sel.cancel();
                view.getTable().refresh();
            }
        });
        view.getBtnBack().setOnAction(_ -> {
        	navigationService.navigateToHomePage(navigationService, typeOfLogin); 
        });
    }

    private void updateButtonState(Booking sel) {
        boolean enable = sel != null && sel.getStatus() == BookingStatus.PENDING;
        view.getBtnConfirm().setDisable(!enable);
        view.getBtnCancel ().setDisable(!enable);
    }

    private void refreshTable() {
        ObservableList<Booking> items =
            FXCollections.observableArrayList(service.loadAll());
        view.setItems(items);
    }

    /* per il NavigationManager */
    public VBox getRoot() { return new VBox(view.getRoot()); }
}