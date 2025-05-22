package View;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** View per prenotare un tavolo “immerso nei gatti”. */
public class BookingView {

    /* ---------- nodi principali (nomi richiesti) ------------------ */
    protected VBox root;                             // contenitore radice

    private final DatePicker       Data;             // giorno
    private final ComboBox<LocalTime> Ora;           // ora (slot da 15')
    private final TextField  numeroPartecipanti;     // n. posti
    private final TextField  NomePrenotazione;       // titolo
    private final TextField  Email;                  // e-mail di conferma

    private final Button confirmButton;
    private final Button cancelButton;

    /* ---------- label di errore ----------------------------------- */
    private final Label DataErrorLabel;
    private final Label OraErrorLabel;
    private final Label numeroPartecipantiErrorLabel;
    private final Label NomePrenotazioneErrorLabel;
    private final Label emailErrorLabel;

    private static final String ERROR_MESSAGE = "error-message";   // stile CSS

    /* ===============================================================*/
    public BookingView() {

        /* ----- layout di base ----- */
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 32;");

        Label title = new Label("Prenota un tavolo immerso nei gatti 🐾");

        /* ----- Data (DatePicker) ----- */
        Label dataLabel = new Label("Giorno");
        Data = new DatePicker();
        Data.setPromptText("Seleziona la data");
        DataErrorLabel = makeErrLabel();

        /* ----- Ora (ComboBox) ----- */
        Label oraLabel = new Label("Ora di arrivo");
        Ora = new ComboBox<>();
        populateTimeBox();                      // slot 15-min 08:00-23:45
        Ora.setPromptText("Seleziona l'ora");
        OraErrorLabel = makeErrLabel();

        /* ----- Numero partecipanti ----- */
        Label partLabel = new Label("Numero partecipanti");
        numeroPartecipanti = new TextField();
        numeroPartecipanti.setPromptText("Es. 2");
        numeroPartecipanti.setMaxWidth(140);
        numeroPartecipantiErrorLabel = makeErrLabel();

        /* ----- Nome prenotazione ----- */
        Label nomeLabel = new Label("Nome prenotazione");
        NomePrenotazione = new TextField();
        NomePrenotazione.setPromptText("Es. 'Luca'");
        NomePrenotazione.setMaxWidth(200);
        NomePrenotazioneErrorLabel = makeErrLabel();

        /* ----- E-mail ----- */
        Label emailLabel = new Label("E-mail di conferma");
        Email = new TextField();
        Email.setPromptText("nome@esempio.com");
        Email.setMaxWidth(260);
        emailErrorLabel = makeErrLabel();

        /* ----- Bottoni ----- */
        confirmButton = new Button("Conferma");
        cancelButton  = new Button("Annulla");

        /* ----- composizione ----- */
        root.getChildren().addAll(
            title,
            dataLabel, Data, DataErrorLabel,
            oraLabel,  Ora,  OraErrorLabel,
            partLabel, numeroPartecipanti, numeroPartecipantiErrorLabel,
            nomeLabel, NomePrenotazione, NomePrenotazioneErrorLabel,
            emailLabel, Email, emailErrorLabel,
            confirmButton, cancelButton
        );
    }

    /* ===============================================================
                                 GETTER
       ===============================================================*/
    public VBox      getRoot()            {
    	return root; 
    	}
    public Button    getConfirmButton()   { 
    	return confirmButton; 
    	}
    public Button    getCancelButton()    {
    	return cancelButton; 
    	}

    public LocalDate getDate()            { 
    	return Data.getValue(); 
    	}
    public LocalTime getTime()            {
    	return Ora.getValue(); 
    	}

    public int getParticipants() {
        try { return Integer.parseInt(numeroPartecipanti.getText().trim()); }
        catch (NumberFormatException e) { 
        	return 0; 
        	}
    }
    public String getNomePrenotazione()  { 
    	return NomePrenotazione.getText().trim();
    	}
    public String getConfirmationEmail() {
    	return Email.getText().trim();
    	}

    /* ===============================================================
                           ERROR-HANDLING METHODS
       ===============================================================*/
    public void hideAllErrors() {
        DataErrorLabel.setVisible(false);
        OraErrorLabel.setVisible(false);
        numeroPartecipantiErrorLabel.setVisible(false);
        NomePrenotazioneErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
    }

    public void setDataError(String m)  { 
    	showErr(DataErrorLabel, m); 
    	}
    public void setOraError(String m)  { 
    	showErr(OraErrorLabel,  m);
    	}
    public void setPartecipantiError(String m)  {
    	showErr(numeroPartecipantiErrorLabel, m); 
    	}
    public void setNomeError(String m) {
    	showErr(NomePrenotazioneErrorLabel, m); 
    	}
    public void setEmailError(String m)  {
    	showErr(emailErrorLabel, m); 
    	}

    /* ===============================================================
                                HELPERS
       ===============================================================*/
    private Label makeErrLabel() {
        Label l = new Label();
        l.getStyleClass().add(ERROR_MESSAGE);
        l.setVisible(false);
        return l;
    }
    private void showErr(Label l, String msg) {
        l.setText(msg); l.setVisible(true);
    }

    /** Popola il ComboBox con slot di 15 minuti (08:00-23:45). */
    private void populateTimeBox() {
        Ora.setItems(FXCollections.observableArrayList(
            IntStream.rangeClosed(32, 95)               // 08:00 → 23:45
                     .mapToObj(i -> LocalTime.of(i/4, (i%4)*15))
                     .collect(Collectors.toList())
        ));
        Ora.setButtonCell(timeCell());
        Ora.setCellFactory(list -> timeCell());
    }
    private ListCell<LocalTime> timeCell() {
        return new ListCell<>() {
            @Override protected void updateItem(LocalTime t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t==null ? "" : t.toString());
            }
        };
    }
}