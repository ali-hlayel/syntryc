package de.sentryc.db;

import de.sentryc.domain.*;
import de.sentryc.repository.MarketplaceRepository;
import de.sentryc.repository.ProducerRepository;
import de.sentryc.repository.SellerInfoRepository;
import de.sentryc.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DataInitializationService {

    private final ProducerRepository producerRepository;
    private final MarketplaceRepository marketplaceRepository;
    private final SellerInfoRepository sellerInfoRepository;
    private final SellerRepository sellerRepository;

    @PostConstruct
    public void initData() {
        // Generate and save 10 producer entries (companies)
        for (int i = 0; i < 10; i++) {
            Producer producer = new Producer();
            producer.setName("Company" + i);
            producer.setId(UUID.randomUUID());
            producerRepository.save(producer);
        }

        // Generate and save 10 marketplace entries (countries)
        for (int i = 0; i < 10; i++) {
            Marketplace marketplace = new Marketplace();
            marketplace.setDescription("Description");
            marketplace.setId("MarkerPlaceId_" + i);
            marketplaceRepository.save(marketplace);
        }

        // Generate and save 1000 seller info entries
        for (int i = 0; i < 1000; i++) {
            SellerInfo sellerInfo = new SellerInfo();
            sellerInfo.setName("Seller_" + i%50);
            sellerInfo.setId(UUID.randomUUID());
            sellerInfo.setMarketplace(getRandomMarketplace());
            sellerInfo.setExternalId("ID_" + i%30);
            sellerInfo.setCountry("Country" + i);
            sellerInfo.setUrl("https://www.example.com/seller" + i);
            sellerInfoRepository.save(sellerInfo);

            // Get a random producer and marketplace

            // Create and save sellers for each seller info entry
            Seller seller = new Seller();
            seller.setSellerState(SellerState.valueOf(getRandomState()));
            seller.setSellerInfo(sellerInfo);
            seller.setId(UUID.randomUUID());
            seller.getProducers().add(getRandomProducer());
            sellerRepository.save(seller);
        }
    }

    // Helper method to get a random state
    private String getRandomState() {
        String[] states = { "REGULAR", "WHITELISTED", "GREYLISTED", "BLACKLISTED"};
        Random random = new Random();
        return states[random.nextInt(states.length)];
    }

    private Producer getRandomProducer() {
        List<Producer> allProducers = StreamSupport
                .stream(producerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        if (allProducers.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(allProducers.size());
        return allProducers.get(randomIndex);
    }

    private Marketplace getRandomMarketplace() {
        List<Marketplace> allMarketplaces = StreamSupport
                .stream(marketplaceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        if (allMarketplaces.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(allMarketplaces.size());
        return allMarketplaces.get(randomIndex);
    }

}
