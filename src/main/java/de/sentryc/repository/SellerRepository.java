package de.sentryc.repository;

import de.sentryc.domain.Seller;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface SellerRepository extends ElasticsearchRepository<Seller, UUID> {
}
