package View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomePageView {
    protected VBox root;
    private final Button bookSeatButton;
    private final Button adoptionButton;
    private final Button menageBookingButton;
    private final Button menageCatButton;
    private final Button logoutButton;
    
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
        adoptionButton = new Button("Adotta un gatto!!");
        menageBookingButton = new Button("Gestisci prenotazioni");
        menageCatButton = new Button("Gestisci gatti");
        logoutButton=new Button("Logout");
        

        

        bookSeatButton.setDisable(!isUser);
        adoptionButton.setDisable(!isUser);

        menageBookingButton.setDisable(!isStaf);
        menageCatButton.setDisable(!isStaf);
        
		// Aggiungi alla root
        root.getChildren().addAll(description,title, bookSeatButton, adoptionButton, menageBookingButton, menageCatButton,logoutButton);
    }

    public Button getBookButton() {
        return bookSeatButton;
    }
    public Button getBookActivityButton() {
        return adoptionButton;
    }
    public Button getMenageBookingButton() {
        return menageBookingButton;
    }
    public Button getReceptionistAccessButton() {
        return menageCatButton;
    }
    public Button getLogoutButton() {
    	return logoutButton;
    	}
    public VBox getRoot() { 
    	return root;
    	}
}