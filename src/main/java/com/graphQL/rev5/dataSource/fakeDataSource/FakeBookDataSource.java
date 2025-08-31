package com.graphQL.rev5.dataSource.fakeDataSource;

import com.microservices.api_gateway.codegen.types.Address;
import com.microservices.api_gateway.codegen.types.Author;
import com.microservices.api_gateway.codegen.types.Book;
import com.microservices.api_gateway.codegen.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakeBookDataSource {

    @Autowired
    private Faker faker;
    public static final List<Book> BOOK_LIST = new ArrayList<>();

    @PostConstruct
    public void fakeBookDataResolver() {
        for (int i = 0; i < 10; i++) {
            var releaseHistory = ReleaseHistory.newBuilder()
                    .year(faker.random().nextInt(1960, 2020))
                    .printedEdition(faker.bool().bool())
                    .releaseHistory(faker.commerce().brand())
                    .build();
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
            var book = Book.newBuilder()
                    .author(author)
                    .released(releaseHistory)
                    .title(faker.book().title())
                    .publisher(faker.book().publisher())
                    .build();
            BOOK_LIST.add(book);
        }
    }
}
