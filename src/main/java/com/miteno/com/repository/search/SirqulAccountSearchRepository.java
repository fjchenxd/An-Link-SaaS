package com.miteno.com.repository.search;

import com.miteno.com.domain.SirqulAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SirqulAccount entity.
 */
public interface SirqulAccountSearchRepository extends ElasticsearchRepository<SirqulAccount, Long> {
}
