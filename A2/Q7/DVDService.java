import java.time.Duration;
import java.util.List;

public interface DVDService extends LibraryItemService {
    Duration getPlayTime();

    List<String> getCastList();
}
