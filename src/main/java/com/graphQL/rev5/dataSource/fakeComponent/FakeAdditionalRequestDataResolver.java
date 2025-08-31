package com.graphQL.rev5.dataSource.fakeComponent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;

@DgsComponent
public class FakeAdditionalRequestDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "onAdditionalRequest")
    public String headerParsing(
            @RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
            @RequestHeader(name = "mandatoryHeader", required = true) String mandatoryHeader,
            @RequestParam(name = "optionalParam", required = false) String optionalParam,
            @RequestParam(name = "mandatoryParam", required = false) String mandatoryParam
    ) throws JsonProcessingException {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("OptionalHeader", optionalHeader);
        hm.put("MandatoryHeader", mandatoryHeader);
        hm.put("OptionalParam", optionalParam);
        hm.put("MandatoryParam", mandatoryParam);
        ObjectMapper map = new ObjectMapper();
        String headers = map.writeValueAsString(hm);

        return headers;
    }
}
