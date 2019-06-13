public class AccountService {

    public void credit(final Account account, final Currency currency, final double amount) {
        double balance = account.getBalance();
        if(currency.equals(account.getCurrency())) {
            balance += amount;    
        } else {
            balance += amount * CurrencyExchangeRate.getExchangeRateForCurrency(currency);
        }
        account.setBalance(balance);
    }

    public void debit(final Account account, final Currency currency, final float amount) {
        double balance = account.getBalance();
        if(currency.equals(account.getCurrency())) {
            balance -= amount;
        } else {
            balance -= amount * CurrencyExchangeRate.getExchangeRateForCurrency(currency);
        }
        account.setBalance(balance);
    }
}
