public class SalaryWorker {
    private float salary;
    private int hoursPerWeek;

    public SalaryWorker() {
        salary = 50000.0f;
        hoursPerWeek = 40;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }
}