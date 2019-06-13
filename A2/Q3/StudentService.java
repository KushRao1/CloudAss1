import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class StudentService {
    public void saveToFile(Student student) {
        if(student == null) {
            System.err.println("no student received to save to file");
            System.exit(0);
        }
        try(PrintWriter writer = new PrintWriter("student.txt", "UTF-8")) {
            writer.println(student.getBannerID());
            writer.println(student.getFirstName());
            writer.println(student.getLastName());
            writer.println(student.getEmail());
        } catch (Exception e) {
            System.err.println("problem while saving student to file: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public Student loadFromFile() {
        try(Scanner in = new Scanner(new FileReader("student.txt"))) {
            String bannerID = in.next();
            String firstName = in.next();
            String lastName = in.next();
            String email = in.next();
            return new Student(bannerID, firstName, lastName, email);
        } catch (Exception e) {
            System.err.println("problem while loading student from file: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}