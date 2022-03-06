package com.p4ddy.paddycrm.application.contact

import com.p4ddy.paddycrm.domain.address.AddressVO
import com.p4ddy.paddycrm.domain.contact.Contact
import com.p4ddy.paddycrm.domain.contact.ContactRepo

/**
 * Contact application service class
 *
 * @param contactRepo Contact repository to use for persistence
 */
class ContactApplicationService(
	val contactRepo: ContactRepo
) {
	/**
	 * Find all contacts (that are visible to the current user)
	 *
	 * @return A list of contacts that have been found
	 */
	fun findAllContacts(): List<Contact> {
		return contactRepo.findAll()
	}

	/**
	 * Find a contact by its id (if it is visible to the current user)
	 *
	 * @param id The id of the contact
	 * @return The contact, if it exists and is visible
	 */
	fun findContactById(id: Int): Contact {
		return contactRepo.findById(id)
	}

	/**
	 * Create a new contact and save it to the database
	 *
	 * @param accountId The id of the related accountId
	 * @param ownerId The id of the record owner
	 * @param salutation The salutation of the contact
	 * @param lastName The last name of the contact
	 * @param firstName The first name of the contact
	 * @param address The AddressVO Object representing the address of the contact
	 * @return The created contact object
	 */
	fun createContact(
		accountId: Int,
		ownerId: Int,
		salutation: String,
		lastName: String,
		firstName: String,
		address: AddressVO
	): Contact {
		val cont = Contact(accountId, ownerId, salutation, lastName, firstName, address)
		return contactRepo.save(cont)
	}

	/**
	 * Update the given contact in the database
	 *
	 * @param cont: The contact to update
	 * @return The updated contact object
	 */
	fun updateContact(cont: Contact): Contact {
		return contactRepo.update(cont)
	}

	/**
	 * Deletes the given contact in the database
	 *
	 * @param cont: The contact to delete
	 */
	fun deleteContact(cont: Contact) {
		return contactRepo.delete(cont)
	}
}
