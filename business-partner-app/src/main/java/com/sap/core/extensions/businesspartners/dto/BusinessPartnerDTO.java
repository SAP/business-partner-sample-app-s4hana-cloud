package com.sap.core.extensions.businesspartners.dto;

import java.time.LocalDateTime;

public class BusinessPartnerDTO extends CreateBusinessPartnerDTO {

	private LocalDateTime creationDate;
	private String id;

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
