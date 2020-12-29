package model;

/**
 * This class is responsible for modelling LoginInfo which details the attributes and methods of each LoginInfo object
 * from a particular user logging into the STARS Planner.
 */
public class LoginInfo {

    /**
     * An {@code AccountType} variable declared as accountType representing the type of account of the user
     * who is logging in
     * @see AccountType
     */
    private AccountType accountType;

    /**
     * A String declared as userId representing the userId entered from the user logging in
     * who is logging in
     */
    private String userId;

    /**
     * A String declared as password representing the password entered from the user logging in
     * who is logging in
     */
    private String password;

    /**
     * A constructor which constructs the LoginInfo object.
     * @param accountType The {@code AccountType} of the user logging in.
     * @param userId The userId entered by the user logging in
     * @param password The password entered by the user logging in
     * @see AccountType
     */
    public LoginInfo(AccountType accountType, String userId, String password) {
        this.accountType = accountType;
        this.userId = userId;
        this.password = password;
    }

    /**
     * Returns the password that the {@code LoginInfo} object holds.
     * @return The String representing the password of the {@code LoginInfo} object
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user id that the {@code LoginInfo} object holds.
     * @return The String representing the userId of the {@code LoginInfo} object
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the {@code AccountType} that the {@code LoginInfo} object holds.
     * @return The {@code AccountType} representing the type of account of the {@code LoginInfo} object
     * @see AccountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Logic to check if two {@code LoginInfo} objects are the same. First checks if the argument passed in is of
     * {@code LoginInfo} type and if it is, check whether the userId, password and {@code AccountType} of both
     * the {@code LoginInfo} objects are the same. Returns true if they are the same, false otherwise.
     * @return A boolean value with true representing that the two {@code LoginInfo} objects are the same; false otherwise.
     * @param obj The Object which will be evaluated.
     * @see AccountType
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LoginInfo) {
            LoginInfo loginInfoObj = (LoginInfo) obj;

            return getUserId().equals(loginInfoObj.getUserId()) &&
                    getPassword().equals(loginInfoObj.getPassword()) &&
                    getAccountType().equals(loginInfoObj.getAccountType());
        }

        return false;
    }
}
