package com.p4ddy.paddycrm.plugins.persistence.exposed.contact

import com.p4ddy.paddycrm.application.session.SessionManager
import com.p4ddy.paddycrm.domain.address.AddressVO
import com.p4ddy.paddycrm.domain.contact.Contact
import com.p4ddy.paddycrm.domain.contact.ContactRepo
import com.p4ddy.paddycrm.domain.user.UserTypes
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.accountId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.addressCity
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.addressCountry
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.addressStreetAndNumber
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.addressZipCode
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.contactId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.createdDate
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.firstName
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.lastName
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.ownerId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.salutation
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable
import com.p4ddy.paddycrm.plugins.session.SingletonSessionManager
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction

class ContactExposedRepo : ContactRepo {
	private val sessionManager: SessionManager = SingletonSessionManager()

	override fun findAll(): List<Contact> {
		val contactList: MutableList<Contact> = mutableListOf()

		transaction {
			for (contactRow in ContactTable.select(getUserVisibilityQueryClause())) {
				contactList.add(
					convertResultRowToContact(contactRow)
				)
			}
		}

		return contactList
	}

	override fun findById(id: Int): Contact {
		var resultRow: ResultRow? = null

		transaction {
			resultRow = ContactTable.select(contactId.eq(id) and getUserVisibilityQueryClause()).first()
		}

		if (resultRow == null) {
			throw Exception("Error reading from the database")
		}

		return convertResultRowToContact(resultRow!!)
	}

	override fun save(cont: Contact): Contact {
		var contactId = -1

		transaction {
			contactId = ContactTable.insert {
				it[accountId] = cont.accountId
				it[ownerId] = getCurrentUserId()
				it[salutation] = cont.salutation
				it[lastName] = cont.lastName
				it[firstName] = cont.firstName
				it[addressCountry] = cont.address.country
				it[addressCity] = cont.address.city
				it[addressZipCode] = cont.address.zipCode
				it[addressStreetAndNumber] = cont.address.streetAndNumber
				it[createdDate] = cont.createdDate
			}[ContactTable.contactId]
		}

		if (contactId == -1) {
			throw Exception("Error inserting the contact into the database")
		}

		return cont.copy(contactId = contactId, ownerId = getCurrentUserId())
	}

	override fun update(cont: Contact): Contact {
		transaction {
			ContactTable.update({ contactId.eq(cont.contactId) and getUserVisibilityQueryClause() }) {
				it[accountId] = cont.accountId
				it[salutation] = cont.salutation
				it[lastName] = cont.lastName
				it[firstName] = cont.firstName
				it[addressCountry] = cont.address.country
				it[addressCity] = cont.address.city
				it[addressZipCode] = cont.address.zipCode
				it[addressStreetAndNumber] = cont.address.streetAndNumber
			}
		}

		return findById(cont.contactId)
	}

	override fun delete(cont: Contact) {
		transaction {
			ContactTable.deleteWhere { contactId.eq(cont.contactId) and getUserVisibilityQueryClause() }
		}
	}

	/**
	 * Returns the user id of the user that is currently logged in
	 *
	 * @throws Exception When no user is logged in
	 */
	private fun getCurrentUserId(): Int {
		return sessionManager.getCurrentUser().userId
	}

	/**
	 * Returns query clause to filter accounts that should not be visible to the user that is logged in
	 *
	 * @throws Exception When no user is logged in
	 */
	private fun getUserVisibilityQueryClause(): Op<Boolean> {
		val user = sessionManager.getCurrentUser()

		val queryFilter = when (user.userType) {
			UserTypes.ADMIN -> {
				contactId.eq(contactId)
			}
			UserTypes.SALESREP -> {
				ownerId.eq(user.userId)
			}
			UserTypes.MANAGER -> {
				ownerId.eq(user.userId) or ownerId.inList(
					UserTable
						.slice(UserTable.userId)
						.select(UserTable.managerId.eq(user.userId))
						.map { it[UserTable.userId] }
				)
			}
		}

		return queryFilter
	}

	/**
	 * Converts a row from the database into a contact object
	 *
	 * @param contactRow The row from the database
	 * @return The converted Contact Object
	 */
	private fun convertResultRowToContact(contactRow: ResultRow) = Contact(
		accountId = contactRow[accountId],
		ownerId = contactRow[ownerId],
		salutation = contactRow[salutation],
		lastName = contactRow[lastName],
		firstName = contactRow[firstName],
		address = AddressVO(
			country = contactRow[addressCountry],
			city = contactRow[addressCity],
			zipCode = contactRow[addressZipCode],
			streetAndNumber = contactRow[addressStreetAndNumber]
		),
		contactId = contactRow[contactId],
		createdDate = contactRow[createdDate]
	)
}
