public class CustomerService {
    
    private final EmailSender emailSender;
    
    public CustomerService() {
        this.emailSender = new EmailSender();
    }

    public void emailCustomerSpecialOffer(final Customer customer)
    {
        String msg = "Congratulations! Your purchase history has earned you a 10% discount on your next purchase!";
        emailSender.sendEmail(customer.getEmail(), "10% off your next order!", msg);
    }
    
    public boolean checkCustomerCountry(final Customer customer, final String country) {
        return customer.getCountry().equals(country);
    }
    
    public boolean checkCustomerProvince(final Customer customer, final String province) {
        return customer.getProvince().equals(province);
    }
}
