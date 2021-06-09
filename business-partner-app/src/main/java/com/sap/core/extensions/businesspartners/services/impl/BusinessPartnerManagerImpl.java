package com.sap.core.extensions.businesspartners.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.core.extensions.businesspartners.destination.DestinationProvider;
import com.sap.core.extensions.businesspartners.dto.BusinessPartnerDTO;
import com.sap.core.extensions.businesspartners.dto.CreateBusinessPartnerDTO;
import com.sap.core.extensions.businesspartners.services.BusinessPartnerManager;

@Component
public class BusinessPartnerManagerImpl implements BusinessPartnerManager {

	private static final String BUSINESS_PARTNERS_CATEGORY = "1";
	private final BusinessPartnerService businessPartnerService;
	private final DestinationProvider destinationProvider;

	@Autowired
	public BusinessPartnerManagerImpl(BusinessPartnerService businessPartnerService,
			DestinationProvider destinationProvider) {
		this.businessPartnerService = businessPartnerService;
		this.destinationProvider = destinationProvider;
	}

	@Override
	public List<BusinessPartnerDTO> getAllBusinessPartners() {
		HttpDestination destination = destinationProvider.getDestination();
		return businessPartnerService //
				.getAllBusinessPartner() //
				.select(BusinessPartner.BUSINESS_PARTNER, //
						BusinessPartner.LAST_NAME, //
						BusinessPartner.FIRST_NAME, //
						BusinessPartner.CREATION_DATE) //
				.filter(BusinessPartner.LAST_NAME.ne("")) //
				.orderBy(BusinessPartner.CREATION_DATE, Order.DESC) //
				.executeRequest(destination) //
				.stream() //
				.map(this::toDTO) //
				.collect(Collectors.toList()); //
	}

	@Override
	public BusinessPartnerDTO createBusinessPartner(CreateBusinessPartnerDTO businessPartnerDto) {
		HttpDestination destination = destinationProvider.getDestination();
		BusinessPartner businessPartner = fromDTO(businessPartnerDto);

		BusinessPartner modifiedBusinessPartner = businessPartnerService //
				.createBusinessPartner(businessPartner) //
				.executeRequest(destination).getModifiedEntity(); //

		return toDTO(modifiedBusinessPartner);

	}

	private BusinessPartner fromDTO(CreateBusinessPartnerDTO businessPartnerDto) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(businessPartnerDto.getFirstName());
		businessPartner.setLastName(businessPartnerDto.getLastName());
		businessPartner.setBusinessPartnerCategory(BUSINESS_PARTNERS_CATEGORY);
		return businessPartner;
	}

	private BusinessPartnerDTO toDTO(BusinessPartner businessPartner) {
		BusinessPartnerDTO businessPartnerDto = new BusinessPartnerDTO();
		businessPartnerDto.setFirstName(businessPartner.getFirstName());
		businessPartnerDto.setLastName(businessPartner.getLastName());
		businessPartnerDto.setCreationDate(businessPartner.getCreationDate());
		businessPartnerDto.setId(businessPartner.getBusinessPartner());
		return businessPartnerDto;
	}

}
