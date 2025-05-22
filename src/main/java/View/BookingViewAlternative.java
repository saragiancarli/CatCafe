package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;

/** View “alternativa” (compact + dark) per la schermata di prenotazione. */
public class BookingViewAlternative {

    /* ---------- nodi principali (NOMI INVARIATI) ------------------ */
    protected VBox root;                       // contenitore radice

    private final DatePicker       Data;       // giorno
    private final Spinner<Integer> OraHour;    // ora  0-23
    private final Spinner<Integer> OraMin;     // min  0-45 step 15

    private final TextField  numeroPartecipanti;
    private final TextField  NomePrenotazione;
    private final TextField  Email;

    private final Button confirmButton;
    private final Button cancelButton;

    /* ---------- label di errore ----------------------------------- */
    private final Label DataErrorLabel;
    private final Label OraErrorLabel;
    private final Label numeroPartecipantiErrorLabel;
    private final Label NomePrenotazioneErrorLabel;
    private final Label emailErrorLabel;

    private static final String ERR_STYLE = "error-message";  // css class

    /* ===============================================================*/
    public BookingViewAlternative() {

        root = new VBox(20);
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.TOP_CENTER);

        /* grid compatta a due colonne */
        GridPane grid = new GridPane();
        grid.setVgap(8); grid.setHgap(10);
        grid.setAlignment(Pos.CENTER_LEFT);

        int row = 0;

        Label title = new Label("Prenotazione (Modalità compatta)");

        /* --- data --- */
        grid.add(new Label("Giorno"), 0, ++row);
        Data = new DatePicker();
        grid.add(Data, 1, row);
        DataErrorLabel = makeErrLabel();
        grid.add(DataErrorLabel, 2, row);

        /* --- ora (spinner) --- */
        grid.add(new Label("Ora di arrivo"), 0, ++row);
        OraHour = new Spinner<>(0, 23, 8);
        OraHour.setPrefWidth(70);
        OraMin  = new Spinner<>(0, 45, 0, 15);
        OraMin.setPrefWidth(70);
        grid.add(new HBox(5, OraHour, new Label(":"), OraMin), 1, row);
        OraErrorLabel = makeErrLabel();
        grid.add(OraErrorLabel, 2, row);

        /* --- n. partecipanti --- */
        grid.add(new Label("Partecipanti"), 0, ++row);
        numeroPartecipanti = new TextField();
        numeroPartecipanti.setPrefWidth(80);
        grid.add(numeroPartecipanti, 1, row);
        numeroPartecipantiErrorLabel = makeErrLabel();
        grid.add(numeroPartecipantiErrorLabel, 2, row);

        /* --- nome prenotazione --- */
        grid.add(new Label("Nome prenotazione"), 0, ++row);
        NomePrenotazione = new TextField();
        grid.add(NomePrenotazione, 1, row);
        NomePrenotazioneErrorLabel = makeErrLabel();
        grid.add(NomePrenotazioneErrorLabel, 2, row);

        /* --- email --- */
        grid.add(new Label("E-mail conferma"), 0, ++row);
        Email = new TextField();
        grid.add(Email, 1, row);
        emailErrorLabel = makeErrLabel();
        grid.add(emailErrorLabel, 2, row);

        /* --- bottoni --- */
        confirmButton = new Button("Conferma");
        cancelButton  = new Button("Annulla");

        root.getChildren().addAll(title, grid,
                                  new HBox(10, confirmButton, cancelButton));

        
    }

    /* ===============================================================
                            GETTER (identici)
       ===============================================================*/
    public VBox      getRoot()            { return root; }
    public Button    getConfirmButton()   { return confirmButton; }
    public Button    getCancelButton()    { return cancelButton; }

    public LocalDate getDate()            { return Data.getValue(); }

    public LocalTime getTime() {                // calcola dal doppio spinner
        return LocalTime.of(OraHour.getValue(), OraMin.getValue());
    }

    public int getParticipants() {
        try { return Integer.parseInt(numeroPartecipanti.getText().trim()); }
        catch (NumberFormatException e) { return 0; }
    }
    public String getNomePrenotazione()  { return NomePrenotazione.getText().trim(); }
    public String getConfirmationEmail() { return Email.getText().trim(); }

    /* ===============================================================
                       ERROR-HANDLING (uguali alla view base)
       ===============================================================*/
    public void hideAllErrors() {
        DataErrorLabel.setVisible(false);
        OraErrorLabel.setVisible(false);
        numeroPartecipantiErrorLabel.setVisible(false);
        NomePrenotazioneErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
    }

    public void setDataError(String m){
    	showErr(DataErrorLabel, m);
    	}
    public void setOraError(String m)    {
    	showErr(OraErrorLabel,  m); 
    	}
    public void setPartecipantiError(String m) {
    	showErr(numeroPartecipantiErrorLabel, m); 
    	}
    public void setNomeError(String m) { 
    	showErr(NomePrenotazioneErrorLabel, m); 
    	}
    public void setEmailError(String m)   {
    	showErr(emailErrorLabel, m); 
    	}

    /* ===============================================================*/
    private Label makeErrLabel() {
        Label l = new Label(); l.getStyleClass().add(ERR_STYLE); l.setVisible(false);
        return l;
    }
    private void showErr(Label l, String msg) { l.setText(msg); l.setVisible(true); }
}