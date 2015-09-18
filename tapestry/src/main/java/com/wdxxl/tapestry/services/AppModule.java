package com.wdxxl.tapestry.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;

public class AppModule {
	public static void contributeApplicationDefaults(
			MappedConfiguration<String, String> configuration) {
		
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
		//configuration.add(SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS,"true");
	}
}