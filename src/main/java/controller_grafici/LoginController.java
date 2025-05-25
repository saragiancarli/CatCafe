package controller_grafici;

import javafx.scene.layout.VBox;
import java.util.logging.Logger;

import bean.LoginBean;
import bean.ModelBeanFactory;
import dao.SessionManager;

import exception.WrongLoginCredentialsException;
import facade.ApplicationFacade;

import view.LoginView;

public class LoginController {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final NavigationService navigationService;
    private final String typeOfLogin;              // scelta esterna: "user" | "staf"

    private final LoginView loginView;

    public LoginController(NavigationService navigationService, String typeOfLogin) {
        this.navigationService = navigationService;
        this.typeOfLogin       = typeOfLogin;

        this.loginView = new LoginView(typeOfLogin);
        addEventHandlers();
    }

    /* ---------------------------------------------------------- */
    private void addEventHandlers() {
        loginView.getLoginButton().setOnAction(_ -> handleLogin());
        loginView.getRegisterButton().setOnAction(_ -> goToRegistration());
    }

    /* ---------------------------------------------------------- */
    private void handleLogin() {

        /* ---------- POPOLA IL BEAN DALLA VIEW ---------- */
        LoginBean bean = ModelBeanFactory.getLoginBean(loginView);

        /* ---------- VALIDAZIONE GUI (no più validateFields) ---------- */
        boolean ok = true;
        loginView.hideErrorMessages();                 // reset

        if (!bean.hasValidEmail()) {
            loginView.showEmailError();
            ok = false;
        }
        if (!bean.hasValidPassword()) {
            loginView.showPasswordError();
            ok = false;
        }
        // userType è fissato dal costruttore oppure dai radio-button
        if (!bean.hasUserType()) {
            loginView.getErrorMessage().setText("Seleziona User o Staf.");
            loginView.getErrorMessage().setVisible(true);
            loginView.getErrorMessage().setManaged(true);
            ok = false;
        }

        if (!ok) return;                              // ferma se errori GUI

        /* ---------- BUSINESS: chiamata ApplicationFacade ----------- */
        try {
            logger.info(() -> "Tentativo di login per utente: " + bean.getUserType());

            if (ApplicationFacade.isLoginValid(bean)) {
                logger.info("Login riuscito!");
                SessionManager.getInstance().setCredentials(bean);
                navigateToNextPage();
            } else {
                throw new WrongLoginCredentialsException("Email o password non validi");
            }

        } catch (WrongLoginCredentialsException e) {
            loginView.getErrorMessage().setText(e.getMessage());
            loginView.getErrorMessage().setVisible(true);
            loginView.getErrorMessage().setManaged(true);
            logger.warning("Errore di login: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Accesso negato", "Credenziali errate", e.getMessage());

        }  catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
    }

    private void navigateToNextPage() {
        navigationService.navigateToHomePage(this.navigationService, this.typeOfLogin);
    }

    private void goToRegistration() {
        navigationService.navigateToRegistration(navigationService, typeOfLogin);
    }

    public VBox getRoot() {
        return loginView.getRoot();
    }
}