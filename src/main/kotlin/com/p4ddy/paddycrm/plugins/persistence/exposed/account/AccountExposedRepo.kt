package com.p4ddy.paddycrm.plugins.persistence.exposed.account

import com.p4ddy.paddycrm.application.user.UserSingleton
import com.p4ddy.paddycrm.domain.account.Account
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.domain.address.AddressVO
import com.p4ddy.paddycrm.domain.user.UserTypes
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.accountId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.billingAddressCity
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.billingAddressCountry
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.billingAddressStreetAndNumber
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.billingAddressZipCode
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.createdDate
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.name
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.ownerId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.shippingAddressCity
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.shippingAddressCountry
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.shippingAddressStreetAndNumber
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable.shippingAddressZipCode
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.managerId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.userId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction

class AccountExposedRepo : AccountRepo {
	override fun findAll(): List<Account> {
		val accountList: MutableList<Account> = mutableListOf()

		transaction {
			for (accountRow in AccountTable.select(getUserVisibilityQueryClause())) {
				accountList.add(
					Account(
						name = accountRow[name],
						ownerId = accountRow[ownerId],
						billingAddress = AddressVO(
							accountRow[billingAddressCountry],
							accountRow[billingAddressCity],
							accountRow[billingAddressZipCode],
							accountRow[billingAddressStreetAndNumber]
						),
						shippingAddress = AddressVO(
							accountRow[shippingAddressCountry],
							accountRow[shippingAddressCity],
							accountRow[shippingAddressZipCode],
							accountRow[shippingAddressStreetAndNumber]
						),
						accountId = accountRow[accountId],
						createdDate = accountRow[createdDate]
					)
				)
			}
		}

		return accountList
	}

	override fun findById(id: Int): Account {
		var resultRow: ResultRow? = null

		transaction {
			resultRow = AccountTable.select(accountId.eq(id) and getUserVisibilityQueryClause()).first()
		}

		if (resultRow == null) {
			throw Exception("Error reading from the database")
		}

		return Account(
			name = resultRow!![name],
			ownerId = resultRow!![ownerId],
			billingAddress = AddressVO(
				resultRow!![billingAddressCountry],
				resultRow!![billingAddressCity],
				resultRow!![billingAddressZipCode],
				resultRow!![billingAddressStreetAndNumber]
			),
			shippingAddress = AddressVO(
				resultRow!![shippingAddressCountry],
				resultRow!![shippingAddressCity],
				resultRow!![shippingAddressZipCode],
				resultRow!![shippingAddressStreetAndNumber]
			),
			accountId = resultRow!![accountId],
			createdDate = resultRow!![createdDate]
		)
	}

	override fun save(acct: Account): Account {
		var accountId = -1

		transaction {
			accountId = AccountTable.insert {
				it[name] = acct.name
				it[ownerId] = getCurrentUserId()
				it[billingAddressCountry] = acct.billingAddress.country
				it[billingAddressCity] = acct.billingAddress.city
				it[billingAddressZipCode] = acct.billingAddress.zipCode
				it[billingAddressStreetAndNumber] = acct.billingAddress.streetAndNumber
				it[shippingAddressCountry] = acct.shippingAddress.country
				it[shippingAddressCity] = acct.shippingAddress.city
				it[shippingAddressZipCode] = acct.shippingAddress.zipCode
				it[shippingAddressStreetAndNumber] = acct.shippingAddress.streetAndNumber
				it[createdDate] = acct.createdDate
			}[AccountTable.accountId]
		}

		if (accountId == -1) {
			throw Exception("Error inserting the account into the database")
		}

		return acct.copy(accountId = accountId, ownerId = getCurrentUserId())
	}

	override fun update(acct: Account): Account {
		transaction {
			AccountTable.update({ accountId.eq(acct.accountId) and getUserVisibilityQueryClause() }) {
				it[name] = acct.name
				it[billingAddressCountry] = acct.billingAddress.country
				it[billingAddressCity] = acct.billingAddress.city
				it[billingAddressZipCode] = acct.billingAddress.zipCode
				it[billingAddressStreetAndNumber] = acct.billingAddress.streetAndNumber
				it[shippingAddressCountry] = acct.shippingAddress.country
				it[shippingAddressCity] = acct.shippingAddress.city
				it[shippingAddressZipCode] = acct.shippingAddress.zipCode
				it[shippingAddressStreetAndNumber] = acct.shippingAddress.streetAndNumber
			}
		}

		return findById(acct.accountId)
	}

	override fun delete(acct: Account) {
		transaction {
			val deletedRows =
				AccountTable.deleteWhere { accountId.eq(acct.accountId) and getUserVisibilityQueryClause() }
			if (deletedRows > 0) {
				ContactTable.deleteWhere { ContactTable.accountId.eq(acct.accountId) }
				OpportunityTable.deleteWhere { OpportunityTable.accountId.eq(acct.accountId) }
			}
		}
	}

	/**
	 * Returns the user id of the user that is currently logged in
	 *
	 * @throws Exception When no user is logged in
	 */
	private fun getCurrentUserId(): Int {
		if (UserSingleton.user == null) {
			throw Exception("No user logged in")
		}

		return UserSingleton.user!!.userId
	}

	/**
	 * Returns query clause to filter accounts that should not be visible to the user that is logged in
	 *
	 * @throws Exception When no user is logged in
	 */
	private fun getUserVisibilityQueryClause(): Op<Boolean> {
		val user = UserSingleton.user ?: throw Exception("No user logged in")

		val queryFilter = when (user.userType) {
			UserTypes.ADMIN -> {
				accountId.eq(accountId)
			}
			UserTypes.SALESREP -> {
				ownerId.eq(user.userId)
			}
			UserTypes.MANAGER -> {
				ownerId.eq(user.userId) or ownerId.inList(
					UserTable
						.slice(userId)
						.select(managerId.eq(user.userId))
						.map { it[userId] }
				)
			}
		}

		return queryFilter
	}
}
