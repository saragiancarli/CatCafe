package com.ispw;

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

        /* ===== Selettore storage ===== */
        ToggleGroup storageGroup = new ToggleGroup();

        RadioButton rbDatabase = new RadioButton("Principale (DB)");
        rbDatabase.setToggleGroup(storageGroup);
        rbDatabase.setSelected(true);

        RadioButton rbFile = new RadioButton("Principale (File)");
        rbFile.setToggleGroup(storageGroup);

        RadioButton rbStateless = new RadioButton("Secondaria (Stateless)");
        rbStateless.setToggleGroup(storageGroup);

        /* ===== Selettore tipo utente ===== */
        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton rbUser = new RadioButton("User");
        rbUser.setToggleGroup(typeGroup);
        rbUser.setSelected(true);

        RadioButton rbStaf = new RadioButton("Staf");
        rbStaf.setToggleGroup(typeGroup);

        /* ===== Pulsante conferma ===== */
        Button confirmBtn = new Button("Conferma");
        confirmBtn.setOnAction(e -> {

            /* 1 · STORE scelto */
            Store selectedStore =
                    rbDatabase.isSelected() ? Store.DATABASE :
                    rbFile.isSelected()     ? Store.FILE      :
                                              Store.STATELESS;

            ApplicationFacade.init(selectedStore);

            /* 2 · NavigationManager */
            NavigationService nav =
                    rbStateless.isSelected()
                    ? new NavigationManagerAlternative(stage)   // interfaccia “secondaria”
                    : new NavigationManager(stage);             // interfaccia “principale”

            /* 3 · Login (user / staf) */
            String userType = rbUser.isSelected() ? "user" : "staf";
            nav.navigateToLogin(nav, userType);
        });

        /* ===== Layout ===== */
        VBox root = new VBox(12,
                new Label("Scegli la modalità di avvio:"),
                rbDatabase, rbFile, rbStateless,
                new Separator(),
                new Label("Tipo di login:"),
                rbUser, rbStaf,
                new Separator(),
                confirmBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 340, 310));
        stage.setTitle("Selezione modalità");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}