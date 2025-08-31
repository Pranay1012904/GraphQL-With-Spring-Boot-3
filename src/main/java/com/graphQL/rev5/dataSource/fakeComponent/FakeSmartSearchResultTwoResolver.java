package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakeBookDataSource;
import com.graphQL.rev5.dataSource.fakeDataSource.FakeMobileAppDataStore;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.SmartSearchResult2;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeSmartSearchResultTwoResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.SmartSearchResult2)
    public List<SmartSearchResult2> getResult(@InputArgument(name = "keyword", collectionType = SmartSearchResult2.class)
                                              Optional<String> keyword) {
        List<SmartSearchResult2> result = new ArrayList<>();
        if (keyword.isEmpty()) {
            result.addAll(FakeMobileAppDataStore.MOBILE_APP_LIST);
            result.addAll(FakeBookDataSource.BOOK_LIST);
        } else {
            String key = keyword.get();
            FakeBookDataSource.BOOK_LIST.stream().filter(b -> b.getTitle().equalsIgnoreCase(key)).forEach(result::add);
            FakeMobileAppDataStore.MOBILE_APP_LIST.stream().filter(app -> app.getName().equalsIgnoreCase(key)).forEach(result::add);
        }
        return result;

    }
}
