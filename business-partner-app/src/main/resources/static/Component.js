sap.ui.define([
	"sap/ui/core/UIComponent",
	"ext/samples/businesspartner/util/Config",
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device",
	"sap/ui/model/resource/ResourceModel"
], function (UIComponent, Config, JSONModel, Device, ResourceModel) {
	"use strict";

	return UIComponent.extend("ext.samples.businesspartner.Component", {

		metadata: {
			manifest: "json"
		},

		init: function () {
			UIComponent.prototype.init.apply(this, arguments);

			this.setI18nModel();
			this.setDocumentTitle();
		},

		setI18nModel: function () {
			let i18nModel = new ResourceModel({ bundleUrl: "i18n/message-bundle.properties" })
			this.setModel(i18nModel, "i18n");
		},

		setDocumentTitle: function () {
			let messages = this.getModel("i18n").getResourceBundle();
			document.title = messages.getText("appTitle");
		}

	});

});
