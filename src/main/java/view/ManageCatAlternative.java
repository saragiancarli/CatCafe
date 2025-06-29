package view;

import entity.Cat;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ManageCatAlternative {

    private final VBox root = new VBox(16);
    private final ListView<Cat> listView = new ListView<>();
    private final Button btnInsertCat = new Button("Inserisci gatto");
    private final Button btnConfirm = new Button("Conferma");
    private final Button btnCancel = new Button("Cancella");
    private final Button btnBack = new Button("Indietro");
    private final Label errLabel = new Label();

    public ManageCatAlternative() {
        configureListView();
        setupLayout();
    }

    private void configureListView() {
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Cat cat, boolean empty) {
                super.updateItem(cat, empty);
                if (empty || cat == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(String.format("%-15s | %-15s | %d anni | %s",
                            cat.getNameCat(),
                            cat.getRace(),
                            cat.getAge(),
                            cat.isStateAdoption() ? "Adottato" : "Disponibile"));
                }
            }
        });
    }

    private void setupLayout() {
        // Bottoni azioni (sinistra)
        HBox leftButtons = new HBox(10, btnInsertCat, btnCancel);
        leftButtons.setAlignment(Pos.CENTER_LEFT);

        // Bottoni navigazione (destra)
        HBox rightButtons = new HBox(10, btnConfirm, btnBack);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        // Spaziatura centrale
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Contenitore principale bottoni
        HBox buttonContainer = new HBox(10, leftButtons, spacer, rightButtons);
        buttonContainer.setPadding(new Insets(8));

        // Configurazione errori
        errLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        errLabel.setVisible(false);

        // Layout root
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                new Label("Gestione Gatti - Visualizzazione Compatta"),
                listView,
                buttonContainer,
                errLabel
        );

        VBox.setVgrow(listView, Priority.ALWAYS);
        listView.setPrefHeight(450);
    }

    /* ---------- API Pubblica ---------- */
    public VBox getRoot() {
        return root;
    }

    public ListView<Cat> getListView() {
        return listView;
    }

    public Button getBtnInsertCat() {
        return btnInsertCat;
    }

    public Button getBtnConfirm() {
        return btnConfirm;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public void showError(String message) {
        errLabel.setText(message);
        errLabel.setVisible(true);
    }

    public void hideError() {
        errLabel.setVisible(false);
    }

    public void setItems(ObservableList<Cat> cats) {
        listView.setItems(cats);
    }
}