package bean;

import entity.Client;
import entity.User;

import java.util.List;

public class RequestAdoptionBean {

    private String nameCat;
    private int phoneNumber;
    private String name;
    private String surname;
    private String email;
    private String address;
    private boolean stateAdoption;

    // =================== Getter & Setter =================== //

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStateAdoption() {
        return stateAdoption;
    }

    public void setStateAdoption(boolean stateAdoption) {
        this.stateAdoption = stateAdoption;
    }
    // =================== Coerenza e correttezza dati =================== //
    public boolean hasValidName() {
        return name != null && !name.trim().isEmpty();
    }

    public boolean hasValidSurname() {
        return surname != null && !surname.trim().isEmpty();
    }

    public boolean hasValidPhoneNumber() {
        return String.valueOf(phoneNumber).length() >= 8; // puoi aggiustare a seconda del formato
    }

    public boolean hasValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean hasValidAddress() {
        return address != null && !address.isBlank();
    }
    //esiste effettvamente lo user
    public boolean matchesExistingUser(List<Client> clients) {
        if (!hasValidName() || !hasValidSurname()) return false;
        return clients.stream()
                .filter(client -> client instanceof User)
                .filter(client -> client.getFirstName() != null && client.getLastName() != null)
                .anyMatch(client ->
                        client.getFirstName().equalsIgnoreCase(name) &&
                                client.getLastName().equalsIgnoreCase(surname));
    }
}
