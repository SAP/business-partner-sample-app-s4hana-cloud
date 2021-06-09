sap.ui.define([], function () {
	"use strict";

	return {
		serviceUrl: "v1",
		highContrastTheme: "sap_belize_hcb",
		jsonMediaType: "application/json; charset=utf-8",
		standardTheme: sap.ui.getCore().getConfiguration().getTheme(),

		initApp: function (elementId) {

			new sap.m.Shell({
				showLogout: false,
				app: new sap.ui.core.ComponentContainer({
					name: 'ext.samples.businesspartner'
				})
			}).placeAt(elementId);
		}

	};
});