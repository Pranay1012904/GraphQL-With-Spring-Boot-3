package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakeHelloDataSource;
import com.microservices.api_gateway.codegen.types.Hello;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@DgsComponent
public class FakeHelloDataResolver {
    //private FakeHelloDataSource fakeHelloDataSource;

    @DgsQuery
    public List<Hello> allHellos(){
      return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsQuery
    public Hello oneHello(){
        return FakeHelloDataSource.HELLO_LIST.get(ThreadLocalRandom.current().nextInt(0,9));
    }
}
