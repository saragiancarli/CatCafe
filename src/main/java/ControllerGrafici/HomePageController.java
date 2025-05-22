package ControllerGrafici;
import View.HomePageView;
import javafx.scene.layout.VBox;
public class HomePageController {
    
    private final NavigationService navigationService;
	private String typeOfLogin;
	private HomePageView HomeView;
   
    

    public HomePageController(NavigationService navigationService,String typeOfLogin) {
        this.navigationService = navigationService;
        this.typeOfLogin=typeOfLogin;
        this.HomeView = new HomePageView(typeOfLogin);
        addEventHandlers();
        
    }

    

    private void addEventHandlers() {
       
    	HomeView.getBookButton().setOnAction(e -> goToBooking());
    	HomeView.getMenageBookingButton().setOnAction(e -> goToMenageBooking());
    	HomeView.getLogoutButton().setOnAction(e ->goToLogin());
    	
    }

    public void goToBooking() {
   	navigationService.navigateToBooking(navigationService,typeOfLogin);
    }
    public void goToMenageBooking() {
       	navigationService.navigateToMenageBooking(navigationService,typeOfLogin);
        }
    public void goToLogin() {
       	navigationService.navigateToLogin(navigationService,typeOfLogin);
        }

    public VBox getRoot() {
    	return this.HomeView.getRoot();
    }
}