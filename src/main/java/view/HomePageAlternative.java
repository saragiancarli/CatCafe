package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HomePageAlternative {
    protected VBox root;
    private final RadioButton adoptOption;
    private final RadioButton catOption;
    private final Button confirmButton;
    private final ToggleGroup selectionGroup;
    private final Label selectionError;
    private final RadioButton changeToStaftOption;
    private final RadioButton changeToUserOption;
	private final String typeOfLogin;
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

        adoptOption = new RadioButton("Adotta");
        catOption = new RadioButton("Gestisci Gatti");
        changeToStaftOption= new RadioButton("Vuoi passare al pannello di controllo dello Staf?");
        changeToUserOption= new RadioButton("Vuoi passare alla schermata da Utente?");

        adoptOption.setToggleGroup(selectionGroup);
        catOption.setToggleGroup(selectionGroup);
        changeToStaftOption.setToggleGroup(selectionGroup);
        changeToUserOption.setToggleGroup(selectionGroup);

        adoptOption    .setDisable(!isUser);
        changeToStaftOption.setDisable(!isUser);

        catOption .setDisable(!isStaf);
        changeToUserOption.setDisable(!isStaf);
        // Messaggio di errore nascosto inizialmente
        selectionError = new Label("Seleziona un'opzione prima di confermare.");
        selectionError.setStyle("-fx-text-fill: red;");
        selectionError.setVisible(false);
        
        // Bottone di conferma
        confirmButton = new Button("Conferma Scelta");
        

        // Aggiunta degli elementi alla root
        root.getChildren().addAll(
                title,

                adoptOption,
                
                catOption,
                selectionError,
                confirmButton,
                changeToStaftOption,
                changeToUserOption
        );
    }
    public RadioButton getAdoptOption() {
        return adoptOption;
    }
    public RadioButton getCatOption() {
        return catOption;
    }
    public Button getConfirmButton() {
        return confirmButton;
    }
    public RadioButton getChangeToStafButton() {
    	return changeToStaftOption; 
    	}
    public RadioButton getChangeToUserButton() {
    	return changeToUserOption; 
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