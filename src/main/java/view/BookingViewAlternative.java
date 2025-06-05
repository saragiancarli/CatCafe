package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/** View “alternativa” (compact + dark) con data inserita manualmente. */
public class BookingViewAlternative {

    /* ---------- nodi principali (nomi invariati) ------------------ */
    protected final VBox root;

    /* sostituito DatePicker → TextField */
    private final TextField dataField;

    private final Spinner<Integer> oraHour;
    private final Spinner<Integer> oraMin;

    private final TextField numeroPartecipanti;
    private final TextField nomePrenotazione;
    private final TextField email;

    private final Button confirmButton;
    private final Button cancelButton;

    /* ---------- label di errore ----------------------------------- */
    private final Label dataErrorLabel;
    private final Label oraErrorLabel;
    private final Label numeroPartecipantiErrorLabel;
    private final Label nomePrenotazioneErrorLabel;
    private final Label emailErrorLabel;

    private static final String ERR_STYLE = "error-message";

    /* ===============================================================*/
    public BookingViewAlternative() {

        root = new VBox(20);
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.TOP_CENTER);

        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER_LEFT);

        int row = 0;

        Label title = new Label("Prenotazione (Modalità compatta)");

        /* --- data (campo testo) --- */
        grid.add(new Label("Giorno (yyyy-MM-dd)"), 0, ++row);
        dataField = new TextField();
        dataField.setPromptText("2025-05-30");
        grid.add(dataField, 1, row);
        dataErrorLabel = makeErrLabel();
        grid.add(dataErrorLabel, 2, row);

        /* --- ora (spinner hh:mm) --- */
        grid.add(new Label("Ora di arrivo"), 0, ++row);
        oraHour = new Spinner<>(0, 23, 8);
        oraHour.setPrefWidth(70);
        oraMin  = new Spinner<>(0, 45, 0, 15);
        oraMin.setPrefWidth(70);
        grid.add(new HBox(5, oraHour, new Label(":"), oraMin), 1, row);
        oraErrorLabel = makeErrLabel();
        grid.add(oraErrorLabel, 2, row);

        /* --- n. partecipanti --- */
        grid.add(new Label("Partecipanti"), 0, ++row);
        numeroPartecipanti = new TextField();
        numeroPartecipanti.setPrefWidth(80);
        grid.add(numeroPartecipanti, 1, row);
        numeroPartecipantiErrorLabel = makeErrLabel();
        grid.add(numeroPartecipantiErrorLabel, 2, row);

        /* --- nome prenotazione --- */
        grid.add(new Label("Nome prenotazione"), 0, ++row);
        nomePrenotazione = new TextField();
        grid.add(nomePrenotazione, 1, row);
        nomePrenotazioneErrorLabel = makeErrLabel();
        grid.add(nomePrenotazioneErrorLabel, 2, row);

        /* --- email --- */
        grid.add(new Label("E-mail conferma"), 0, ++row);
        email = new TextField();
        grid.add(email, 1, row);
        emailErrorLabel = makeErrLabel();
        grid.add(emailErrorLabel, 2, row);

        /* --- bottoni --- */
        confirmButton = new Button("Conferma");
        cancelButton  = new Button("Annulla");

        root.getChildren().addAll(title, grid,
                                  new HBox(10, confirmButton, cancelButton));
    }

    /* ===============================================================
                            GETTER IDENTICI
       ===============================================================*/
    public VBox   getRoot()          { return root; }
    public Button getConfirmButton() { return confirmButton; }
    public Button getCancelButton()  { return cancelButton; }

    /** Converte il testo in LocalDate; ritorna null se il parsing fallisce. */
    public LocalDate getDate() {
        String txt = dataField.getText().trim();
        if (txt.isEmpty()) return null;
        try { return LocalDate.parse(txt); }
        catch (DateTimeParseException _) { return null; }
    }

    public LocalTime getTime() {
        return LocalTime.of(oraHour.getValue(), oraMin.getValue());
    }

    public int getParticipants() {
        try { return Integer.parseInt(numeroPartecipanti.getText().trim()); }
        catch (NumberFormatException _) { return 0; }
    }

    public String getNomePrenotazione()  { return nomePrenotazione.getText().trim(); }
    public String getConfirmationEmail() { return email.getText().trim(); }

    /* ===============================================================
                           ERROR-HANDLING
       ===============================================================*/
    public void hideAllErrors() {
        dataErrorLabel.setVisible(false);
        oraErrorLabel.setVisible(false);
        numeroPartecipantiErrorLabel.setVisible(false);
        nomePrenotazioneErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
    }

    public void setDataError(String m)          { showErr(dataErrorLabel, m); }
    public void setOraError(String m)           { showErr(oraErrorLabel, m); }
    public void setPartecipantiError(String m)  { showErr(numeroPartecipantiErrorLabel, m); }
    public void setNomeError(String m)          { showErr(nomePrenotazioneErrorLabel, m); }
    public void setEmailError(String m)         { showErr(emailErrorLabel, m); }

    /* ===============================================================*/
    private Label makeErrLabel() {
        Label l = new Label();
        l.getStyleClass().add(ERR_STYLE);
        l.setVisible(false);
        return l;
    }
    private void showErr(Label l, String msg) {
        l.setText(msg);
        l.setVisible(true);
    }
}