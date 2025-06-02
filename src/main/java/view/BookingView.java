package view;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.stream.IntStream;

/** View per prenotare un tavolo “immerso nei gatti”. */
public class BookingView {

    /* ---------- nodi principali (nomi richiesti) ------------------ */
    protected VBox root;                             // contenitore radice

    private final DatePicker       data;             // giorno
    private final ComboBox<LocalTime> ora;           // ora (slot da 15')
    private final TextField  numeroPartecipanti;     // numero posti
    private final TextField  nomePrenotazione;       // titolo
    private final TextField  email;                  // e-mail di conferma

    private final Button confirmButton;
    private final Button cancelButton;

    /* ---------- label di errore ----------------------------------- */
    private final Label dataErrorLabel;
    private final Label oraErrorLabel;
    private final Label numeroPartecipantiErrorLabel;
    private final Label nomePrenotazioneErrorLabel;
    private final Label emailErrorLabel;

    private static final String ERROR_MESSAGE = "error-message";   // stile CSS

    /* ===============================================================*/
    public BookingView() {

        /* ----- layout di base ----- */
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 32;");

        Label title = new Label("Prenota un tavolo immerso tra i gatti 🐾");

        /* ----- Data (DatePicker) ----- */
        Label dataLabel = new Label("Giorno");
        data = new DatePicker();
        data.setPromptText("Seleziona la data");
        dataErrorLabel = makeErrLabel();

        /* ----- Ora (ComboBox) ----- */
        Label oraLabel = new Label("Ora di arrivo");
        ora = new ComboBox<>();
        populateTimeBox();                      // slot 15-min 08:00-23:45
        ora.setPromptText("Seleziona l'ora");
        oraErrorLabel = makeErrLabel();

        /* ----- Numero partecipanti ----- */
        Label partLabel = new Label("Numero partecipanti");
        numeroPartecipanti = new TextField();
        numeroPartecipanti.setPromptText("Es. 2");
        numeroPartecipanti.setMaxWidth(140);
        numeroPartecipantiErrorLabel = makeErrLabel();

        /* ----- Nome prenotazione ----- */
        Label nomeLabel = new Label("Nome prenotazione");
        nomePrenotazione = new TextField();
        nomePrenotazione.setPromptText("Es. 'Luca'");
        nomePrenotazione.setMaxWidth(200);
        nomePrenotazioneErrorLabel = makeErrLabel();

        /* ----- E-mail ----- */
        Label emailLabel = new Label("E-mail di conferma");
        email = new TextField();
        email.setPromptText("nome@esempio.com");
        email.setMaxWidth(260);
        emailErrorLabel = makeErrLabel();

        /* ----- Bottoni ----- */
        confirmButton = new Button("Conferma");
        cancelButton  = new Button("Annulla");

        /* ----- composizione ----- */
        root.getChildren().addAll(
            title,
            dataLabel, data, dataErrorLabel,
            oraLabel,  ora,  oraErrorLabel,
            partLabel, numeroPartecipanti, numeroPartecipantiErrorLabel,
            nomeLabel, nomePrenotazione, nomePrenotazioneErrorLabel,
            emailLabel, email, emailErrorLabel,
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
    	return data.getValue(); 
    	}
    public LocalTime getTime()            {
    	return ora.getValue(); 
    	}

    public int getParticipants() {
        try { return Integer.parseInt(numeroPartecipanti.getText().trim()); }
        catch (NumberFormatException _) { 
        	return 0; 
        	}
    }
    public String getNomePrenotazione()  { 
    	return nomePrenotazione.getText().trim();
    	}
    public String getConfirmationEmail() {
    	return email.getText().trim();
    	}

    /* ===============================================================
                           ERROR-HANDLING METHODS
       ===============================================================*/
    public void hideAllErrors() {
    	dataErrorLabel.setVisible(false);
    	oraErrorLabel.setVisible(false);
        numeroPartecipantiErrorLabel.setVisible(false);
        nomePrenotazioneErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
    }

    public void setDataError(String m)  { 
    	showErr(dataErrorLabel, m); 
    	}
    public void setOraError(String m)  { 
    	showErr(oraErrorLabel,  m);
    	}
    public void setPartecipantiError(String m)  {
    	showErr(numeroPartecipantiErrorLabel, m); 
    	}
    public void setNomeError(String m) {
    	showErr(nomePrenotazioneErrorLabel, m); 
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
        // 08:00 → 23:45 (slot di 15')
        var times = IntStream.rangeClosed(32, 95)          // 32 * 15′ = 08:00
                             .mapToObj(i -> LocalTime.of(i / 4, (i % 4) * 15))
                             .toList();                    // ← niente Collectors

        ora.setItems(FXCollections.observableArrayList(times));
        ora.setButtonCell(timeCell());
        ora.setCellFactory(_ -> timeCell());
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