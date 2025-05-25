package bean;



import java.util.List;


import dao.SessionManager;
import Entity.Booking;
import View.BookingView;
import View.LoginAlternativeView;
import View.LoginView;
import View.RegistrationView;
import View.RegistrationViewAlternative;

public final class ModelBeanFactory {

    private ModelBeanFactory() { }            // utility-class

    /* ------------------------------------------------------------------
     *  LOGIN BEAN –  versione “view principale”
     * ------------------------------------------------------------------ */
    public static LoginBean getLoginBean(LoginView view) {

        LoginBean bean = new LoginBean();
        bean.setEmail(view.getEmailField().getText());
        bean.setPassword(view.getPasswordField().getText());

        // RadioButton o toggle nella view principale (“user” / “staf”)
        if (view.getType().equals("user")) {
            bean.setUserType("user");
        } else  {
            bean.setUserType("staf");
        } // else null

        return bean;
    }

    /* ------------------------------------------------------------------
     *  LOGIN BEAN – versione “alternative view”
     * ------------------------------------------------------------------ */
    public static LoginBean getLoginBean(LoginAlternativeView view) {

        LoginBean bean = new LoginBean();
        bean.setEmail(view.getEmailField().getText());
        bean.setPassword(view.getPasswordField().getText());

        if (view.getClientLoginOption().isSelected()) {
            bean.setUserType("user");
        } else if (view.getReceptionistLoginOption().isSelected()) {
            bean.setUserType("staf");
        }

        return bean;
    }

    /* ------------------------------------------------------------------
     *  BEAN caricati dalla sessione (dopo login riuscito)
     * ------------------------------------------------------------------ */
    public static LoginBean loadLoginBean() {

        if (SessionManager.getInstance().getEmail() == null) return null;

        LoginBean bean = new LoginBean();
        bean.setEmail(SessionManager.getInstance().getEmail());
        bean.setPassword(SessionManager.getInstance().getPassword());
        bean.setUserType(SessionManager.getInstance().getType());
        return bean;
    }

    /* ------------------------------------------------------------------
     *  REGISTRAZIONE – bean di supporto
     * ------------------------------------------------------------------ */
    /* -------- view “normale” -------- */
    public static ClientRegistrationBean getClientRegistrationBean(RegistrationView v) {
        ClientRegistrationBean b = new ClientRegistrationBean();
        b.setFirstName(v.getFirstNameField().getText());
        b.setLastName(v.getLastNameField().getText());
        b.setEmail(v.getEmailField().getText());
        b.setPhoneNumber(v.getPhoneNumberField().getText());
        b.setPassword(v.getPasswordField().getText());
        b.setRepeatPassword(v.getRepeatPasswordField().getText());
        b.setUserType(v.getuserType());                     // "user" / "staf"
        return b;
    }

    /* -------- view alternativa -------- */
    public static ClientRegistrationBean getClientRegistrationBean(RegistrationViewAlternative v) {
        ClientRegistrationBean b = new ClientRegistrationBean();
        b.setFirstName(v.getFirstNameField().getText());
        b.setLastName(v.getLastNameField().getText());
        b.setEmail(v.getEmailField().getText());
        b.setPhoneNumber(v.getPhoneNumberField().getText());
        b.setPassword(v.getPasswordField().getText());
        b.setRepeatPassword(v.getRepeatPasswordField().getText());
        b.setUserType(v.getSelectedUserType());
        return b;
    }
    public static BookingBean getBookingBean(BookingView v) {
        BookingBean b = new BookingBean();
        b.setTitle   (v.getNomePrenotazione());
        b.setDate (v.getDate());
        b.setTime(v.getTime());                // nel tuo View “Ora” è il secondo DatePicker
        b.setSeats   (v.getParticipants());
        b.setConfirmationEmail(v.getConfirmationEmail());
        return b;
    }
    public static List<ManageBookingBean> getManageBookingBeans(List<Booking> bookings) {
        return bookings.stream()
                       .map(ManageBookingBean::new)   // usa il costruttore ❶
                       .toList();                     // disponibile da Java 16
    }
}