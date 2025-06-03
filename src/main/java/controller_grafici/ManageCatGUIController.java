package controller_grafici;

import bean.ManageCatBean;
import controller_applicativi.ManageCatController;
import entity.Cat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import view.ManageCat;

public class ManageCatGUIController {

    private final ManageCat view = new ManageCat();
    private final ManageCatController service = new ManageCatController();

    private final ManageCatBean bean = new ManageCatBean();
    private final NavigationService navigationService;
    private final String typeOfLogin;

    public ManageCatGUIController(NavigationService navigationService, String typeOfLogin) {
        this.navigationService = navigationService;
        this.typeOfLogin = typeOfLogin;
        addEventHandlers();
        refreshTable();
    }

    private void addEventHandlers() {

        // selezione riga -> aggiorna bean
        view.getTable().getSelectionModel().selectedItemProperty().addListener(
                (_, _, sel) -> bean.setSelected(sel)
        );

        // CONFERMA / CREA
        view.getBtnConfirm().setOnAction(_ -> {
            if (bean.isSelected()) {
                service.newCat(bean);
                refreshTable();
            }
        });

        // CANCELLA
        view.getBtnCancel().setOnAction(_ -> {
            if (bean.isSelected()) {
                service.cancelCat(bean);
                refreshTable();
            }
        });

        // TORNA INDIETRO
        view.getBtnBack().setOnAction(_ -> navigationService.navigateToHomePage(navigationService, typeOfLogin));
    }

    private void refreshTable() {
        ObservableList<Cat> items = FXCollections.observableArrayList(service.loadAll());
        bean.setCatList(items);
        view.setItems(items);
    }

    public VBox getRoot() {
        return new VBox(view.getRoot());
    }
}
