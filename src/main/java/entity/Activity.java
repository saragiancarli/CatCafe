package entity;

import java.io.Serializable;

/** Entità “base” con nome, descrizione, max partecipanti e prezzo extra. */
public class Activity implements Serializable {

   
    private String name;
    private String description;
    
   

    public Activity() {}

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
        
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}