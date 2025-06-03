package controller_grafici;

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
    	homeView.getConfirmButton().setOnAction(_ -> {

        Toggle sel = homeView.getSelectionGroup().getSelectedToggle();
        if (sel == null) {                    // nessuna scelta
        	homeView.showSelectionError();
            return;
        }
        /* Radio-button  ➜  navigazione corrispondente */
        if (sel == homeView.getBookSeatOption()) {
        	navigationService.navigateToBooking(navigationService, typeOfLogin);

        } 
        else if (sel == homeView.getAdoptOption()) {
            navigationService.navigateToAdopt(navigationService, typeOfLogin);}

         
    else if (sel == homeView.getMenageBookOption()) {
    	navigationService.navigateToMenageBooking(navigationService, typeOfLogin);       

        } 
        else if (sel == homeView.getCatOption()) {
            navigationService.navigateToManageCat(navigationService, typeOfLogin);
        }
    });
}

  

    
   public void goToBooking() {
	   	navigationService.navigateToBooking(navigationService,typeOfLogin);
	    }
   public void goToMenageBooking() {
	   	navigationService.navigateToBooking(navigationService,typeOfLogin);
	    }
    public void goToAdopt() {
        navigationService.navigateToBooking(navigationService,typeOfLogin);
    }
    public void goToManageCat() {
        navigationService.navigateToManageCat(navigationService,typeOfLogin);
    }


    public VBox getRoot() {
    	return this.homeView.getRoot();}
       
}