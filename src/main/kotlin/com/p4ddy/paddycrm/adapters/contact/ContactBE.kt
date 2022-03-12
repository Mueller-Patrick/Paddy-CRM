package com.p4ddy.paddycrm.adapters.contact

import java.time.LocalDate

/**
 * Contact Business Entity for usage in the GUI
 *
 * @property accountName The name of the related account
 * @property accountId The id of the related account
 * @property ownerName The name of the record owner
 * @property ownerId The id of the record owner
 * @property salutation The salutation of the contact
 * @property lastName The last name of the contact
 * @property firstName The first name of the contact
 * @property addressCountry The country of the contact's address
 * @property addressCity The city of the contact's address
 * @property addressZip The zip code of the contact's address
 * @property addressStreetAndNumber The street and house number of the contact's address
 * @property contactId The id of the contact
 * @property createdDate The creation date of the contact
 */
data class ContactBE(
	val accountName: String,
	val accountId: Int,
	val ownerName: String,
	val ownerId: Int,
	val salutation: String,
	val lastName: String,
	val firstName: String,
	val addressCountry: String,
	val addressCity: String,
	val addressZip: String,
	val addressStreetAndNumber: String,
	val contactId: Int = -1,
	val createdDate: LocalDate = LocalDate.now()
) {
}
