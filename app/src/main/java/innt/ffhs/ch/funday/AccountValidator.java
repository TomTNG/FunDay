package innt.ffhs.ch.funday;

/**
 * Validation of PW length and Mailadress
 */

public class AccountValidator {

    public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}
