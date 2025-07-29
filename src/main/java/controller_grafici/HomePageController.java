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

        homeView.getAdoptionButton().setOnAction(_ -> goToAdopt());
       
        homeView.getManageCat().setOnAction(_ -> gotoManageCat());
        homeView.getLogoutButton().setOnAction(_ ->goToLogin());

    }
    public void goToAdopt() {
        navigationService.navigateToAdopt(navigationService,typeOfLogin);
    }
    
    public void gotoManageCat() {
        navigationService.navigateToManageCat(navigationService,typeOfLogin);
    }
    public void goToLogin() {
        navigationService.navigateToLogin(navigationService,typeOfLogin);
    }
    

    public VBox getRoot() {
        return this.homeView.getRoot();
    }
}