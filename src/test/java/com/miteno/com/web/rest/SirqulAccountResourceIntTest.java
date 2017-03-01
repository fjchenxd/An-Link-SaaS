package com.miteno.com.web.rest;

import com.miteno.com.SaasApp;

import com.miteno.com.domain.SirqulAccount;
import com.miteno.com.repository.SirqulAccountRepository;
import com.miteno.com.service.SirqulAccountService;
import com.miteno.com.repository.search.SirqulAccountSearchRepository;
import com.miteno.com.service.dto.SirqulAccountDTO;
import com.miteno.com.service.mapper.SirqulAccountMapper;
import com.miteno.com.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SirqulAccountResource REST controller.
 *
 * @see SirqulAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaasApp.class)
public class SirqulAccountResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private SirqulAccountRepository sirqulAccountRepository;

    @Autowired
    private SirqulAccountMapper sirqulAccountMapper;

    @Autowired
    private SirqulAccountService sirqulAccountService;

    @Autowired
    private SirqulAccountSearchRepository sirqulAccountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSirqulAccountMockMvc;

    private SirqulAccount sirqulAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SirqulAccountResource sirqulAccountResource = new SirqulAccountResource(sirqulAccountService);
        this.restSirqulAccountMockMvc = MockMvcBuilders.standaloneSetup(sirqulAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SirqulAccount createEntity(EntityManager em) {
        SirqulAccount sirqulAccount = new SirqulAccount()
                .name(DEFAULT_NAME)
                .username(DEFAULT_USERNAME)
                .password(DEFAULT_PASSWORD);
        return sirqulAccount;
    }

    @Before
    public void initTest() {
        sirqulAccountSearchRepository.deleteAll();
        sirqulAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSirqulAccount() throws Exception {
        int databaseSizeBeforeCreate = sirqulAccountRepository.findAll().size();

        // Create the SirqulAccount
        SirqulAccountDTO sirqulAccountDTO = sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(sirqulAccount);

        restSirqulAccountMockMvc.perform(post("/api/sirqul-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirqulAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the SirqulAccount in the database
        List<SirqulAccount> sirqulAccountList = sirqulAccountRepository.findAll();
        assertThat(sirqulAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SirqulAccount testSirqulAccount = sirqulAccountList.get(sirqulAccountList.size() - 1);
        assertThat(testSirqulAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSirqulAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSirqulAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);

        // Validate the SirqulAccount in Elasticsearch
        SirqulAccount sirqulAccountEs = sirqulAccountSearchRepository.findOne(testSirqulAccount.getId());
        assertThat(sirqulAccountEs).isEqualToComparingFieldByField(testSirqulAccount);
    }

    @Test
    @Transactional
    public void createSirqulAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sirqulAccountRepository.findAll().size();

        // Create the SirqulAccount with an existing ID
        SirqulAccount existingSirqulAccount = new SirqulAccount();
        existingSirqulAccount.setId(1L);
        SirqulAccountDTO existingSirqulAccountDTO = sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(existingSirqulAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSirqulAccountMockMvc.perform(post("/api/sirqul-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSirqulAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SirqulAccount> sirqulAccountList = sirqulAccountRepository.findAll();
        assertThat(sirqulAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSirqulAccounts() throws Exception {
        // Initialize the database
        sirqulAccountRepository.saveAndFlush(sirqulAccount);

        // Get all the sirqulAccountList
        restSirqulAccountMockMvc.perform(get("/api/sirqul-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sirqulAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getSirqulAccount() throws Exception {
        // Initialize the database
        sirqulAccountRepository.saveAndFlush(sirqulAccount);

        // Get the sirqulAccount
        restSirqulAccountMockMvc.perform(get("/api/sirqul-accounts/{id}", sirqulAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sirqulAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSirqulAccount() throws Exception {
        // Get the sirqulAccount
        restSirqulAccountMockMvc.perform(get("/api/sirqul-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSirqulAccount() throws Exception {
        // Initialize the database
        sirqulAccountRepository.saveAndFlush(sirqulAccount);
        sirqulAccountSearchRepository.save(sirqulAccount);
        int databaseSizeBeforeUpdate = sirqulAccountRepository.findAll().size();

        // Update the sirqulAccount
        SirqulAccount updatedSirqulAccount = sirqulAccountRepository.findOne(sirqulAccount.getId());
        updatedSirqulAccount
                .name(UPDATED_NAME)
                .username(UPDATED_USERNAME)
                .password(UPDATED_PASSWORD);
        SirqulAccountDTO sirqulAccountDTO = sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(updatedSirqulAccount);

        restSirqulAccountMockMvc.perform(put("/api/sirqul-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirqulAccountDTO)))
            .andExpect(status().isOk());

        // Validate the SirqulAccount in the database
        List<SirqulAccount> sirqulAccountList = sirqulAccountRepository.findAll();
        assertThat(sirqulAccountList).hasSize(databaseSizeBeforeUpdate);
        SirqulAccount testSirqulAccount = sirqulAccountList.get(sirqulAccountList.size() - 1);
        assertThat(testSirqulAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSirqulAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSirqulAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);

        // Validate the SirqulAccount in Elasticsearch
        SirqulAccount sirqulAccountEs = sirqulAccountSearchRepository.findOne(testSirqulAccount.getId());
        assertThat(sirqulAccountEs).isEqualToComparingFieldByField(testSirqulAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingSirqulAccount() throws Exception {
        int databaseSizeBeforeUpdate = sirqulAccountRepository.findAll().size();

        // Create the SirqulAccount
        SirqulAccountDTO sirqulAccountDTO = sirqulAccountMapper.sirqulAccountToSirqulAccountDTO(sirqulAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSirqulAccountMockMvc.perform(put("/api/sirqul-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sirqulAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the SirqulAccount in the database
        List<SirqulAccount> sirqulAccountList = sirqulAccountRepository.findAll();
        assertThat(sirqulAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSirqulAccount() throws Exception {
        // Initialize the database
        sirqulAccountRepository.saveAndFlush(sirqulAccount);
        sirqulAccountSearchRepository.save(sirqulAccount);
        int databaseSizeBeforeDelete = sirqulAccountRepository.findAll().size();

        // Get the sirqulAccount
        restSirqulAccountMockMvc.perform(delete("/api/sirqul-accounts/{id}", sirqulAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sirqulAccountExistsInEs = sirqulAccountSearchRepository.exists(sirqulAccount.getId());
        assertThat(sirqulAccountExistsInEs).isFalse();

        // Validate the database is empty
        List<SirqulAccount> sirqulAccountList = sirqulAccountRepository.findAll();
        assertThat(sirqulAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSirqulAccount() throws Exception {
        // Initialize the database
        sirqulAccountRepository.saveAndFlush(sirqulAccount);
        sirqulAccountSearchRepository.save(sirqulAccount);

        // Search the sirqulAccount
        restSirqulAccountMockMvc.perform(get("/api/_search/sirqul-accounts?query=id:" + sirqulAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sirqulAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SirqulAccount.class);
    }
}
