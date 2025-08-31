package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakePetDataSource;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.Cat;
import com.microservices.api_gateway.codegen.types.Dog;
import com.microservices.api_gateway.codegen.types.Pet;
import com.microservices.api_gateway.codegen.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakePetDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "pets")
    public List<Pet> getPetList(@InputArgument(name = "petFilter", collectionType = PetFilter.class)
                                Optional<PetFilter> filter) {
        if (filter.isEmpty())
            return FakePetDataSource.PET_LIST;
        return FakePetDataSource.PET_LIST.stream()
                .filter(pet -> filterPet(filter.get(), pet)).toList();
    }

    private boolean filterPet(PetFilter filter, Pet pet) {
            if(StringUtils.isBlank(filter.getPetType()))
                return true;
            if(filter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())){
                return pet instanceof Dog;
            }else if(filter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())){
                return pet instanceof Cat;
            }
            return false;
    }
}
