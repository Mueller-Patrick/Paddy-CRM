package com.p4ddy.paddycrm.adapters.account

import java.time.LocalDate

/**
 * Account Business Entity for usage in the GUI
 *
 * @property name The name of the account
 * @property ownerName The name of the record owner
 * @property ownerId The id of the record owner
 * @property billingCountry The country of the billing address
 * @property billingCity The city of the billing address
 * @property billingZip The zip code of the billing address
 * @property billingStreetAndNumber The street and house number of the billing address
 * @property shippingCountry The country of the shipping address
 * @property shippingCity The city of the shipping address
 * @property shippingZip The zip code of the shipping address
 * @property shippingStreetAndNumber The street and house number of the shipping address
 * @property accountId The id of the account
 * @property createdDate The creation date of the account
 */
data class AccountBE(
	var name: String,
	val ownerName: String,
	val ownerId: Int,
	var billingCountry: String,
	var billingCity: String,
	var billingZip: String,
	var billingStreetAndNumber: String,
	var shippingCountry: String,
	var shippingCity: String,
	var shippingZip: String,
	var shippingStreetAndNumber: String,
	val accountId: Int = -1,
	val createdDate: LocalDate = LocalDate.now(),
)
