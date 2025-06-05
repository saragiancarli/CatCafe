package controller_applicativi;

import dao.CatDaoDB;
import dao.DatabaseConnectionManager;
import entity.Cat;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.ManageCatBean;
import exception.CatDaoException;

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
    public void cancelCat(Cat cat) {
        try {
            catDao.delete(cat.getIdCat());
        } catch (SQLException e) {
            throw new CatDaoException("Errore DB durante la cancellazione del gatto", e);
        }
    }
}
