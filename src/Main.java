import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(-3);
        System.out.println(localDate.toString());
    }
}