import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DVDServiceImpl implements DVDService {
    public Duration getPlayTime() {
        return Duration.ofMinutes(135);
    }

    public String getTitle() {
        return "Solo: A Star Wars Story";
    }

    public boolean isDigitalOnly() {
        return false;
    }

    public List<String> getCastList() {
        List<String> cast = new ArrayList<String>();
        cast.add("Alden Ehrenreich");
        cast.add("Joonas Suotamo");
        cast.add("Woody Harrelson");
        cast.add("Emilia Clarke");
        cast.add("Donald Glover");
        cast.add("Thandie Newton");
        cast.add("Paul Bettany");
        return cast;
    }
}