package de.sentryc.service;

import de.sentryc.domain.*;
import de.sentryc.graphql.input.PageInput;
import de.sentryc.graphql.input.SellerFilter;
import de.sentryc.graphql.input.SellerSortBy;
import de.sentryc.graphql.ouput.SellerPageableResponse;
import de.sentryc.repository.MarketplaceRepository;
import de.sentryc.repository.ProducerRepository;
import de.sentryc.repository.SellerInfoRepository;
import de.sentryc.repository.SellerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@NonTransactionalIntegrationTest
@Transactional
public class SellerServiceImplTest {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    void testGetSellers_withFilters() {
        createSellers("TEST_1", "externalId", "Adidas", 5);
        createSellers("TEST_2", "externalId_1", "Adidas_1", 5);

        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;
        SellerPageableResponse response = sellerService.getSellers(filter, page, sortBy);

        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertNotNull(response.getData());

        SellerFilter filterBySearchByName = new SellerFilter();
        filterBySearchByName.setSearchByName("TEST_1");
        SellerPageableResponse responseBySearchByName = sellerService.getSellers(filterBySearchByName, page, sortBy);
        assertNotNull(responseBySearchByName.getMeta());
        assertNotNull(responseBySearchByName.getData());
        int totalItems = (int) responseBySearchByName.getMeta().getTotalItems();
        Assertions.assertEquals(5, totalItems);

        SellerFilter filterByMarketPlaceId = new SellerFilter();
        filterByMarketPlaceId.setMarketplaceIds(List.of("Adidas"));
        SellerPageableResponse responseByMarketPlaceId = sellerService.getSellers(filterByMarketPlaceId, page, sortBy);
        assertNotNull(responseByMarketPlaceId.getMeta());
        assertNotNull(responseByMarketPlaceId.getData());
        totalItems = (int) responseByMarketPlaceId.getMeta().getTotalItems();
        Assertions.assertEquals(5, totalItems);
    }

    @Test
    void testGetSellers_withSorting() {
        createSellers("A", "A", "A", 1);
        createSellers("Z", "Z", "Z", 1);

        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(0, 10);

        SellerSortBy sortByNameAsc = SellerSortBy.NAME_ASC;
        SellerPageableResponse responseByNameAsc = sellerService.getSellers(filter, page, sortByNameAsc);
        Assertions.assertEquals("A", responseByNameAsc.getData().get(0).getSellerName());

        SellerSortBy sortByNameDesc = SellerSortBy.NAME_DESC;
        SellerPageableResponse responseByNameDesc = sellerService.getSellers(filter, page, sortByNameDesc);
        Assertions.assertEquals("Z", responseByNameDesc.getData().get(0).getSellerName());

        SellerSortBy sortByMarkerPlaceAsc = SellerSortBy.MARKETPLACE_ID_ASC;
        SellerPageableResponse responseByMarkerPlaceAsc = sellerService.getSellers(filter, page, sortByMarkerPlaceAsc);
        Assertions.assertEquals("A", responseByMarkerPlaceAsc.getData().get(0).getMarketplaceId());

        SellerSortBy sortByMarkerPlaceDesc = SellerSortBy.MARKETPLACE_ID_DESC;
        SellerPageableResponse responseByMarkerPlaceDesc = sellerService.getSellers(filter, page, sortByMarkerPlaceDesc);
        Assertions.assertEquals("Z", responseByMarkerPlaceDesc.getData().get(0).getMarketplaceId());

        SellerSortBy sortByExternalIdAsc = SellerSortBy.SELLER_INFO_EXTERNAL_ID_ASC;
        SellerPageableResponse responseByExternalIdAsc = sellerService.getSellers(filter, page, sortByExternalIdAsc);
        Assertions.assertEquals("A", responseByExternalIdAsc.getData().get(0).getExternalId());

        SellerSortBy sortByExternalIdDesc = SellerSortBy.SELLER_INFO_EXTERNAL_ID_DESC;
        SellerPageableResponse responseByExternalIdDesc = sellerService.getSellers(filter, page, sortByExternalIdDesc);
        Assertions.assertEquals("Z", responseByExternalIdDesc.getData().get(0).getExternalId());
    }

    /**
     * add 10 Sellers with 10 SellerInfo
     */

    public void createSellers(String name, String externalId, String marketPlaceId, int ids) {
        Producer producer = new Producer();
        producer.setName("Company_A");
        producer.setId(UUID.randomUUID());
        producerRepository.save(producer);

        Marketplace marketplace = new Marketplace();
        marketplace.setDescription("Description");
        marketplace.setId(marketPlaceId);
        marketplaceRepository.save(marketplace);

        for (int i = 0; i < ids; i++) {
            SellerInfo sellerInfo = new SellerInfo();
            sellerInfo.setName(name);
            sellerInfo.setId(UUID.randomUUID());
            sellerInfo.setMarketplace(marketplace);
            sellerInfo.setExternalId(externalId);
            sellerInfo.setCountry("CountryTEST");
            sellerInfo.setUrl("https://www.example.com/sellerTEST" + i);
            sellerInfoRepository.save(sellerInfo);

            Seller seller = new Seller();
            seller.setSellerState(SellerState.valueOf("WHITELISTED"));
            seller.setSellerInfo(sellerInfo);
            seller.setId(UUID.randomUUID());
            seller.getProducers().add(producer);
            sellerRepository.save(seller);
        }

    }
}
