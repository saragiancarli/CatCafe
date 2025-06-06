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
    private final ManageCatBean bean = new ManageCatBean();
    private final NavigationService nav;
    private final String typeOfLogin;

    public ManageCatGUIControllerAlternative(NavigationService nav, String typeOfLogin) {
        this.nav = nav;
        this.typeOfLogin = typeOfLogin;

        // Configurazione iniziale
        view.getBtnConfirm().setOnAction(e -> handleConfirm());
        view.getBtnCancel().setOnAction(e -> handleCancel());
        view.getBtnBack().setOnAction(e -> nav.navigateToHomePage(nav, typeOfLogin));

        refreshData();
    }

    private void handleConfirm() {
        Cat selected = view.getListView().getSelectionModel().getSelectedItem();
        if (selected != null) {
            bean.setSelected(selected); // Imposta il gatto selezionato nel bean
            service.newCat(bean);      // Chiama il metodo del controller con il bean
            refreshData();
        }
    }

    private void handleCancel() {
        Cat selected = view.getListView().getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.cancelCat(selected); // Questo accetta Cat direttamente
            refreshData();
        }
    }

    private void refreshData() {
        view.setItems(FXCollections.observableArrayList(service.loadAll()));
    }

    public VBox getRoot() {
        return view.getRoot();
    }
}