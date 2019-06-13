public class Student {
    private String bannerID;
    private String firstName;
    private String lastName;
    private String email;

    public Student() {
        super();
    }

    public Student(String bannerID, String firstName, String lastName, String email) {
        super();
        this.bannerID = bannerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getBannerID() {
        return bannerID;
    }

    public void setBannerID(String bannerID) {
        this.bannerID = bannerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}