package com.miteno.com.repository.search;

import com.miteno.com.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Account entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
