package com.miteno.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miteno.com.service.SirqulAccountService;
import com.miteno.com.web.rest.util.HeaderUtil;
import com.miteno.com.web.rest.util.PaginationUtil;
import com.miteno.com.service.dto.SirqulAccountDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SirqulAccount.
 */
@RestController
@RequestMapping("/api")
public class SirqulAccountResource {

    private final Logger log = LoggerFactory.getLogger(SirqulAccountResource.class);

    private static final String ENTITY_NAME = "sirqulAccount";
        
    private final SirqulAccountService sirqulAccountService;

    public SirqulAccountResource(SirqulAccountService sirqulAccountService) {
        this.sirqulAccountService = sirqulAccountService;
    }

    /**
     * POST  /sirqul-accounts : Create a new sirqulAccount.
     *
     * @param sirqulAccountDTO the sirqulAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sirqulAccountDTO, or with status 400 (Bad Request) if the sirqulAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sirqul-accounts")
    @Timed
    public ResponseEntity<SirqulAccountDTO> createSirqulAccount(@Valid @RequestBody SirqulAccountDTO sirqulAccountDTO) throws URISyntaxException {
        log.debug("REST request to save SirqulAccount : {}", sirqulAccountDTO);
        if (sirqulAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sirqulAccount cannot already have an ID")).body(null);
        }
        SirqulAccountDTO result = sirqulAccountService.save(sirqulAccountDTO);
        return ResponseEntity.created(new URI("/api/sirqul-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sirqul-accounts : Updates an existing sirqulAccount.
     *
     * @param sirqulAccountDTO the sirqulAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sirqulAccountDTO,
     * or with status 400 (Bad Request) if the sirqulAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the sirqulAccountDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sirqul-accounts")
    @Timed
    public ResponseEntity<SirqulAccountDTO> updateSirqulAccount(@Valid @RequestBody SirqulAccountDTO sirqulAccountDTO) throws URISyntaxException {
        log.debug("REST request to update SirqulAccount : {}", sirqulAccountDTO);
        if (sirqulAccountDTO.getId() == null) {
            return createSirqulAccount(sirqulAccountDTO);
        }
        SirqulAccountDTO result = sirqulAccountService.save(sirqulAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sirqulAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sirqul-accounts : get all the sirqulAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sirqulAccounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sirqul-accounts")
    @Timed
    public ResponseEntity<List<SirqulAccountDTO>> getAllSirqulAccounts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SirqulAccounts");
        Page<SirqulAccountDTO> page = sirqulAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sirqul-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sirqul-accounts/:id : get the "id" sirqulAccount.
     *
     * @param id the id of the sirqulAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sirqulAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sirqul-accounts/{id}")
    @Timed
    public ResponseEntity<SirqulAccountDTO> getSirqulAccount(@PathVariable Long id) {
        log.debug("REST request to get SirqulAccount : {}", id);
        SirqulAccountDTO sirqulAccountDTO = sirqulAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sirqulAccountDTO));
    }

    /**
     * DELETE  /sirqul-accounts/:id : delete the "id" sirqulAccount.
     *
     * @param id the id of the sirqulAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sirqul-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSirqulAccount(@PathVariable Long id) {
        log.debug("REST request to delete SirqulAccount : {}", id);
        sirqulAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sirqul-accounts?query=:query : search for the sirqulAccount corresponding
     * to the query.
     *
     * @param query the query of the sirqulAccount search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sirqul-accounts")
    @Timed
    public ResponseEntity<List<SirqulAccountDTO>> searchSirqulAccounts(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SirqulAccounts for query {}", query);
        Page<SirqulAccountDTO> page = sirqulAccountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sirqul-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
