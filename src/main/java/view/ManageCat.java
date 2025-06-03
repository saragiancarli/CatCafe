package view;
import entity.Cat;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class ManageCat {
    private final TableView<Cat> table = new TableView<>();
    private final Button btnConfirm = new Button("Conferma");
    private final Button btnCancel  = new Button("Cancella");
    private final Button btnBack  = new Button("Indietro");

    private final VBox root = new VBox(12);
    public ManageCat() {
        buildTable();

        HBox buttons = new HBox(10, btnConfirm, btnCancel,btnBack);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(8));

        root.setPadding(new Insets(16));
        root.getChildren().addAll(table, buttons);
        VBox.setVgrow(table, Priority.ALWAYS);
    }
    /* ---------- costruzione colonne ---------- */
    private void buildTable() {
        TableColumn<Cat,Integer> idCol   = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Cat,String>  nameCatCol = new TableColumn<>("Nome Gatto");
        nameCatCol.setCellValueFactory(new PropertyValueFactory<>("name cat"));

        TableColumn<Cat, String> raceCol = new TableColumn<>("Razza");
        raceCol.setCellValueFactory(new PropertyValueFactory<>("race"));

        TableColumn<Cat, String> descriptionCol = new TableColumn<>("Descrizione");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Cat,Integer> ageCol = new TableColumn<>("Età");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Cat,Boolean> statusCol = new TableColumn<>("Stato di adozione");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("state adoption"));

        table.getColumns().addAll(idCol,nameCatCol,raceCol,descriptionCol,
                ageCol,statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }
    public void setItems(ObservableList<Cat> data) {
        table.setItems(data);
    }
    public TableView<Cat> getTable() {
        return table;
    }
    public Button getBtnConfirm()        {
        return btnConfirm;
    }
    public Button getBtnCancel()         {
        return btnCancel;
    }
    public Button getBtnBack()         {
        return btnBack;
    }
    public VBox   getRoot()              {
        return root;
    }








}
