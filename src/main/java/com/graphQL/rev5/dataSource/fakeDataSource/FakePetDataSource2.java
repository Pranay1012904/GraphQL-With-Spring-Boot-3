package com.graphQL.rev5.dataSource.fakeDataSource;

import com.microservices.api_gateway.codegen.types.Cat2;
import com.microservices.api_gateway.codegen.types.Dog2;
import com.microservices.api_gateway.codegen.types.NewPetFoodType;
import com.microservices.api_gateway.codegen.types.Pet2;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakePetDataSource2 {

    @Autowired
    private Faker faker;

    public static final List<Pet2> PET_2_LIST = new ArrayList<>();

    @PostConstruct
    public void populatePets() {
        for (int i = 0; i < 10; i++) {
            switch (ThreadLocalRandom.current().nextInt(1, 10) % 2) {
                case 0:
                    var dog = Dog2.newBuilder()
                            .name(faker.dog().name())
                            //.food(NewPetFoodType.values()[ThreadLocalRandom.current().nextInt(NewPetFoodType.values().length)])
                            .breed(faker.dog().breed())
                            .coatLength(faker.dog().coatLength())
                            .size(faker.dog().size())
                            .build();
                    PET_2_LIST.add(dog);
                default:
                    var cat = Cat2.newBuilder()
                            .name(faker.cat().name())
                            .breed(faker.cat().breed())
                            //.food(NewPetFoodType.values()[ThreadLocalRandom.current().nextInt(NewPetFoodType.values().length)])
                            .registry(faker.cat().registry())
                            .build();
                    PET_2_LIST.add(cat);
            }
        }
    }
}
