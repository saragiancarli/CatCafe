package controller_applicativi;

import dao.CatDaoDB;
import dao.DatabaseConnectionManager;
import entity.Cat;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.ManageCatBean;

public class ManageCatController {

    private static final Logger LOG =
            Logger.getLogger(ManageCatController.class.getName());

    
    private final CatDaoDB catDao = new CatDaoDB(DatabaseConnectionManager.getConnection());

    /* ----- carica tutti i gatti ----- */
    public List<Cat> loadAll() {
        return catDao.readAdoptableCats();
    }

    /* ----- aggiunta nuovo gatto ----- */
    public void newCat(ManageCatBean bean) {
        if (!bean.isSelected()) {
            LOG.log(Level.WARNING, "Nessun gatto selezionato per la creazione.");
            return;
        }

        try {
            catDao.create(bean.getSelected());  // Assicurati che CatDaoDB abbia create/update/delete
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Errore DB durante l'inserimento del gatto", e);
        }
    }

    /* ----- cancellazione gatto ----- */
    public void cancelCat(ManageCatBean bean) {
        if (!bean.isSelected()) {
            LOG.log(Level.WARNING, "Nessun gatto selezionato per la cancellazione.");
            return;
        }

        try {
            catDao.delete(bean.getSelected().getIdCat());
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Errore DB durante la cancellazione del gatto", e);
        }
    }

    /* ----- modifica di un gatto ----- */
    public void makeChanges(ManageCatBean bean) {
        if (!bean.isSelected()) {
            LOG.log(Level.WARNING, "Nessun gatto selezionato per la modifica.");
            return;
        }

        try {
            catDao.update(bean.getSelected());
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Errore DB durante l'aggiornamento del gatto", e);
        }
    }
}
