package com.sap.core.extensions.businesspartners.services;

import java.util.List;

import com.sap.core.extensions.businesspartners.dto.BusinessPartnerDTO;
import com.sap.core.extensions.businesspartners.dto.CreateBusinessPartnerDTO;

public interface BusinessPartnerManager {

	List<BusinessPartnerDTO> getAllBusinessPartners();

	BusinessPartnerDTO createBusinessPartner(CreateBusinessPartnerDTO businessPartner);

}
