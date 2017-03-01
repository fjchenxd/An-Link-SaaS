package com.miteno.com.service.mapper;

import com.miteno.com.domain.*;
import com.miteno.com.service.dto.SirqulAccountDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SirqulAccount and its DTO SirqulAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SirqulAccountMapper {

    SirqulAccountDTO sirqulAccountToSirqulAccountDTO(SirqulAccount sirqulAccount);

    List<SirqulAccountDTO> sirqulAccountsToSirqulAccountDTOs(List<SirqulAccount> sirqulAccounts);

    SirqulAccount sirqulAccountDTOToSirqulAccount(SirqulAccountDTO sirqulAccountDTO);

    List<SirqulAccount> sirqulAccountDTOsToSirqulAccounts(List<SirqulAccountDTO> sirqulAccountDTOs);
}
