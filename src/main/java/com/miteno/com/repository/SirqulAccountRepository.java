package com.miteno.com.repository;

import com.miteno.com.domain.SirqulAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SirqulAccount entity.
 */
@SuppressWarnings("unused")
public interface SirqulAccountRepository extends JpaRepository<SirqulAccount,Long> {

}
