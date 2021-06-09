sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageBox",
	"sap/ui/model/json/JSONModel",
	"ext/samples/businesspartner/util/Config",
], function (Controller, MessageBox, JSONModel, Config) {
	"use strict";

	return Controller.extend("ext.samples.businesspartner.controller.AddPartner", {

		onInit: function () {
			this.getView().setModel(new JSONModel(), "addPartner");
			this.byId("addPartnerDialog").setBusyIndicatorDelay(0);
		},

		resetModel: function () {
			let model = this.getView().getModel("addPartner");
			model.setData({});
		},

		onSubmitPartner: function () {
			let model = this.getView().getModel("addPartner");

			function handlePartnerAddedSuccess(data, textStatus, jqXHR) {
				if (jqXHR.status !== 201) {
					this.showErrorFn("cannotAddBusinessPartnersMessage").bind(this)();
					return;
				}

				sap.ui.getCore().getEventBus().publish("ext.samples.businesspartner", "PartnerAdded", data);
			}

			jQuery.ajax({
				method: "POST",
				url: Config.serviceUrl + "/businessPartners",
				contentType: Config.jsonMediaType,
				context: this,
				beforeSend: this.showBusyFn("addPartnerDialog"),
				data: JSON.stringify({
					firstName: model.getProperty("/firstName"),
					lastName: model.getProperty("/lastName")
				})
			}).done(handlePartnerAddedSuccess)
				.fail(this.showErrorFn("cannotAddBusinessPartnersMessage"))
				.always(this.hideBusyFn("addPartnerDialog"), this.onCancel);
		},

		onCancel: function () {
			let dialog = this.byId("addPartnerDialog");
			dialog.close();
		},

		formatSubmitEnabled: function (firstName, lastName) {
			return Boolean(firstName) && Boolean(lastName);
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
		}

	});

});
