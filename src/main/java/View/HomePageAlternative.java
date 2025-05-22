package View;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HomePageAlternative {
    protected VBox root;
    private final RadioButton bookSeatOption;
    private final RadioButton AdoptOption;
    private final RadioButton MenageBookOption;
    private final RadioButton CatOption;
    private final Button confirmButton;
    private final ToggleGroup selectionGroup;
    private final Label selectionError;
	private String typeOfLogin;
	  String role;
    

    public HomePageAlternative(String typeOfLogin) {
        // Inizializzazione layout
    	this.typeOfLogin=typeOfLogin;
    	role=typeOfLogin;
    	
		boolean isUser = "user".equalsIgnoreCase(role);
        boolean isStaf = "staf".equalsIgnoreCase(role);
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.setAlignment(Pos.CENTER);

        // Titolo e descrizione
        Label title = new Label("Benvenuti nel nostro CATCAFE'!");
        

        // Gruppo di selezione con RadioButton
        selectionGroup = new ToggleGroup();

        bookSeatOption = new RadioButton("Prenota Tavolo");
        AdoptOption = new RadioButton("Adotta");
        MenageBookOption = new RadioButton("Gestisci prenotazioni");
        CatOption = new RadioButton("Gestisci Gatti");

        bookSeatOption.setToggleGroup(selectionGroup);
        AdoptOption.setToggleGroup(selectionGroup);
        MenageBookOption.setToggleGroup(selectionGroup);
        CatOption.setToggleGroup(selectionGroup);

        bookSeatOption .setDisable(!isUser);
        AdoptOption    .setDisable(!isUser);

        MenageBookOption.setDisable(!isStaf);
        CatOption .setDisable(!isStaf);
        // Messaggio di errore nascosto inizialmente
        selectionError = new Label("Seleziona un'opzione prima di confermare.");
        selectionError.setStyle("-fx-text-fill: red;");
        selectionError.setVisible(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma Scelta");

        // Aggiunta degli elementi alla root
        root.getChildren().addAll(
                title,
               
                bookSeatOption,
                AdoptOption,
                MenageBookOption,
                CatOption,
                selectionError,
                confirmButton
        );
    }

    public RadioButton getBookSeatOption() {
        return bookSeatOption;
    }

    public RadioButton getAdoptOption() {
        return AdoptOption;
    }

    public RadioButton getMenageBookOption() {
        return MenageBookOption;
    }
    public RadioButton getCatOption() {
        return CatOption;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public ToggleGroup getSelectionGroup() {
        return selectionGroup;
    }

    public void showSelectionError() {
        selectionError.setVisible(true);
    }

    public VBox getRoot() {
        return root;
    }
}