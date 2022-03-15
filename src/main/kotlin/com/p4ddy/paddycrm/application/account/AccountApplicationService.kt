package com.p4ddy.paddycrm.application.account

import com.p4ddy.paddycrm.domain.account.Account
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.domain.address.AddressVO

/**
 * Account application service class
 *
 * @param accountRepo account repository to use for persistence
 */
class AccountApplicationService(
	val accountRepo: AccountRepo
) {
	/**
	 * Find all accounts (that are visible to the current user)
	 *
	 * @return A list of accounts that have been found
	 */
	fun findAllAccounts(): List<Account> {
		return accountRepo.findAll()
	}

	/**
	 * Find an account by its id (if it is visible to the current user)
	 *
	 * @param id The id of the account
	 * @return The account, if it exists and is visible
	 */
	fun findAccountById(id: Int): Account {
		return accountRepo.findById(id)
	}

	/**
	 * Create a new account and save it to the database
	 *
	 * @param name The name of the account
	 * @param ownerId The id of the user that owns the account
	 * @param billingAddress AddressVO object of the billing address
	 * @param shippingAddress AddressVO object of the shipping address
	 * @return The created account object
	 */
	fun createAccount(name: String, ownerId: Int, billingAddress: AddressVO, shippingAddress: AddressVO): Account {
		val acct = Account(name, ownerId, billingAddress, shippingAddress)
		return accountRepo.save(acct)
	}

	/**
	 * Create a new account and save it to the database.
	 * Shipping address will be the same as the billing address
	 *
	 * @param name The name of the account
	 * @param ownerId The id of the user that owns the account
	 * @param billingAddress AddressVO object of the billing address
	 * @return The created account object
	 */
	fun createAccount(name: String, ownerId: Int, billingAddress: AddressVO): Account {
		val acct = Account(name, ownerId, billingAddress)
		return accountRepo.save(acct)
	}

	/**
	 * Save the given account object to the database
	 *
	 * @param acct The account to save
	 * @return The created account object
	 */
	fun createAccount(acct: Account): Account {
		return accountRepo.save(acct)
	}

	/**
	 * Update the given account in the database
	 *
	 * @param acct: The account to update
	 * @return The updated account object
	 */
	fun updateAccount(acct: Account): Account {
		return accountRepo.update(acct)
	}

	/**
	 * Deletes the given account in the database
	 *
	 * @param acct: The account to delete
	 */
	fun deleteAccount(acct: Account) {
		return accountRepo.delete(acct)
	}
}
