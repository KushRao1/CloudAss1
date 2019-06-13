import java.util.List;

public class CountryGDPReportService {
    public void printCountryGDPReport(final List<CountryInfo> countries) {
        System.out.println("GDP By Country:\n");
        for (CountryInfo country : countries) {
            System.out.println("- " + country.getName() + ":\n");
            System.out.println("   - Agriculture: " + country.getAgricultureIncome()+" "+country.getCurrency());
            System.out.println("   - Manufacturing: " + country.getManufacturingIncome()+" "+country.getCurrency());
        }
    }
}