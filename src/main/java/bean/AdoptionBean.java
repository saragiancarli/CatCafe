package bean;

public class AdoptionBean {

    private String  nameCat;
    private String  phoneNumber;
    private String  name;
    private String  surname;
    private String  email;
    private String  address;
    private boolean stateAdoption;

    /* -------------- validazioni -------------- */
    private static final String EMAIL_RX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public boolean hasValidName() {
        return name != null && !name.isBlank() && name.length() <= 100;
    }

    public boolean hasValidSurname() {
        return surname != null && !surname.isBlank() && surname.length() <= 100;
    }

    public boolean hasValidPhoneNumber() {
        return phoneNumber != null && phoneNumber.matches("\\d{10,15}");
    }

    public boolean hasValidEmail() {
        return email != null && email.matches(EMAIL_RX) && email.length() <= 254;
    }

    public boolean hasValidAddress() {
        return address != null && !address.isBlank() && address.length() <= 200;
    }

    public boolean hasValidStatus() {
        return !stateAdoption;
    }

    /* -------------- getter / setter -------------- */
    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public boolean getStateAdoption() {
        return stateAdoption;
    }

    public void setStateAdoption(boolean stateAdoption) {
        this.stateAdoption = stateAdoption;
    }
}
