package View;


import java.time.LocalDate;
import java.time.LocalTime;

import entity.Booking;
import entity.BookingStatus;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ManageBookingView {

    private final TableView<Booking> table = new TableView<>();
    private final Button btnConfirm = new Button("Conferma");
    private final Button btnCancel  = new Button("Cancella");
    private final Button btnBack  = new Button("Indietro");

    private final VBox root = new VBox(12);          

    public ManageBookingView() {
        buildTable();

        HBox buttons = new HBox(10, btnConfirm, btnCancel,btnBack);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(8));

        root.setPadding(new Insets(16));
        root.getChildren().addAll(table, buttons);
        VBox.setVgrow(table, Priority.ALWAYS);       // la table si espande
    }

    /* ---------- costruzione colonne ---------- */
    private void buildTable() {
        TableColumn<Booking,Integer> idCol   = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Booking,String>  titleCol = new TableColumn<>("Titolo");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Booking,LocalDate> dateCol = new TableColumn<>("Data");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Booking,LocalTime> timeCol = new TableColumn<>("Ora");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Booking,Integer> seatsCol = new TableColumn<>("Posti");
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));

        TableColumn<Booking,String> mailCol = new TableColumn<>("E-mail");
        mailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Booking,BookingStatus> statCol = new TableColumn<>("Stato");
        statCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol,titleCol,dateCol,timeCol,
                                  seatsCol,mailCol,statCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    
    public void setItems(ObservableList<Booking> data) { 
    	table.setItems(data);
    	}
    public TableView<Booking> getTable() { 
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