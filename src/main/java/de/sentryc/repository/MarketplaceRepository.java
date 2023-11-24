package de.sentryc.repository;

import de.sentryc.domain.Marketplace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends ElasticsearchRepository<Marketplace, String> {

}