package de.sentryc.repository;

import de.sentryc.domain.SellerInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;
public interface SellerInfoRepository extends ElasticsearchRepository<SellerInfo, UUID> {

}
