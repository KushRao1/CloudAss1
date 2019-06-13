import java.util.List;

public class EmployerService {
    public float calculatePayForHourlyWorker(Employer employer, int hours) {
        float cost = 0.0f;
        List<HourlyWorker> hourlyWorkers = employer.getHourlyWorkers();
        for (int i = 0; i < hourlyWorkers.size(); i++) {
            HourlyWorker worker = hourlyWorkers.get(i);
            cost += worker.getHourlyRate() * hours;
        }
        return cost;
    }

    public float calculatePayForSalaryWorker(Employer employer, int hours) {
        float cost = 0.0f;
        List<SalaryWorker> salaryWorkers = employer.getSalaryWorkers();
        for (int i = 0; i < salaryWorkers.size(); i++) {
            SalaryWorker worker = salaryWorkers.get(i);
            float hourlyRate = worker.getSalary() / (52 * worker.getHoursPerWeek());
            cost += hourlyRate * hours;
        }
        return cost;
    }

    public void outputWageCostsForAllStaff(Employer employer, int hours) {
        float cost = 0.0f;
        cost += calculatePayForHourlyWorker(employer, hours);
        cost += calculatePayForSalaryWorker(employer, hours);
        System.out.println("Total wage cost for all staff = $" + cost);
    }
}
