package de.sentryc.service;

import de.sentryc.graphql.input.PageInput;
import de.sentryc.graphql.input.SellerFilter;
import de.sentryc.graphql.ouput.SellerPageableResponse;
import de.sentryc.graphql.input.SellerSortBy;

public interface SellerService {

    SellerPageableResponse getSellers(SellerFilter filter, PageInput page, SellerSortBy sortBy);
}
