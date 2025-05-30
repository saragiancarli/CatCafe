package view;

import entity.Cat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.List;

/** View alternativa per la richiesta di adozione */
public class RequestAdoptionAlternative {

    /* ---------- nodi -------------- */

    protected VBox root;

    private final ComboBox<Cat> nomeGatto;  // <-- qui il tipo Cat
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

    private static final String ERR_STYLE = "error-message";

    /* ---------- costruttore ------------------ */
    public RequestAdoptionAlternative() {
        root = new VBox(20);
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.TOP_CENTER);

        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER_LEFT);

        int row = 0;

        Label title = new Label("Richiesta di Adozione (Versione alternativa)");

        grid.add(new Label("Gatto da adottare"), 0, row);
        nomeGatto = new ComboBox<>();
        nomeGatto.setPromptText(" Seleziona un gatto ");
        grid.add(nomeGatto, 1, row);
        nomeGattoErrorLabel = makeErrLabel();
        grid.add(nomeGattoErrorLabel, 2, row++);

        grid.add(new Label("Nome"), 0, row);
        nome = new TextField();
        grid.add(nome, 1, row);
        nomeErrorLabel = makeErrLabel();
        grid.add(nomeErrorLabel, 2, row++);

        grid.add(new Label("Cognome"), 0, row);
        cognome = new TextField();
        grid.add(cognome, 1, row);
        cognomeErrorLabel = makeErrLabel();
        grid.add(cognomeErrorLabel, 2, row++);

        grid.add(new Label("Email di conferma"), 0, row);
        emailConferma = new TextField();
        grid.add(emailConferma, 1, row);
        emailConfermaErrorLabel = makeErrLabel();
        grid.add(emailConfermaErrorLabel, 2, row++);

        grid.add(new Label("Indirizzo"), 0, row);
        indirizzo = new TextField();
        grid.add(indirizzo, 1, row);
        indirizzoErrorLabel = makeErrLabel();
        grid.add(indirizzoErrorLabel, 2, row++);

        grid.add(new Label("Numero di telefono"), 0, row);
        telefono = new TextField();
        grid.add(telefono, 1, row);
        telefonoErrorLabel = makeErrLabel();
        grid.add(telefonoErrorLabel, 2, row++);

        conferma = new Button("Conferma");
        annulla = new Button("Annulla");

        root.getChildren().addAll(title, grid, new HBox(10, conferma, annulla));
    }

    /* ---------- getter ------------------ */

    public VBox getRoot() {
        return root;
    }

    public Button getConferma() {
        return conferma;
    }

    public Button getAnnulla() {
        return annulla;
    }

    public String getSelectedCatName() {
        Cat selected = nomeGatto.getValue();
        return (selected != null) ? selected.getNameCat() : null;
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

    public int getPhoneNumber() {
        String text = telefono.getText().trim();
        if (text.matches("\\d+")) {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException _) {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /* ---------- gestione-errori ------------------ */

    public void hideAllErrors() {
        nomeGattoErrorLabel.setVisible(false);
        nomeErrorLabel.setVisible(false);
        cognomeErrorLabel.setVisible(false);
        emailConfermaErrorLabel.setVisible(false);
        indirizzoErrorLabel.setVisible(false);
        telefonoErrorLabel.setVisible(false);
    }

    public void setnomeGattoError(String m) {
        showErr(nomeGattoErrorLabel, m);
    }

    public void setNomeError(String m) {
        showErr(nomeErrorLabel, m);
    }

    public void setCognomeError(String m) {
        showErr(cognomeErrorLabel, m);
    }

    public void setEmailError(String m) {
        showErr(emailConfermaErrorLabel, m);
    }

    public void setIndirizzoError(String m) {
        showErr(indirizzoErrorLabel, m);
    }

    public void setTelefonoError(String m) {
        showErr(telefonoErrorLabel, m);
    }

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

    public void setAvailableCats(List<Cat> cats) {
        nomeGatto.getItems().clear();
        nomeGatto.getItems().addAll(cats);

        nomeGatto.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cat cat) {
                return (cat != null) ? cat.getNameCat() : "";
            }

            @Override
            public Cat fromString(String name) {
                return cats.stream()
                        .filter(c -> c.getNameCat().equals(name))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
}

