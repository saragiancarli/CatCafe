package view;

import entity.Booking;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** Variante “minimal” della schermata Gestione-prenotazioni.              
 *  – ListView compatta (una riga = una prenotazione)                        
 *  – pulsanti Conferma / Cancella globali sotto la lista                   */
public class ManageBookingAlternativeView {

    /* ---------- nodi principali esposti al controller ---------------- */
    private final VBox      root       = new VBox(16);
    private final ListView<Booking> listView  = new ListView<>();
    private final Button    btnConfirm = new Button("Conferma");
    private final Button    btnCancel  = new Button("Cancella");
    private final Label     errLabel   = new Label("Seleziona prima una prenotazione.");

    public ManageBookingAlternativeView() {

        /* ---------- list-view compatta ---------- */
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Booking b, boolean empty) {
                super.updateItem(b, empty);
                if (empty || b == null) { setText(null); }
                else {
                    setText("%d  |  %-12s | %s %s | %s".formatted(
                            b.getId(),
                            b.getTitle(),
                            b.getDate(),
                            b.getTime(),
                            b.getStatus()));
                }
            }
        });

        /* ---------- bottoni + label errore ---------- */
        HBox buttons = new HBox(10, btnConfirm, btnCancel);
        buttons.setAlignment(Pos.CENTER);

        errLabel.setStyle("-fx-text-fill:red;");
        errLabel.setVisible(false);

        /* ---------- layout ---------- */
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(new Label("Gestione prenotazioni"),
                                  listView,
                                  buttons,
                                  errLabel);
        root.setPrefSize(900, 600);
        listView.setPrefHeight(450);
    }

    /* ---------- API per il controller ---------- */
    public VBox      getRoot()  {
    	return root;
    	}
    public ListView<Booking> getList()  {
    	return listView;
    	}
    public Button    getBtnConfirm(){
    	return btnConfirm; 
    	}
    public Button    getBtnCancel() {
    	return btnCancel;
    	}
    public void      showError()  { 
    	errLabel.setVisible(true);
    	}
    public void      hideError()   {
    	errLabel.setVisible(false); 
    	}

    public void setItems(java.util.Collection<Booking> data) {
        listView.setItems(FXCollections.observableArrayList(data));
    }
}