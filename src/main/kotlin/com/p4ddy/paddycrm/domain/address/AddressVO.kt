package com.p4ddy.paddycrm.domain.address

/**
 * Address value object.
 *
 * @property country The country of the address
 * @property city The city of the address
 * @property zipCode The zip code of the address. Has to be between 0 and 99999
 * @property streetAndNumber The street and house number of the address
 * @constructor Creates an address value object. The params must not be blank
 */
data class AddressVO(
	val country: String,
	val city: String,
	val zipCode: String,
	val streetAndNumber: String
) {
	init {
		if (
			country.isBlank()
			|| city.isBlank()
			|| zipCode.isBlank()
			|| streetAndNumber.isBlank()
		) {
			throw Exception("No empty values allowed in address")
		}

		if (zipCode.toInt() < 0 || zipCode.toInt() > 99999) {
			throw Exception("zipCode must be between 0 and 99999")
		}
	}

	override fun toString(): String {
		return "$streetAndNumber, $zipCode $city, $country"
	}
}
