package controller_grafici;

import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import bean.ManageCatBean;
import controller_applicativi.ManageCatController;
import entity.Cat;
import view.ManageCatAlternative;

public class ManageCatGUIControllerAlternative {

    private final ManageCatAlternative view = new ManageCatAlternative();
    private final ManageCatController service = new ManageCatController();

    private final NavigationService nav;
    private final String typeOfLogin;

    public ManageCatGUIControllerAlternative(NavigationService nav, String typeOfLogin) {
        this.nav = nav;
        this.typeOfLogin = typeOfLogin;

        refresh();

        /* --- pulsante Conferma --- */
        view.getBtnConfirm().setOnAction(_ -> {
            Cat sel = view.getListView().getSelectionModel().getSelectedItem();
            if (sel == null) {
                view.showError("Seleziona un gatto prima di confermare.");
                return;
            }
            try {
                ManageCatBean bean = new ManageCatBean();
                bean.setSelected(sel);
                service.newCat(bean);
                refresh();
            } catch (Exception e) {
                view.showError("Errore nel confermare il gatto: " + e.getMessage());
            }
        });

        /* --- pulsante Cancella --- */
        view.getBtnCancel().setOnAction(_ -> {
            Cat sel = view.getListView().getSelectionModel().getSelectedItem();
            if (sel == null) {
                view.showError("Seleziona un gatto prima di cancellare.");
                return;
            }
            try {
                service.cancelCat(sel);
                refresh();
            } catch (Exception e) {
                view.showError("Errore nel cancellare il gatto: " + e.getMessage());
            }
        });

        view.getBtnBack().setOnAction(_ -> nav.navigateToHomePage(nav, typeOfLogin));
    }

    private void refresh() {
        view.hideError();
        try {
            view.setItems(FXCollections.observableArrayList(service.loadAll()));
        } catch (Exception e) {
            view.showError("Errore nel caricamento della lista gatti: " + e.getMessage());
        }
    }

    public VBox getRoot() {
        return view.getRoot();
    }
}
