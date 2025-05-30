package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomePageView {

    private final VBox    root = new VBox(20);

    private final Button bookSeatButton     = new Button("Prenota un tavolo");
    private final Button adoptionButton     = new Button("Adotta un gatto!!");
    private final Button menageBookingButton= new Button("Gestisci prenotazioni");
    private final Button menageCatButton    = new Button("Gestisci gatti");
    private final Button logoutButton       = new Button("Logout");

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

        menageBookingButton.setDisable(!isStaf);
        menageCatButton.setDisable(!isStaf);

        /* Ordine nella VBox: titolo → descrizione → bottoni */
        root.getChildren().addAll(
                title, description,
                bookSeatButton, adoptionButton,
                menageBookingButton, menageCatButton,
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
    public Button getMenageBookingButton(){
    	return menageBookingButton;
    	}
    public Button getMenageCatButton() {
    	return menageCatButton; 
    	}
    public Button getLogoutButton() {
    	return logoutButton; 
    	}
}