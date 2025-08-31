package com.graphQL.rev5.dataSource.fakeComponent;

import com.graphQL.rev5.dataSource.fakeDataSource.FakeBookDataSource;
import com.microservices.api_gateway.codegen.DgsConstants;
import com.microservices.api_gateway.codegen.types.Book;
import com.microservices.api_gateway.codegen.types.ReleaseHistory;
import com.microservices.api_gateway.codegen.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@DgsComponent
public class FakeBookDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "books")
    public List<Book> selectBooks(@InputArgument(name = "author") String authorName) {
        if (StringUtils.isBlank(authorName))
            return FakeBookDataSource.BOOK_LIST;
        else {
            return FakeBookDataSource.BOOK_LIST.stream()
                    .filter(book -> filterBooks(authorName, book.getAuthor().getName())).toList();
        }
    }

    private boolean filterBooks(String authorName, String bookAuthor) {
        if (StringUtils.containsIgnoreCase(bookAuthor, authorName))
            return true;
        return false;
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByRelease
    )
    public List<Book> getBooksByRelease(DataFetchingEnvironment dataFetchingEnvironment) {
        var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
        var releaseHistoryInput = ReleaseHistoryInput.newBuilder()
                .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .build();
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(book -> releaseHistoryMatcher(releaseHistoryInput, book.getReleased()))
                .toList();
    }

    private boolean releaseHistoryMatcher(ReleaseHistoryInput input, ReleaseHistory element) {
        return input.getPrintedEdition()==element.getPrintedEdition() &&
                input.getYear() == element.getYear();
    }
}
