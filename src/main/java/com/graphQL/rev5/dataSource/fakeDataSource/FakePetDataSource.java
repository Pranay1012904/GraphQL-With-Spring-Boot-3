package com.graphQL.rev5.dataSource.fakeDataSource;

import com.microservices.api_gateway.codegen.types.Cat;
import com.microservices.api_gateway.codegen.types.Dog;
import com.microservices.api_gateway.codegen.types.Pet;
import com.microservices.api_gateway.codegen.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakePetDataSource {
    @Autowired
    private Faker faker;

    public static final List<Pet> PET_LIST = new ArrayList<>();

    @PostConstruct
    public void fakePetDataSource() {
        for (int i = 0; i < 10; i++) {
            switch (i % 2) {
                case 1:
                    var dog = Dog.newBuilder()
                            .name(faker.dog().name())
                            .breed(faker.dog().breed())
                            .food(PetFoodType.values()[ThreadLocalRandom.current().nextInt(PetFoodType.values().length)])
                            .size(faker.dog().size())
                            .coatLength(faker.dog().coatLength())
                            .build();
                    PET_LIST.add(dog);
                default:
                    var cat= Cat.newBuilder()
                            .name(faker.cat().name())
                            .food(PetFoodType.values()[ThreadLocalRandom.current().nextInt(PetFoodType.values().length)])
                            .breed(faker.cat().breed())
                            .registry(faker.cat().registry())
                            .country(faker.address().country())
                            .build();
                    PET_LIST.add(cat);
            }
        }
    }
}
