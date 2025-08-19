import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataGenerator {
    private final Faker faker = new Faker(new Locale("ru"));

    public static String generateCity() {
        return faker.address().cityName();
    }

    public static String generateName() {
        return faker.name().fullName();
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateDate(int daysFromNow) {
        return LocalDate.now().plusDays(daysFromNow)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static RegistrationInfo generateUser() {
        return new RegistrationInfo(generateCity(), generateName(), generatePhone());
    }
}
