package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RequestAdoption {

    /* ---------- campi ------------------ */

    protected VBox root;
    private final ComboBox<String> nomeGatto;
    private final TextField nome;
    private final TextField cognome;
    private final TextField emailConferma;
    private final TextField indirizzo;
    private final TextField telefono;

    private final Button conferma;
    private final Button annulla;

    /* ---------- gestione-errori ------------------ */

    private final Label nomeGattoErrorLabel;
    private final Label nomeErrorLabel;
    private final Label cognomeErrorLabel;
    private final Label emailConfermaErrorLabel;
    private final Label indirizzoErrorLabel;
    private final Label telefonoErrorLabel;
    private static final String ERROR_MESSAGE = "error";

    /* ---------- costruttore-classe ------------------ */

    public RequestAdoption() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 32;");

        Label title = new Label(" 🐾 Zampette in cerca di casa 🐾 ");

        /* ---------- nome-gatto ------------------ */

        Label nomeGattoLabel = new Label(" Scegli gatto da adottare ");
        nomeGatto = new ComboBox<>();
        nomeGatto.setPromptText(" Seleziona un gatto ");
        nomeGattoErrorLabel = makeErrLabel();

        /* ---------- nome------------------ */

        Label nomeLabel = new Label(" Nome ");
        nome = new TextField();
        nome.setPromptText(" ");
        nome.setMaxWidth(150);
        nomeErrorLabel = makeErrLabel();

        /* ---------- cognome------------------ */

        Label cognomeLabel = new Label(" Cognome ");
        cognome = new TextField();
        cognome.setPromptText(" ");
        cognome.setMaxWidth(150);
        cognomeErrorLabel = makeErrLabel();

        /* ---------- email------------------ */

        Label emailConfermaLabel = new Label(" Email di conferma ");
        emailConferma = new TextField();
        emailConferma.setPromptText(" ");
        emailConferma.setMaxWidth(150);
        emailConfermaErrorLabel = makeErrLabel();

        /* ---------- indirizzo------------------ */

        Label indirizzoLabel = new Label(" Indirizzo ");
        indirizzo = new TextField();
        indirizzo.setPromptText(" ");
        indirizzo.setMaxWidth(150);
        indirizzoErrorLabel = makeErrLabel();

        /* ---------- numero------------------ */
        Label telefonoLabel = new Label(" Numero di telefono ");
        telefono = new TextField();
        telefono.setPromptText(" ");
        telefono.setMaxWidth(150);
        telefonoErrorLabel = makeErrLabel();

        conferma = new Button("Conferma");
        annulla = new Button("Annulla");

        /* ---------- aggiungo-componenti-alla-vbox------------------ */
        root.getChildren().addAll(
                title,
                nomeGattoLabel, nomeGatto, nomeGattoErrorLabel,
                nomeLabel, nome, nomeErrorLabel,
                cognomeLabel, cognome, cognomeErrorLabel,
                emailConfermaLabel, emailConferma, emailConfermaErrorLabel,
                indirizzoLabel, indirizzo, indirizzoErrorLabel,
                telefonoLabel, telefono, telefonoErrorLabel,
                conferma, annulla
        );
    }

    /* ---------- getter------------------ */

    public VBox      getRoot()            {
        return root;
    }
    public Button    getConferma()   {
        return conferma;
    }
    public Button    getAnnulla()    {
        return annulla;
    }

    public String getSelectedCatName() {return nomeGatto.getValue();}
    public ComboBox<String> getComboBoxCatName() {return nomeGatto;}
    public String getName() {return nome.getText().trim();}
    public String getSurname() {return cognome.getText().trim();}
    public String getEmail() {return emailConferma.getText().trim();}
    public String getAddress() {return indirizzo.getText().trim();}
    public String getPhoneNumber() {
        return telefono.getText().trim();
    }

    /* ---------- gestione-errori------------------ */

    public void hideAllErrors() {
        nomeGattoErrorLabel.setVisible(false);
        nomeErrorLabel.setVisible(false);
        cognomeErrorLabel.setVisible(false);
        emailConfermaErrorLabel.setVisible(false);
        indirizzoErrorLabel.setVisible(false);
        telefonoErrorLabel.setVisible(false);
    }
    public void setNomeGattoError(String m)  {
        showError(nomeGattoErrorLabel, m);
    }
    public void setNomeError(String m)  {
        showError(nomeErrorLabel,  m);
    }
    public void setCognomeError(String m)  {
        showError(cognomeErrorLabel, m);
    }
    public void setEmailError(String m) {
        showError(emailConfermaErrorLabel, m);
    }
    public void setIndirizzoError(String m)  {
        showError(indirizzoErrorLabel, m);
    }
    public void setTelefonoError(String m)  {
        showError(telefonoErrorLabel, m);
    }

    /* ---------- gestione-errori------------------ */

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


