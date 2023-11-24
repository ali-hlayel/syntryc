package de.sentryc.service;

import de.sentryc.domain.Seller;
import de.sentryc.graphql.input.PageInput;
import de.sentryc.graphql.input.SellerFilter;
import de.sentryc.graphql.input.SellerSortBy;
import de.sentryc.graphql.ouput.PageMeta;
import de.sentryc.service.dto.SellerDto;
import de.sentryc.graphql.ouput.SellerPageableResponse;
import de.sentryc.service.dto.SellerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final SellerMapper sellerMapper;

    public SellerPageableResponse getSellers(SellerFilter filter, PageInput page, SellerSortBy sortBy) {

        NativeSearchQueryBuilder queryBuilder = buildSearchQuery(filter, page, sortBy);
        SearchHits<Seller> sellers = elasticsearchRestTemplate.search(queryBuilder.build(), Seller.class);

        long totalHits = sellers.getTotalHits();
        int totalPages = (int) ((totalHits + page.getPageSize() - 1) / page.getPageSize());
        List<SellerDto> sellerDTOs = mapToDTOs(sellers);

        return new SellerPageableResponse(new PageMeta(totalPages, totalHits), sellerDTOs);
    }

    private List<SellerDto> mapToDTOs(SearchHits<Seller> sellers) {
        return sellerMapper.mapToDTOs(sellers.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList()));
    }

    private NativeSearchQueryBuilder buildSearchQuery(SellerFilter filter, PageInput page, SellerSortBy sortBy) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        if (filter.getSearchByName() != null) {
            queryBuilder.withQuery(QueryBuilders.matchQuery("sellerInfo.name", filter.getSearchByName()));
        }
        if (filter.getProducerIds() != null && !filter.getProducerIds().isEmpty()) {

            queryBuilder.withQuery(
                    QueryBuilders.nestedQuery(
                            "producers",
                            QueryBuilders.termsQuery("producers.id", filter.getProducerIds()),
                            ScoreMode.None
                    )
            );
        }
        if (filter.getMarketplaceIds() != null && !filter.getMarketplaceIds().isEmpty()) {
            queryBuilder.withQuery(QueryBuilders.nestedQuery(
                    "sellerInfo",
                    QueryBuilders.nestedQuery(
                            "sellerInfo.marketplace",
                            QueryBuilders.termsQuery("sellerInfo.marketplace.id", filter.getMarketplaceIds()),
                            ScoreMode.None), ScoreMode.None
            ));
        }

        if (sortBy != null) {
            queryBuilder.withSort(getSort(sortBy));
        }

        queryBuilder.withPageable(PageRequest.of(page.getPage(), page.getPageSize()));
        return queryBuilder;
    }

    private SortBuilder<?> getSort(SellerSortBy sortBy) {
        switch (sortBy) {
            case SELLER_INFO_EXTERNAL_ID_ASC:
                return SortBuilders.fieldSort("sellerInfo.externalId").order(SortOrder.ASC);
            case SELLER_INFO_EXTERNAL_ID_DESC:
                return SortBuilders.fieldSort("sellerInfo.externalId").order(SortOrder.DESC);
            case NAME_ASC:
                return SortBuilders.fieldSort("sellerInfo.name").order(SortOrder.ASC);
            case NAME_DESC:
                return SortBuilders.fieldSort("sellerInfo.name").order(SortOrder.DESC);
            case MARKETPLACE_ID_ASC:
                return SortBuilders.fieldSort("sellerInfo.marketplace.id").order(SortOrder.ASC);
            case MARKETPLACE_ID_DESC:
                return SortBuilders.fieldSort("sellerInfo.marketplace.id").order(SortOrder.DESC);
            default:
                throw new IllegalArgumentException("Invalid sorting criteria: " + sortBy);
        }
    }

}
