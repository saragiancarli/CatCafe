package View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomePageView {
    protected VBox root;
    private final Button bookSeatButton;
    private final Button AdoptionButton;
    private final Button MenageBookingButton;
    private final Button MenageCatButton;
    private final Button LogoutButton;
    
	private String typeOfLogin;
	private String text;
	

    public HomePageView(String typeOfLogin) {
    	this.typeOfLogin= typeOfLogin;
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        String role = typeOfLogin; 
        boolean isUser = "user".equalsIgnoreCase(role);
        boolean isStaf = "staf".equalsIgnoreCase(role);
     
        // Titolo e descrizione
        
        Label title = new Label("Benvenuti nel nostro CATCAFE'!");
        
        if (!isUser) {
        	Label text = new Label("Effettuate la vostra prenotazione e godetevi i nostri gatti.");
        }else {
        	Label text = new Label("Benvenuto nel pannello di controllo per lo staf.");
        }
        Label description = new Label(text);
		

        // Bottoni per i casi d'uso
        bookSeatButton = new Button("Prenota Un Tavolo");
        AdoptionButton = new Button("Adotta un gatto!!");
        MenageBookingButton = new Button("Gestisci prenotazioni");
        MenageCatButton = new Button("Gestisci gatti");
        LogoutButton=new Button("Logout");
        

        

        bookSeatButton.setDisable(!isUser);
        AdoptionButton.setDisable(!isUser);

        MenageBookingButton.setDisable(!isStaf);
        MenageCatButton.setDisable(!isStaf);
        
		// Aggiungi alla root
        root.getChildren().addAll(description,title, bookSeatButton, AdoptionButton, MenageBookingButton, MenageCatButton,LogoutButton);
    }

    public Button getBookButton() {
        return bookSeatButton;
    }
    public Button getBookActivityButton() {
        return AdoptionButton;
    }
    public Button getMenageBookingButton() {
        return MenageBookingButton;
    }
    public Button getReceptionistAccessButton() {
        return MenageCatButton;
    }
    public Button getLogoutButton() {
    	return LogoutButton;
    	}
    public VBox getRoot() { 
    	return root;
    	}
}