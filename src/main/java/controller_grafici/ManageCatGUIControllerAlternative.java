package controller_grafici;

import bean.ManageCatBean;
import controller_applicativi.ManageCatController;
import entity.Cat;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import view.ManageCatAlternative;



public class ManageCatGUIControllerAlternative {

    

    private final ManageCatAlternative view = new ManageCatAlternative();
    private final ManageCatController service = new ManageCatController();

    private final ManageCatBean bean = new ManageCatBean();
    private final NavigationService navigationService;
    private final String typeOfLogin;

    private boolean deleteMode = false;

    public ManageCatGUIControllerAlternative(NavigationService navigationService, String typeOfLogin) {
        this.navigationService = navigationService;
        this.typeOfLogin = typeOfLogin;
        addEventHandlers();
        addIndexColumn();
        refreshTable();
    }

    private void addEventHandlers() {
        // selezione riga -> aggiorna bean
        view.getTable().getSelectionModel().selectedItemProperty().addListener(
                (_, _, sel) -> bean.setSelected(sel)
        );

        // CONFERMA / CREA
        view.getBtnConfirm().setOnAction(_ -> {
        	if (!deleteMode && bean.isSelected()) {
                service.newCat(bean);   // oppure update se già esiste
                refreshTable();
            }
        });

       

        // CREA NUOVO GATTO

        view.getBtnAddCat().setOnAction(_ -> {
            Cat nuovoGatto = new Cat();
            nuovoGatto.setNameCat("");
            nuovoGatto.setRace("");
            nuovoGatto.setDescription("");
            nuovoGatto.setAge(0);
            nuovoGatto.setStateAdoption(false);

            view.getTable().getItems().add(nuovoGatto);
            view.getTable().getSelectionModel().select(nuovoGatto);
            bean.setSelected(nuovoGatto);
        });



        // TORNA INDIETRO
        view.getBtnBack().setOnAction(_ -> navigationService.navigateToHomePage(navigationService, typeOfLogin));
    }

    private void refreshTable() {
        ObservableList<Cat> items = FXCollections.observableArrayList(service.loadAll());
        bean.setCatList(items);
        view.setItems(items);
    }
    private void addIndexColumn() {
        TableColumn<Cat, Number> indexCol = new TableColumn<>("ID");
        indexCol.setCellValueFactory(col ->
                new ReadOnlyObjectWrapper<>(view.getTable().getItems().indexOf(col.getValue()))
        );
        view.getTable().getColumns().addFirst(indexCol);
    }

    public VBox getRoot() {
        return new VBox(view.getRoot());
    }
}
