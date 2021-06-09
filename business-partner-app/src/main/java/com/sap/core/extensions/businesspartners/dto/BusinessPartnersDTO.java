package com.sap.core.extensions.businesspartners.dto;

import java.util.List;

public class BusinessPartnersDTO {

	List<BusinessPartnerDTO> businessPartners;

	public BusinessPartnersDTO(List<BusinessPartnerDTO> businessPartners) {
		this.businessPartners = businessPartners;
	}

	public List<BusinessPartnerDTO> getBusinessPartners() {
		return businessPartners;
	}

	public void setBusinessPartners(List<BusinessPartnerDTO> businessPartners) {
		this.businessPartners = businessPartners;
	}

}
