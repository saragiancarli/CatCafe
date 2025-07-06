package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomePageView {

    private final VBox    root = new VBox(20);

    private final Button bookSeatButton     = new Button("Prenota un tavolo");
    private final Button adoptionButton     = new Button("Adotta un gatto!!");
   
    private final Button menageCatButton    = new Button("Gestisci i gatti del cat cafè");
    private final Button logoutButton       = new Button("Logout");
    private final Button reviviewButton       = new Button("Lascia una tua recensione");
    

    /* ------------------------------------------------------------ */
    public HomePageView(String typeOfLogin) {

        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        boolean isUser = "user".equalsIgnoreCase(typeOfLogin);
        boolean isStaf = "staf".equalsIgnoreCase(typeOfLogin);

        /* Titolo */
        Label title = new Label("Benvenuti nel nostro CATCAFE'!");

        /* Messaggio descrittivo */
        String msg = isUser
                ? "Effettuate la vostra prenotazione e godetevi i nostri gatti."
                : "Benvenuto nel pannello di controllo per lo staf.";
        Label description = new Label(msg);

        /* Abilita/disabilita bottoni in base al ruolo */
        bookSeatButton.setDisable(!isUser);
        adoptionButton.setDisable(!isUser);
        reviviewButton.setDisable(!isUser);

        menageCatButton.setDisable(!isStaf);

        /* Ordine nella VBox: titolo → descrizione → bottoni */
        root.getChildren().addAll(
                title, description,
                bookSeatButton, adoptionButton,reviviewButton,
                 menageCatButton,
                logoutButton
        );
    }

   

    public VBox   getRoot()  {
    	return root; 
    	}
    public Button getBookButton()  {
    	return bookSeatButton; 
    	}
    public Button getAdoptionButton() {
    	return adoptionButton;
    	}
    
    public Button getManageCat() {
    	return menageCatButton; 
    	}
    public Button getReviviewButton() {
    	return reviviewButton; 
    	}

    public Button getLogoutButton() {
    	return logoutButton; 
    	}
}