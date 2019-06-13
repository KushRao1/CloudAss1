public enum CurrencyExchangeRate {
    USD(1), GBP(0.80), EUR(0.75);
    
    private final double exchangeRate;

    private CurrencyExchangeRate(final double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public double getExchangeRate() {
        return exchangeRate;
    }

    public static double getExchangeRateForCurrency(final Currency currency) {
        return CurrencyExchangeRate.valueOf(currency.toString()).getExchangeRate();
    }
    
}
