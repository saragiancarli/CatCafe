package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RequestAdoption {

    /* ---------- campi ------------------ */

    protected VBox root;
    private final ComboBox<String> nomeGatto;
    private final TextField nome;
    private final TextField cognome;
    private final TextField emailConferma;
    private final TextField indirizzo;
    private final TextField telefono;

    private final Button modifica;
    private final Button conferma;
    private final Button annulla;

    private final Label nomeGattoErrorLabel;
    private final Label nomeErrorLabel;
    private final Label cognomeErrorLabel;
    private final Label emailConfermaErrorLabel;
    private final Label indirizzoErrorLabel;
    private final Label telefonoErrorLabel;
    private static final String ERROR_MESSAGE = "error";

    /* ---------- costruttore ------------------ */

    public RequestAdoption() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 32;");

        Label title = new Label(" 🐾 Zampette in cerca di casa 🐾 ");

        // Sezione nome gatto
        Label nomeGattoLabel = new Label(" Scegli gatto da adottare ");
        nomeGatto = new ComboBox<>();
        nomeGatto.setPromptText(" Seleziona un gatto ");
        nomeGattoErrorLabel = makeErrLabel();

        // Nome
        Label nomeLabel = new Label(" Nome ");
        nome = new TextField();
        nome.setPromptText(" ");
        nome.setMaxWidth(150);
        nomeErrorLabel = makeErrLabel();

        // Cognome
        Label cognomeLabel = new Label(" Cognome ");
        cognome = new TextField();
        cognome.setPromptText(" ");
        cognome.setMaxWidth(150);
        cognomeErrorLabel = makeErrLabel();

        // Email
        Label emailConfermaLabel = new Label(" Email di conferma ");
        emailConferma = new TextField();
        emailConferma.setPromptText(" ");
        emailConferma.setMaxWidth(150);
        emailConfermaErrorLabel = makeErrLabel();

        // Indirizzo
        Label indirizzoLabel = new Label(" Indirizzo ");
        indirizzo = new TextField();
        indirizzo.setPromptText(" ");
        indirizzo.setMaxWidth(150);
        indirizzoErrorLabel = makeErrLabel();

        // Telefono
        Label telefonoLabel = new Label(" Numero di telefono ");
        telefono = new TextField();
        telefono.setPromptText(" ");
        telefono.setMaxWidth(150);
        telefonoErrorLabel = makeErrLabel();

        // Bottoni
        modifica = new Button("Modifica richieste adozione");
        conferma = new Button("Conferma");
        annulla = new Button("Annulla");

        // Layout bottone "Modifica" in basso a sinistra
        HBox modificaBox = new HBox(modifica);
        modificaBox.setAlignment(Pos.CENTER_LEFT);
        modificaBox.setPadding(new Insets(20, 0, 0, 0));

        // Layout bottoni "Conferma" e "Annulla" centrati
        HBox azioniBox = new HBox(15, conferma, annulla);
        azioniBox.setAlignment(Pos.CENTER);
        azioniBox.setPadding(new Insets(10, 0, 0, 0));

        // Aggiunta componenti al root
        root.getChildren().addAll(
                title,
                nomeGattoLabel, nomeGatto, nomeGattoErrorLabel,
                nomeLabel, nome, nomeErrorLabel,
                cognomeLabel, cognome, cognomeErrorLabel,
                emailConfermaLabel, emailConferma, emailConfermaErrorLabel,
                indirizzoLabel, indirizzo, indirizzoErrorLabel,
                telefonoLabel, telefono, telefonoErrorLabel,
                modificaBox,
                azioniBox
        );
    }

    /* ---------- getter ------------------ */

    public VBox getRoot() {
        return root;
    }

    public Button getModifica() {
        return modifica;
    }

    public Button getConferma() {
        return conferma;
    }

    public Button getAnnulla() {
        return annulla;
    }

    public String getSelectedCatName() {
        return nomeGatto.getValue();
    }

    public ComboBox<String> getComboBoxCatName() {
        return nomeGatto;
    }

    public String getName() {
        return nome.getText().trim();
    }

    public String getSurname() {
        return cognome.getText().trim();
    }

    public String getEmail() {
        return emailConferma.getText().trim();
    }

    public String getAddress() {
        return indirizzo.getText().trim();
    }

    public String getPhoneNumber() {
        return telefono.getText().trim();
    }

    /* ---------- gestione errori ------------------ */

    public void hideAllErrors() {
        nomeGattoErrorLabel.setVisible(false);
        nomeErrorLabel.setVisible(false);
        cognomeErrorLabel.setVisible(false);
        emailConfermaErrorLabel.setVisible(false);
        indirizzoErrorLabel.setVisible(false);
        telefonoErrorLabel.setVisible(false);
    }

    public void setNomeGattoError(String m) {
        showError(nomeGattoErrorLabel, m);
    }

    public void setNomeError(String m) {
        showError(nomeErrorLabel, m);
    }

    public void setCognomeError(String m) {
        showError(cognomeErrorLabel, m);
    }

    public void setEmailError(String m) {
        showError(emailConfermaErrorLabel, m);
    }

    public void setIndirizzoError(String m) {
        showError(indirizzoErrorLabel, m);
    }

    public void setTelefonoError(String m) {
        showError(telefonoErrorLabel, m);
    }

    /* ---------- metodi utili ------------------ */

    private Label makeErrLabel() {
        Label l = new Label();
        l.getStyleClass().add(ERROR_MESSAGE);
        l.setVisible(false);
        return l;
    }

    private void showError(Label l, String msg) {
        l.setText(msg);
        l.setVisible(msg != null && !msg.isEmpty());
    }
}
