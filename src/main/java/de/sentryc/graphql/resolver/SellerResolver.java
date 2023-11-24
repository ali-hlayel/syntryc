package de.sentryc.graphql.resolver;

import de.sentryc.graphql.input.SellerFilter;
import de.sentryc.graphql.ouput.SellerPageableResponse;
import de.sentryc.graphql.input.SellerSortBy;
import de.sentryc.graphql.input.PageInput;
import de.sentryc.service.SellerService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerResolver implements GraphQLQueryResolver {
    @Autowired
    private SellerService sellerService;
    public SellerPageableResponse getSellers(SellerFilter filter, PageInput page, SellerSortBy sortBy) {
        return sellerService.getSellers(filter, page, sortBy);
    }
}
