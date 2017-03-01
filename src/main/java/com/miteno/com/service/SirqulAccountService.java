package com.miteno.com.service;

import com.miteno.com.service.dto.SirqulAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing SirqulAccount.
 */
public interface SirqulAccountService {

    /**
     * Save a sirqulAccount.
     *
     * @param sirqulAccountDTO the entity to save
     * @return the persisted entity
     */
    SirqulAccountDTO save(SirqulAccountDTO sirqulAccountDTO);

    /**
     *  Get all the sirqulAccounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SirqulAccountDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" sirqulAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SirqulAccountDTO findOne(Long id);

    /**
     *  Delete the "id" sirqulAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sirqulAccount corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SirqulAccountDTO> search(String query, Pageable pageable);
}
