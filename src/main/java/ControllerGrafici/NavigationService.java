package ControllerGrafici;


import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public interface NavigationService {
   
    void navigateToLogin(NavigationService navigationService,String typeOfLogin);
    void navigateToRegistration(NavigationService navigationService,  String typeOfLogin);
    void navigateToHomePage(NavigationService navigationService,String typeOfLogin);
    void navigateToBooking(NavigationService navigationService,String typeOfLogin);
     void navigateToMenageBooking(NavigationService navigationService,String typeOfLogin);
    Stage getStage();
    }