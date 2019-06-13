import java.util.ArrayList;
import java.util.List;

public class Employer {
    private List<HourlyWorker> hourlyWorkers;
    private List<SalaryWorker> salaryWorkers;

    public Employer() {
        hourlyWorkers = new ArrayList<HourlyWorker>();
        for (int i = 0; i < 5; i++) {
            hourlyWorkers.add(new HourlyWorker());
        }
        salaryWorkers = new ArrayList<SalaryWorker>();
        for (int i = 0; i < 5; i++) {
            salaryWorkers.add(new SalaryWorker());
        }
    }

    public List<HourlyWorker> getHourlyWorkers() {
        return hourlyWorkers;
    }

    public List<SalaryWorker> getSalaryWorkers() {
        return salaryWorkers;
    }
}