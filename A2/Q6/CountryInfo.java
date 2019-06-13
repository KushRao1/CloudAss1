public class CountryInfo {
    private String name;
    private String currency;
    private double agricultureIncome;
    private double manufacturingIncome;

    public CountryInfo() {
        super();
    }

    public CountryInfo(String name, String currency, double agricultureIncome, double manufacturingIncome) {
        super();
        this.name = name;
        this.currency = currency;
        this.agricultureIncome = agricultureIncome;
        this.manufacturingIncome = manufacturingIncome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAgricultureIncome() {
        return agricultureIncome;
    }

    public void setAgricultureIncome(double agricultureIncome) {
        this.agricultureIncome = agricultureIncome;
    }

    public double getManufacturingIncome() {
        return manufacturingIncome;
    }

    public void setManufacturingIncome(double manufacturingIncome) {
        this.manufacturingIncome = manufacturingIncome;
    }

}
