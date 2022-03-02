package com.p4ddy.paddycrm.domain.account

interface AccountRepo {
	fun findAll(): List<Account>

	fun findById(id: Int): Account

	fun save(acct: Account): Account

	fun update(acct: Account): Account

	fun delete(acct: Account)
}
