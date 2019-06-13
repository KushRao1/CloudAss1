public class Customer {
    private String email;
    private String country;
    private String province;

    public Customer() {
    }
    
    public Customer(final String email, final String country, final String province) {
        this.email = email;
        this.country = country;
        this.province = province;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(final String province) {
        this.province = province;
    }

}