package de.sentryc.repository;

import de.sentryc.domain.Producer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface ProducerRepository extends ElasticsearchRepository<Producer, UUID> {
}
