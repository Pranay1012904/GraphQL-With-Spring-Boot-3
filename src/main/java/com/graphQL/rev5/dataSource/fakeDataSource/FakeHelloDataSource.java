package com.graphQL.rev5.dataSource.fakeDataSource;

import com.microservices.api_gateway.codegen.types.Hello;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakeHelloDataSource {

    @Autowired
    private Faker faker;

    public static final List<Hello> HELLO_LIST = new ArrayList<>();

    @PostConstruct
    public void fakeHelloDataSource() {
        for (int i = 0; i < 10; i++) {
            var hello = Hello.newBuilder()
                    .text(faker.text().text(10))
                    .randomNumber(faker.random().nextInt(1000, 9999))
                    .build();
            HELLO_LIST.add(hello);
        }
    }
}
