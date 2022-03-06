package com.p4ddy.paddycrm.domain.contact

import com.p4ddy.paddycrm.domain.address.AddressVO
import java.time.LocalDate
import java.util.*

/**
 * Contact domain entity
 *
 * @property accountId The id of the related account
 * @property ownerId The id of the user that owns this record
 * @property salutation The salutation of the contact
 * @property lastName The last name of the contact
 * @property firstName The first name of the contact
 * @property address AddressVO Object of the contact address
 * @property contactId The unique id of the contact
 * @property createdDate The date of the contact creation
 *
 * @constructor Creates a new contact entity
 */
class Contact(
	val accountId: Int,
	val ownerId: Int,
	var salutation: String,
	var lastName: String,
	var firstName: String,
	var address: AddressVO,
	val contactId: Int = -1,
	val createdDate: LocalDate = LocalDate.now()
) {
}
