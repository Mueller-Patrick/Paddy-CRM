package com.p4ddy.paddycrm.domain.contact

import com.p4ddy.paddycrm.domain.address.AddressVO
import java.util.*

/**
 * Contact domain entity
 *
 * @property accountId The id of the related account
 * @property createdDate The date of the account creation
 * @property ownerId The id of the user that owns this record
 * @property salutation The salutation of the contact
 * @property lastName The last name of the contact
 * @property firstName The first name of the contact
 * @property address AddressVO Object of the contact address
 * @property contactId The unique id of the contact
 *
 * @constructor Creates a new account entity
 */
class Contact(
	val accountId: Int,
	val createdDate: Date,
	val ownerId: Int,
	var salutation: String,
	var lastName: String,
	var firstName: String,
	var address: AddressVO,
	val contactId: Int = -1
) {
}
