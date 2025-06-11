package view;

import entity.Cat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;


public class ManageCatAlternative {
    private final TableView<Cat> table = new TableView<>();
    private final ObservableList<Cat> data = FXCollections.observableArrayList();

    private final Button btnAddCat = new Button("Inserisci gatto");
    private final Button btnConfirm = new Button("Conferma modifiche");
    private final Button btnBack = new Button("Indietro");

    private final VBox root = new VBox(12);

    public ManageCatAlternative() {
        buildTable();
        table.setItems(data);

        btnAddCat.setOnAction(event -> {
            Cat newCat = new Cat();
            newCat.setNameCat("");
            newCat.setRace("");
            newCat.setDescription("");
            newCat.setAge(0);
            newCat.setStateAdoption(false);

            data.add(newCat);
            table.getSelectionModel().select(newCat);
            table.scrollTo(newCat);
        });

        HBox leftButtons = new HBox(10, btnAddCat);
        leftButtons.setAlignment(Pos.CENTER_RIGHT);

        HBox rightButtons = new HBox(10, btnConfirm, btnBack);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttons = new HBox(10, leftButtons, spacer, rightButtons);
        buttons.setPadding(new Insets(8));

        leftButtons.setMaxWidth(Double.MAX_VALUE);
        rightButtons.setMaxWidth(Double.MAX_VALUE);


        root.setPadding(new Insets(16));
        root.getChildren().addAll(table, buttons);
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    @SuppressWarnings("unchecked")
    private void buildTable() {
        table.setEditable(true);


        TableColumn<Cat, String> nameCatCol = new TableColumn<>("Nome Gatto");
        nameCatCol.setCellValueFactory(new PropertyValueFactory<>("nameCat"));
        nameCatCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCatCol.setOnEditCommit(e -> e.getRowValue().setNameCat(e.getNewValue()));

        TableColumn<Cat, String> raceCol = new TableColumn<>("Razza");
        raceCol.setCellValueFactory(new PropertyValueFactory<>("race"));
        raceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        raceCol.setOnEditCommit(e -> e.getRowValue().setRace(e.getNewValue()));

        TableColumn<Cat, String> descriptionCol = new TableColumn<>("Descrizione");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(e -> e.getRowValue().setDescription(e.getNewValue()));

        TableColumn<Cat, Integer> ageCol = new TableColumn<>("Età");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ageCol.setOnEditCommit(e -> e.getRowValue().setAge(e.getNewValue()));

        TableColumn<Cat, Boolean> statusCol = new TableColumn<>("Stato di adozione");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("stateAdoption"));
        statusCol.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        statusCol.setOnEditCommit(e -> e.getRowValue().setStateAdoption(e.getNewValue()));

        table.getColumns().addAll(nameCatCol, raceCol, descriptionCol, ageCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public void setItems(ObservableList<Cat> cats) {
        data.setAll(cats);
    }

    public TableView<Cat> getTable() {
        return table;
    }

    public Button getBtnAddCat() {
        return btnAddCat;
    }

    public Button getBtnConfirm() {
        return btnConfirm;
    }

    

    public Button getBtnBack() {
        return btnBack;
    }

    public VBox getRoot() {
        return root;
    }
}
