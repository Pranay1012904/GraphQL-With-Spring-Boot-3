package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakePetDataSource2;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.Cat2;
import com.microservices.api_gateway.codegen.types.Dog2;
import com.microservices.api_gateway.codegen.types.NewPetFilter;
import com.microservices.api_gateway.codegen.types.Pet2;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@DgsComponent
public class FakePetDataResolver2 {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Newpets)
    public List<Pet2> getPets(@InputArgument(name = "newFilter", collectionType = NewPetFilter.class) NewPetFilter filter) {
        if (filter.getNewPetType().isBlank())
            return FakePetDataSource2.PET_2_LIST;
        return FakePetDataSource2.PET_2_LIST
                .stream().filter(pet -> filterPet(filter, pet)).toList();
    }

    private boolean filterPet(NewPetFilter filter, Pet2 pet) {
        if (StringUtils.isBlank(filter.getNewPetType())) {
            return false;
        }
        if (StringUtils.containsIgnoreCase(filter.getNewPetType(), "Dog")) {
            return pet instanceof Dog2;
        }
        if (StringUtils.containsIgnoreCase(filter.getNewPetType(), "Cat")) {
            return pet instanceof Cat2;
        }
        return false;
    }
}
