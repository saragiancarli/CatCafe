package ControllerGrafici;

import View.HomePageAlternative;
import javafx.scene.control.Toggle;
import javafx.scene.layout.VBox;


public class HomePageAlternativeController {
    
    private final NavigationService navigationService;

    private String typeOfLogin;
    
    private HomePageAlternative HomeView;
	
    public HomePageAlternativeController(NavigationService navigationService,String typeOfLogin) {
        this.navigationService = navigationService;
        this.HomeView = new HomePageAlternative(typeOfLogin);
        this.typeOfLogin=typeOfLogin;
        addEventHandlers();
    }

    
    private void addEventHandlers() {
    	HomeView.getConfirmButton().setOnAction(e -> {

        Toggle sel = HomeView.getSelectionGroup().getSelectedToggle();
        if (sel == null) {                    // nessuna scelta
        	HomeView.showSelectionError();
            return;
        }
        /* Radio-button  ➜  navigazione corrispondente */
        if (sel == HomeView.getBookSeatOption()) {
        	navigationService.navigateToBooking(navigationService, typeOfLogin);

        } //else if (sel == view.getAdoptOption()) {
            //navigationService.navigateToAdoption(navigationService, typeOfLogin);      

        //} 
    else if (sel == HomeView.getMenageBookOption()) {
    	navigationService.navigateToMenageBooking(navigationService, typeOfLogin);       

        } //else if (sel == view.getCatOption()) {
            //navigationService.navigateToManageCats(navigationService);              
        //}
    });
}

  

    
   public void goToBooking() {
	   	navigationService.navigateToBooking(navigationService,typeOfLogin);
	    }
   public void goToMenageBooking() {
	   	navigationService.navigateToBooking(navigationService,typeOfLogin);
	    }
    public VBox getRoot() {
    	return this.HomeView.getRoot();}
       
}