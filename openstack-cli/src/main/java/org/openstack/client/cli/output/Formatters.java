package org.openstack.client.cli.output;

import java.text.NumberFormat;
import java.util.Currency;

public class Formatters {

	public static String formatPrice(Float v) {
		if (v == null) {
			return "";
		}

		final NumberFormat currencyInstance = NumberFormat
				.getCurrencyInstance();
		currencyInstance.setCurrency(Currency.getInstance("USD"));

		String s = currencyInstance.format(v);
		return s;
	}

}
