package exe;

import controller_grafici.NavigationManager;
import controller_grafici.NavigationManagerAlternative;
import controller_grafici.NavigationService;
import dao.DaoFactory.Store;
import facade.ApplicationFacade;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        /* ---------------- scelta modalità storage ---------------- */
        ToggleGroup storageGroup = new ToggleGroup();

        RadioButton rbDatabase  = new RadioButton("Interfaccia Principale (DB)");
        rbDatabase.setToggleGroup(storageGroup);
        rbDatabase.setSelected(true);                        // default

        RadioButton rbStateless = new RadioButton("Interfaccia Secondaria (Stateless)");
        rbStateless.setToggleGroup(storageGroup);

        /* ---------------- scelta tipo utente --------------------- */
        ToggleGroup typeGroup = new ToggleGroup();

        RadioButton rbUser = new RadioButton("User");
        rbUser.setToggleGroup(typeGroup);
        rbUser.setSelected(true);

        RadioButton rbStaf = new RadioButton("Staf");
        rbStaf.setToggleGroup(typeGroup);

        /* ---------------- pulsante conferma ---------------------- */
        Button confirmBtn = new Button("Conferma");
        confirmBtn.setOnAction(e -> {

            /* 1 · inizializza ApplicationFacade (UNA SOLA VOLTA) */
            Store selectedMode = rbDatabase.isSelected() ? Store.DATABASE : Store.STATELESS;
            ApplicationFacade.init(selectedMode);

            /* 2 · scegli il NavigationService */
            NavigationService nav = rbDatabase.isSelected()
                    ? new NavigationManager(stage)
                    : new NavigationManagerAlternative(stage);

            /* 3 · apri la schermata di login con il tipo utente scelto */
            String userType = rbUser.isSelected() ? "user" : "staf";
            nav.navigateToLogin(nav, userType);
        });

        /* ---------------- layout ------------------------------- */
        VBox root = new VBox(12,
                rbDatabase, rbStateless,
                new Separator(),
                rbUser, rbStaf,
                new Separator(),
                confirmBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 320, 260));
        stage.setTitle("Seleziona modalità di avvio");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}