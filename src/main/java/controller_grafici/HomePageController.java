package controller_grafici;
import view.HomePageView;
import javafx.scene.layout.VBox;
public class HomePageController {
    
    private final NavigationService navigationService;
	private String typeOfLogin;
	private HomePageView homeView;
   
    

    public HomePageController(NavigationService navigationService,String typeOfLogin) {
        this.navigationService = navigationService;
        this.typeOfLogin=typeOfLogin;
        this.homeView = new HomePageView(typeOfLogin);
        addEventHandlers();
        
    }

    

    private void addEventHandlers() {
       
    	homeView.getBookButton().setOnAction(_ -> goToBooking());
    	homeView.getMenageBookingButton().setOnAction(_ -> goToMenageBooking());
    	homeView.getLogoutButton().setOnAction(_ ->goToLogin());
    	homeView.getAdoptionButton().setOnAction(_ ->goToAdoption());
    }

    public void goToBooking() {
   	navigationService.navigateToBooking(navigationService,typeOfLogin);
    }
    public void goToMenageBooking() {
       	navigationService.navigateToMenageBooking(navigationService,typeOfLogin);
        }
    public void goToAdoption() {
       	navigationService.navigateToAdopt(navigationService,typeOfLogin);
        }
    public void goToLogin() {
       	navigationService.navigateToLogin(navigationService,typeOfLogin);
        }

    public VBox getRoot() {
    	return this.homeView.getRoot();
    }
}