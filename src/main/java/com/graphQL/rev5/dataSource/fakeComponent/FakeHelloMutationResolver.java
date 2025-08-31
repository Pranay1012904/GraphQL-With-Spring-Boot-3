package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakeHelloDataSource;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.Hello;
import com.microservices.api_gateway.codegen.types.HelloInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeHelloMutationResolver {

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddHello)
    public int addHello(@InputArgument(name = "helloInput") Optional<HelloInput> input) {
        if (input.isEmpty()) {
            return FakeHelloDataSource.HELLO_LIST.size();
        }
        var newHello = Hello.newBuilder()
                .text(input.get().getText())
                .randomNumber(input.get().getNumber())
                .build();
        /*Hello newHello=new Hello();
        newHello.setText(input.get().getText());
        newHello.setRandomNumber(input.get().getNumber());
        FakeHelloDataSource.HELLO_LIST.add(newHello);*/
        FakeHelloDataSource.HELLO_LIST.add(newHello);
        return FakeHelloDataSource.HELLO_LIST.size();
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.ReplaceHelloText)
    public List<Hello> replaceHelloText(@InputArgument(name = "helloInput") HelloInput helloInput){
        for(Hello h: FakeHelloDataSource.HELLO_LIST){
            if(h.getRandomNumber()==helloInput.getNumber()){
                h.setText(helloInput.getText());
            }
        }
        return FakeHelloDataSource.HELLO_LIST;
    }

}
