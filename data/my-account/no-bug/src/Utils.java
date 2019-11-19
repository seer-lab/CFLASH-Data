public class Utils {

    synchronized static public String accountToString(Account account) {
        return "Account: " + account.name + " -> balance $" + account.balance;
    }
}