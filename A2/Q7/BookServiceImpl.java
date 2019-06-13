public class BookServiceImpl implements BookService {
    public String getAuthor() {
        return "Hemingway";
    }

    public String getTitle() {
        return "For Whom The Bell Tolls";
    }

    public boolean isDigitalOnly() {
        return false;
    }
}