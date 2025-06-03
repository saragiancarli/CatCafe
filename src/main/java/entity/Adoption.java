package entity;

import javafx.beans.property.*;

public class Adoption {

    private final StringProperty nameCat = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final BooleanProperty stateAdoption = new SimpleBooleanProperty(false);

    /* ---------------- helpers ---------------- */

    private boolean isValidString(String value, int maxLength) {
        return value != null && !value.trim().isEmpty() && value.length() <= maxLength;
    }

    private boolean isValidPhone(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10,15}");
    }

    private boolean isValidEmail(String emailValue) {
        return emailValue != null &&
                emailValue.length() <= 254 &&
                emailValue.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    /* ---------------- getter/setter/property ---------------- */
    public String getNameCat() { return nameCat.get(); }
    public void setNameCat(String v) { nameCat.set(v); }
    public StringProperty nameCatProperty() { return nameCat; }

    public String getPhoneNumber() { return phoneNumber.get(); }
    public void setPhoneNumber(String v) { phoneNumber.set(v); }
    public StringProperty phoneNumberProperty() { return phoneNumber; }

    public String getName() { return name.get(); }
    public void setName(String v) { name.set(v); }
    public StringProperty nameProperty() { return name; }

    public String getSurname() { return surname.get(); }
    public void setSurname(String v) { surname.set(v); }
    public StringProperty surnameProperty() { return surname; }

    public String getEmail() { return email.get(); }
    public void setEmail(String v) { email.set(v); }
    public StringProperty emailProperty() { return email; }

    public String getAddress() { return address.get(); }
    public void setAddress(String v) { address.set(v); }
    public StringProperty addressProperty() { return address; }

    public boolean getStateAdoption() { return stateAdoption.get(); }
    public void setStateAdoption(boolean v) { stateAdoption.set(v); }
    public BooleanProperty approvedProperty() { return stateAdoption; }

    /* =================== METODI DI VALIDAZIONE =================== */

    public boolean hasValidName() {
        return isValidString(getName(), 100); // 100 chars max for name
    }

    public boolean hasValidSurname() {
        return isValidString(getSurname(), 100); // 100 chars max for surname
    }

    public boolean hasValidPhoneNumber() {
        return isValidPhone(getPhoneNumber());
    }

    public boolean hasValidEmail() {
        return isValidEmail(getEmail());
    }

    public boolean hasValidAddress() {
        return isValidString(getAddress(), 200); // 200 chars max for address
    }

    public boolean hasValidStatus() {
        return !getStateAdoption();
    }

    @Override
    public String toString() {
        return "Adoption["+nameCat.get()+", "+name.get()+" "+surname.get() +
                ", "+email.get()+", "+stateAdoption.get()+"]";
    }
}