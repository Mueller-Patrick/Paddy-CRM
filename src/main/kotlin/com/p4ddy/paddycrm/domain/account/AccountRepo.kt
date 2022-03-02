package com.p4ddy.paddycrm.domain.account

/**
 * Account repo interface
 */
interface AccountRepo {
	/**
	 * Find all accounts (that are visible to the current user)
	 */
	fun findAll(): List<Account>

	/**
	 * Find the account by the given id (if it is visible to the current user)
	 */
	fun findById(id: Int): Account

	/**
	 * Save the given account to the database
	 */
	fun save(acct: Account): Account

	/**
	 * Update the given account in the database
	 */
	fun update(acct: Account): Account

	/**
	 * Delete the given account in the database
	 */
	fun delete(acct: Account)
}
