package com.miteno.com.service.impl;

import com.miteno.com.service.SirqulAccountService;
import com.miteno.com.domain.SirqulAccount;
import com.miteno.com.repository.SirqulAccountRepository;
import com.miteno.com.repository.search.SirqulAccountSearchRepository;
import com.miteno.com.service.dto.SirqulAccountDTO;
import com.miteno.com.service.mapper.SirqulAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SirqulAccount.
 */
@Service
@Transactional
public class SirqulAccountServiceImpl implements SirqulAccountService{

    private final Logger log = LoggerFactory.getLogger(SirqulAccountServiceImpl.class);
    
    private final SirqulAccountRepository sirqulAccountRepository;

    private final SirqulAccountMapper sirqulAccountMapper;

    private final SirqulAccountSearchRepository sirqulAccountSearchRepository;

    public SirqulAccountServiceImpl(SirqulAccountRepository sirqulAccountRepository, SirqulAccountMapper sirqulAccountMapper, SirqulAccountSearchRepository sirqulAccountSearchRepository) {
        this.sirqulAccountRepository = sirqulAccountRepository;
        this.sirqulAccountMapper = sirqulAccountMapper;
        this.sirqulAccountSearchRepository = sirqulAccountSearchRepository;
    }

    /**
     * Save a sirqulAccount.
     *
     * @param sirqulAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SirqulAccountDTO save(SirqulAccountDTO sirqulAccountDTO) {
        log.debug("Request to save SirqulAccount : {}", sirqulAccountDTO);
        SirqulAccount sirqulAccount = sirqulAccountMapper.sirqulAccountDTOToSirqulAccount(sirqulAccountDTO);
        sirqulAccount = sirqulAccountRepository.save(sirqulAccount);
        SirqulAccountDTO result = sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(sirqulAccount);
        sirqulAccountSearchRepository.save(sirqulAccount);
        return result;
    }

    /**
     *  Get all the sirqulAccounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SirqulAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SirqulAccounts");
        Page<SirqulAccount> result = sirqulAccountRepository.findAll(pageable);
        return result.map(sirqulAccount -> sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(sirqulAccount));
    }

    /**
     *  Get one sirqulAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SirqulAccountDTO findOne(Long id) {
        log.debug("Request to get SirqulAccount : {}", id);
        SirqulAccount sirqulAccount = sirqulAccountRepository.findOne(id);
        SirqulAccountDTO sirqulAccountDTO = sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(sirqulAccount);
        return sirqulAccountDTO;
    }

    /**
     *  Delete the  sirqulAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SirqulAccount : {}", id);
        sirqulAccountRepository.delete(id);
        sirqulAccountSearchRepository.delete(id);
    }

    /**
     * Search for the sirqulAccount corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SirqulAccountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SirqulAccounts for query {}", query);
        Page<SirqulAccount> result = sirqulAccountSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(sirqulAccount -> sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(sirqulAccount));
    }
}
