package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HomePageAlternative {
    protected VBox root;
    private final RadioButton bookSeatOption;
    private final RadioButton adoptOption;
    private final RadioButton menageBookOption;
    private final RadioButton catOption;
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
        adoptOption = new RadioButton("Adotta");
        menageBookOption = new RadioButton("Gestisci prenotazioni");
        catOption = new RadioButton("Gestisci Gatti");

        bookSeatOption.setToggleGroup(selectionGroup);
        adoptOption.setToggleGroup(selectionGroup);
        menageBookOption.setToggleGroup(selectionGroup);
        catOption.setToggleGroup(selectionGroup);

        bookSeatOption .setDisable(!isUser);
        adoptOption    .setDisable(!isUser);

        menageBookOption.setDisable(!isStaf);
        catOption .setDisable(!isStaf);
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
                adoptOption,
                menageBookOption,
                catOption,
                selectionError,
                confirmButton
        );
    }

    public RadioButton getBookSeatOption() {
        return bookSeatOption;
    }

    public RadioButton getAdoptOption() {
        return adoptOption;
    }

    public RadioButton getMenageBookOption() {
        return menageBookOption;
    }
    public RadioButton getCatOption() {
        return catOption;
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