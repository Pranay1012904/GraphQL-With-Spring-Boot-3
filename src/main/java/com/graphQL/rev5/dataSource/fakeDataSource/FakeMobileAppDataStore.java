package com.graphQL.rev5.dataSource.fakeDataSource;

import com.microservices.api_gateway.codegen.types.Address;
import com.microservices.api_gateway.codegen.types.Author;
import com.microservices.api_gateway.codegen.types.MobileApp;
import com.microservices.api_gateway.codegen.types.MobileAppCategory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDataStore {
    @Autowired
    private Faker faker;
    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();

    @PostConstruct
    public void populateMobileApps() throws MalformedURLException {
        for (int i = 0; i < 10; i++) {
            var address = Address.newBuilder()
                    .city(faker.address().city())
                    .country(faker.address().country())
                    .street(faker.address().streetName())
                    .zipCode(faker.address().zipCode())
                    .build();

            List<Address> addressList = new ArrayList<>();
            addressList.add(address);
            var author = Author.newBuilder()
                    .name(faker.book().author())
                    .originCountry(faker.address().country())
                    .addresses(addressList)
                    .build();

            var mobileApp = MobileApp.newBuilder()
                    .author(author)
                    .name(faker.app().name())
                    .version(faker.app().version())
                    .platform(platformGenerator(ThreadLocalRandom.current().nextInt(10)))
                    .appId(UUID.randomUUID().toString())
                    .homepage(new URL("https://" + faker.internet().url()))
                    .downloaded(faker.random().nextInt(999))
                    .releaseDate(LocalDate.now())
                    .category(MobileAppCategory.values()[ThreadLocalRandom.current().nextInt(0, MobileAppCategory.values().length)])
                    .build();
            MOBILE_APP_LIST.add(mobileApp);
        }
    }

    private List<String> platformGenerator(int i) {
        return switch (i % 3) {
            case 0 -> List.of("iOS", "Linux");
            case 1 -> List.of("Android", "Ubuntu");
            default -> List.of("iOS", "Android");
        };
    }
}
