package controller_grafici;

import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import bean.ManageCatBean;
import controller_applicativi.ManageCatController;
import entity.Cat;
import view.ManageCatAlternative;

public class ManageCatGUIControllerAlternative {

    private final ManageCatAlternative view = new ManageCatAlternative();
    private final ManageCatController service = new ManageCatController();
    private Cat toDelete = null;
    private Cat newCat = null;

    private final NavigationService nav;
    private final String typeOfLogin;

    public ManageCatGUIControllerAlternative(NavigationService nav, String typeOfLogin) {
        this.nav = nav;
        this.typeOfLogin = typeOfLogin;

        refresh();

        /* --- pulsante Inserisci Gatto --- */
        view.getBtnInsertCat().setOnAction(_ -> {
            if (newCat != null) {
                view.showError("Completa prima l'inserimento del gatto.");
                return;
            }
            newCat = new Cat();
            newCat.setNameCat("<Nuovo Gatto>");
            view.getListView().getItems().add(0, newCat);
            view.getListView().getSelectionModel().select(0);
            // Qui puoi eventualmente aprire modalità edit
        });

        /* --- pulsante Conferma --- */
        view.getBtnConfirm().setOnAction(_ -> {
            if (newCat != null) {
                try {
                    ManageCatBean bean = new ManageCatBean();
                    bean.setSelected(newCat);
                    bean.setSelected(newCat);
                    service.newCat(bean);
                    newCat = null;
                    refresh();
                    view.hideError();
                } catch (Exception _) {
                    view.showError("Errore nell'inserimento: ");
                }
                return;
            }
            if (toDelete == null) {
                view.showError("Nessuna cancellazione in sospeso.");
                return;
            }
            try {
                service.cancelCat(toDelete);
                toDelete = null;
                refresh();
            } catch (Exception _) {
                view.showError("Errore nel cancellare il gatto: " );
            }
        });

        /* --- pulsante Cancella --- */
        view.getBtnCancel().setOnAction(_ -> {
            if (newCat != null) {
                view.showError("Completa o annulla prima l'inserimento.");
                return;
            }
            Cat sel = view.getListView().getSelectionModel().getSelectedItem();
            if (sel == null) {
                view.showError("Seleziona un gatto prima di cancellare.");
                return;
            }
            toDelete = sel;
            view.showError("Premi CONFERMA per cancellare: " + sel.getNameCat());
        });

        /* --- pulsante Back --- */
        view.getBtnBack().setOnAction(_ -> nav.navigateToHomePage(nav, typeOfLogin));
    }

    private void refresh() {
        view.hideError();
        try {
            view.setItems(FXCollections.observableArrayList(service.loadAll()));
        } catch (Exception e) {
            view.showError("Errore nel caricamento della lista gatti: " + e.getMessage());
        }
    }

    public VBox getRoot() {
        return view.getRoot();
    }
}
