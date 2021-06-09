sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/format/DateFormat",
	"sap/m/MessageBox",
	"sap/ui/model/json/JSONModel",
	"ext/samples/businesspartner/util/Config",
], function (Controller, DateFormat, MessageBox, JSONModel, Config) {
	"use strict";

	return Controller.extend("ext.samples.businesspartner.controller.App", {

		dateFormatter: DateFormat.getDateInstance(),

		onInit: function () {
			sap.ui
				.getCore()
				.getEventBus()
				.subscribe(
					"ext.samples.businesspartner", "PartnerAdded",
					function (sGroup, sEvent, newPartner) {
						let model = this.getView().getModel("partners");
						let partners = model.getProperty("/businessPartners");
						partners.unshift(newPartner);
						model.setProperty("/businessPartners", partners);
					},
					this
				);

			sap.ui
				.getCore()
				.getEventBus()
				.subscribe(
					"ext.samples.businesspartner", "PartnerRemoved",
					function (sGroup, sEvent, removedPartner) {
						let model = this.getView().getModel("partners");
						let partners = model.getProperty("/businessPartners");
						let filteredPartners = partners.filter((partner) => partner.id !== removedPartner.id);
						model.setProperty("/businessPartners", filteredPartners);
					},
					this
				);


				
			function setRequestsModel(data) {
				this.getView().setModel(new JSONModel(data), "partners");
				
			};
			
			jQuery.ajax({
				method: "GET",
				url: Config.serviceUrl + "/businessPartners",
				context: this,
				beforeSend: this.showBusyFn("table")
			}).done(setRequestsModel)
				.fail(this.showErrorFn("cannotLoadBusinessPartnersMessage"))
				.always(this.hideBusyFn("table"));

				
			function setUserModel(data) {
				this.getView().setModel(new JSONModel(data), "user");
			};
				
			jQuery.ajax({
				method: "GET",
				url: Config.serviceUrl + "/users/current",
				context: this,
				beforeSend: this.showBusyFn("profile")
			}).done(setUserModel)
				.fail(this.showErrorFn("cannotLoadCurrentUserDetailsMessage"))
				.always(this.hideBusyFn("profile"));

		},

		onAfterRendering: function () {
			this.byId("table").focus();
		},

		onAddPartner: function () {
			let dialog = this.byId("addPartnerView").byId("addPartnerDialog");
			dialog.open();
		},

		formatNames: function (firstName, lastName) {
			let messages = this.getView().getModel("i18n").getResourceBundle();

			return (firstName && lastName) ? messages.getText("firstAndLastName", [firstName, lastName]) : (firstName || lastName);
		},
		
		namesVisible: function (firstName, lastName) {
			return Boolean(firstName || lastName);
		},
		
		formatInitials: function (firstName, lastName) {
			let firstNameInitial = firstName && firstName.charAt(0);
			let lastNameInitial = lastName && lastName.charAt(0);
			
			let messages = this.getView().getModel("i18n").getResourceBundle();

			return (firstNameInitial && lastNameInitial) ? messages.getText("firstAndLastInitials", [firstNameInitial, lastNameInitial]) : (firstNameInitial || lastNameInitial);
		},

		formatDate: function (date) {
			return this.dateFormatter.format(new Date(date));
		},

		showBusyFn: function (controlId) {
			return function (jqXHR, settings) {
				this.byId(controlId).setBusy(true);
			};
		},

		hideBusyFn: function (controlId) {
			return function () {
				this.byId(controlId).setBusy(false);
			};
		},

		showErrorFn: function (messageKey) {
			return function () {
				let messages = this.getView().getModel("i18n").getResourceBundle();
				MessageBox.error(messages.getText(messageKey), { styleClass: "sapUiSizeCompact" });
			}
		},

		onProfilePressed: function (evt) {
			let profileAvatar = evt.getSource();
			this.byId("userDetailsPopover").openBy(profileAvatar);
		}

	});

});
