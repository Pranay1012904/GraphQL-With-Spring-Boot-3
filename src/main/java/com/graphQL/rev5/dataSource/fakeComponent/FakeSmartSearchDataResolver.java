package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakeBookDataSource;
import com.graphQL.rev5.dataSource.fakeDataSource.FakeHelloDataSource;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.SmartSearchResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeSmartSearchDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.SmartSearch)
    public List<SmartSearchResult> getSmartSearch(@InputArgument(name = "keyword")Optional<String> keyword){
        var searchList=new ArrayList<SmartSearchResult>();
        if(keyword.isEmpty()){
            searchList.addAll(FakeHelloDataSource.HELLO_LIST);
            searchList.addAll(FakeBookDataSource.BOOK_LIST);
            return searchList;
        }else{
            var input=keyword.get();
            FakeHelloDataSource.HELLO_LIST
                    .stream().filter(d->StringUtils.containsIgnoreCase(d.getText(),keyword.get()))
                            .forEach(searchList::add);
            FakeBookDataSource.BOOK_LIST
                    .stream().filter(v->StringUtils.containsIgnoreCase(v.getTitle(),keyword.get()))
                    .forEach(searchList::add);
            return searchList;
        }
    }
}
