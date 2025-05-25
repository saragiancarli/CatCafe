package controllerGrafici;

import javafx.scene.layout.VBox;

import java.util.List;


import controller_applicativi.ManageBookingService;
import entity.Booking;
import entity.BookingStatus;
import view.ManageBookingAlternativeView;

public class ManageBookingAlternativeController {

    

    /* view + service */
    private final ManageBookingAlternativeView view =
            new ManageBookingAlternativeView();
    private final ManageBookingService service = new ManageBookingService();

	private final NavigationService nav;

	private String typeOfLogin;

    public ManageBookingAlternativeController(NavigationService nav,String typeOfLogin) {
    	this.nav=nav;
    	this.typeOfLogin=typeOfLogin;
    	

        refresh();                                         // carica iniziale
        /* --- pulsante Conferma --- */
        view.getBtnConfirm().setOnAction(_ -> {
            Booking sel = view.getList().getSelectionModel().getSelectedItem();
            if (sel == null)           { view.showError(); return; }
            if (sel.getStatus()!= BookingStatus.PENDING)  { return; }

            service.confirm(sel.getId());
            refresh();
        });

        /* --- pulsante Cancella --- */
        view.getBtnCancel().setOnAction(_ -> {
            Booking sel = view.getList().getSelectionModel().getSelectedItem();
            if (sel == null)           { view.showError(); return; }
            if (sel.getStatus()!= BookingStatus.PENDING)  { return; }

            service.cancel(sel.getId());
            refresh();
        });
    }

    /* ------------------------------------------------------------ */
    private void refresh() {
        view.hideError();
        List<Booking> data = service.loadAll();
        view.setItems(data);
    }

    /* per il NavigationManager */
    public VBox getRoot() { return view.getRoot(); }
}