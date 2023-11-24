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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@NonTransactionalIntegrationTest
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
    void testGetSellers_withoutFilter() {

        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(1, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;
        SellerPageableResponse response = sellerService.getSellers(filter, page, sortBy);

        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertNotNull(response.getData());
    }

    @Test
    void testGetSellers_withSearchByName() {
        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(1, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;
        filter.setSearchByName("SellerTEST_1");
        SellerPageableResponse response_bySearch = sellerService.getSellers(filter, page, sortBy);
        assertNotNull(response_bySearch);
        assertNotNull(response_bySearch.getMeta());
        assertNotNull(response_bySearch.getData());
        Assertions.assertEquals(response_bySearch.getMeta().getTotalItems(), 2);
    }

    @Test
    void testGetSellers_withFilterByMarketPlaceId() {
        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(1, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;
        filter.setMarketplaceIds(List.of("1"));
        SellerPageableResponse response_bySearch = sellerService.getSellers(filter, page, sortBy);
        assertNotNull(response_bySearch);
        assertNotNull(response_bySearch.getMeta());
        assertNotNull(response_bySearch.getData());
        Assertions.assertEquals(response_bySearch.getMeta().getTotalItems(), 2);
    }

    @Test
    void testGetSellers_withFilterByExternalId() {
        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(1, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;
        filter.setProducerIds(List.of("IDTEST_1"));
        SellerPageableResponse response_bySearch = sellerService.getSellers(filter, page, sortBy);
        assertNotNull(response_bySearch);
        assertNotNull(response_bySearch.getMeta());
        assertNotNull(response_bySearch.getData());
        Assertions.assertEquals(response_bySearch.getMeta().getTotalItems(), 2);
    }

    /**
     * add 10 Sellers with 10 SellerInfo
     */
    @BeforeEach
    public void setUp() {
        Producer producer_1 = new Producer();
        producer_1.setName("Company_A");
        producer_1.setId(UUID.randomUUID());
        producerRepository.save(producer_1);


        Producer producer_2 = new Producer();
        producer_2.setName("Company_2");
        producer_2.setId(UUID.randomUUID());
        producerRepository.save(producer_2);

        Marketplace marketplace_1 = new Marketplace();
        marketplace_1.setDescription("DescriptionTEST_1");
        marketplace_1.setId(String.valueOf(1));
        marketplaceRepository.save(marketplace_1);


        Marketplace marketplace_2 = new Marketplace();
        marketplace_2.setDescription("DescriptionTEST_2");
        marketplace_2.setId(String.valueOf(2));
        marketplaceRepository.save(marketplace_2);

        for (int i = 0; i < 5; i++) {
            SellerInfo sellerInfo = new SellerInfo();
            sellerInfo.setName("SellerTEST_" + i);
            sellerInfo.setId(UUID.randomUUID());
            sellerInfo.setMarketplace(marketplace_1);
            sellerInfo.setExternalId("IDTEST_" + i);
            sellerInfo.setCountry("CountryTEST_" + i);
            sellerInfo.setUrl("https://www.example.com/sellerTEST" + i);
            sellerInfoRepository.save(sellerInfo);

            Seller seller = new Seller();
            seller.setSellerState(SellerState.valueOf(getRandomState()));
            seller.setSellerInfo(sellerInfo);
            seller.setId(UUID.randomUUID());
            seller.getProducers().add(producer_1);
            sellerRepository.save(seller);
        }

        for (int i = 0; i < 5; i++) {
            SellerInfo sellerInfo = new SellerInfo();
            sellerInfo.setName("SellerTEST_" + i);
            sellerInfo.setId(UUID.randomUUID());
            sellerInfo.setMarketplace(marketplace_2);
            sellerInfo.setExternalId("IDTEST_" + i);
            sellerInfo.setCountry("CountryTEST_" + i);
            sellerInfo.setUrl("https://www.example.com/sellerTEST" + i);
            sellerInfoRepository.save(sellerInfo);

            Seller seller = new Seller();
            seller.setSellerState(SellerState.valueOf(getRandomState()));
            seller.setSellerInfo(sellerInfo);
            seller.setId(UUID.randomUUID());
            seller.getProducers().add(producer_2);
            sellerRepository.save(seller);
        }
    }

    private String getRandomState() {
        String[] states = {"REGULAR", "WHITELISTED", "GREYLISTED", "BLACKLISTED"};
        Random random = new Random();
        return states[random.nextInt(states.length)];
    }

    public void cleanUp() {
        sellerRepository.deleteAll();
        sellerInfoRepository.deleteAll();
        producerRepository.deleteAll();
        marketplaceRepository.deleteAll();
    }
}
