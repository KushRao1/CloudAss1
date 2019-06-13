public class Account {
    private Currency currency;

    private double balance;

    public Account() {
        super();
    }

    public Account(Currency currency, double balance) {
        super();
        this.currency = currency;
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
}