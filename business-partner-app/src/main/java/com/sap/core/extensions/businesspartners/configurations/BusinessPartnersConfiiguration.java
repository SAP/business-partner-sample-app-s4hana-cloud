package com.sap.core.extensions.businesspartners.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationLoader;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfDestinationLoader;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

@Configuration
class BusinessPartnersConfiiguration {

	@Bean
	BusinessPartnerService getBusinessPartnerService() {
		return new DefaultBusinessPartnerService();
	}

	@Bean
	DestinationLoader getDestinationLoader() {
		return new ScpCfDestinationLoader();
	}
}
