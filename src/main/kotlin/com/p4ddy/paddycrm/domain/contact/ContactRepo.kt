package com.p4ddy.paddycrm.domain.contact

/**
 * Contact repo interface
 */
interface ContactRepo {
	/**
	 * Find all contacts (that are visible to the current user)
	 */
	fun findAll(): List<Contact>

	/**
	 * Find the contact by the given id (if it is visible to the current user)
	 */
	fun findById(id: Int): Contact

	/**
	 * Save the given contact to the database
	 */
	fun save(cont: Contact): Contact

	/**
	 * Update the given contact in the database
	 */
	fun update(cont: Contact): Contact

	/**
	 * Delete the given contact in the database
	 */
	fun delete(cont: Contact)
}
