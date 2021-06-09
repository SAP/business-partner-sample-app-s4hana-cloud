package com.sap.core.extensions.businesspartners.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.core.extensions.businesspartners.dto.BusinessPartnerDTO;
import com.sap.core.extensions.businesspartners.dto.BusinessPartnersDTO;
import com.sap.core.extensions.businesspartners.dto.CreateBusinessPartnerDTO;
import com.sap.core.extensions.businesspartners.services.BusinessPartnerManager;

@RestController
@RequestMapping("/v1/businessPartners")
class BusinessPartnerController {
	private final BusinessPartnerManager businessPartnerManager;

	@Autowired
	BusinessPartnerController(BusinessPartnerManager businessPartnerManager) {
		this.businessPartnerManager = businessPartnerManager;
	}

	@GetMapping
	public ResponseEntity<BusinessPartnersDTO> getBusinessPartners() {
		final List<BusinessPartnerDTO> businessPartners = businessPartnerManager.getAllBusinessPartners();

		return ResponseEntity.ok(new BusinessPartnersDTO(businessPartners));
	}

	@PostMapping
	public ResponseEntity<BusinessPartnerDTO> createBusinesPartner(
			@RequestBody CreateBusinessPartnerDTO businessPartner) {
		BusinessPartnerDTO modifiedBusinessPartner = businessPartnerManager.createBusinessPartner(businessPartner);

		return ResponseEntity.status(HttpStatus.CREATED).body(modifiedBusinessPartner);
	}

}
