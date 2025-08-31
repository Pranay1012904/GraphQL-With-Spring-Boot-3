package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakeMobileAppDataStore;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.MobileApp;
import com.microservices.api_gateway.codegen.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.MobileApps
    )
    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter", collectionType = MobileAppFilter.class) Optional<MobileAppFilter> filterer) {
        if (filterer.isEmpty())
            return FakeMobileAppDataStore.MOBILE_APP_LIST;
        return FakeMobileAppDataStore.MOBILE_APP_LIST.stream()
                .filter(mobileApp -> appFilterHelper(mobileApp, filterer.get())).toList();
    }

    private boolean appFilterHelper(MobileApp app, MobileAppFilter filter) {
        if (StringUtils.isNotBlank(filter.getName()) && StringUtils.isNotBlank(filter.getVersion()) && StringUtils.isNotBlank(filter.getAuthor().getName())) {
            if (filter.getName().equalsIgnoreCase(app.getName()) && filter.getVersion().equalsIgnoreCase(app.getVersion()) && filter.getAuthor().getName().equalsIgnoreCase(app.getAuthor().getName()))
                return true;
        }
        return false;
    }

}
