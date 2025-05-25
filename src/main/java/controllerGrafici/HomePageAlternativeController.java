package controllerGrafici;

import view.HomePageAlternative;
import javafx.scene.control.Toggle;
import javafx.scene.layout.VBox;


public class HomePageAlternativeController {
    
    private final NavigationService navigationService;

    private String typeOfLogin;
    
    private HomePageAlternative homeView;
	
    public HomePageAlternativeController(NavigationService navigationService,String typeOfLogin) {
        this.navigationService = navigationService;
        this.homeView = new HomePageAlternative(typeOfLogin);
        this.typeOfLogin=typeOfLogin;
        addEventHandlers();
    }

    
    private void addEventHandlers() {
    	homeView.getConfirmButton().setOnAction(e -> {

        Toggle sel = homeView.getSelectionGroup().getSelectedToggle();
        if (sel == null) {                    // nessuna scelta
        	homeView.showSelectionError();
            return;
        }
        /* Radio-button  ➜  navigazione corrispondente */
        if (sel == homeView.getBookSeatOption()) {
        	navigationService.navigateToBooking(navigationService, typeOfLogin);

        } 
        /*else if (sel == view.getAdoptOption()) {
            navigationService.navigateToAdoption(navigationService, typeOfLogin);}     */

         
    else if (sel == homeView.getMenageBookOption()) {
    	navigationService.navigateToMenageBooking(navigationService, typeOfLogin);       

        } 
        /*else if (sel == view.getCatOption()) {
            //navigationService.navigateToManageCats(navigationService);              
        }*/
    });
}

  

    
   public void goToBooking() {
	   	navigationService.navigateToBooking(navigationService,typeOfLogin);
	    }
   public void goToMenageBooking() {
	   	navigationService.navigateToBooking(navigationService,typeOfLogin);
	    }
    public VBox getRoot() {
    	return this.homeView.getRoot();}
       
}