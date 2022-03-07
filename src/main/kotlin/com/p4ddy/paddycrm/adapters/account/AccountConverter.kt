package com.p4ddy.paddycrm.adapters.account

import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.domain.account.Account
import com.p4ddy.paddycrm.domain.address.AddressVO
import com.p4ddy.paddycrm.domain.user.UserRepo

/**
 * Account converter class to convert account objects to account business entity objects and the other way around
 */
class AccountConverter(
	val userRepo: UserRepo,
	val userApplicationService: UserApplicationService = UserApplicationService(userRepo)
) {
	/**
	 * Converts an AccountBE Object to an Account Object
	 * @param acctBE The AccountBE Object to convert
	 * @return The converted Account Object
	 */
	fun convertBEToAccount(acctBE: AccountBE): Account {
		val account = Account(
			name = acctBE.name,
			ownerId = acctBE.ownerId,
			billingAddress = AddressVO(
				acctBE.billingCountry,
				acctBE.billingCity,
				acctBE.billingZip,
				acctBE.billingStreetAndNumber
			),
			shippingAddress = AddressVO(
				acctBE.shippingCountry,
				acctBE.shippingCity,
				acctBE.shippingZip,
				acctBE.shippingStreetAndNumber
			),
			accountId = acctBE.accountId,
			createdDate = acctBE.createdDate
		)

		return account
	}

	/**
	 * Converts an Account object to an AccountBE Object
	 * @param acct The Account Object to convert
	 * @return The converted AccountBE Object
	 */
	fun convertAccountToBE(acct: Account): AccountBE {
		val accountOwner = userApplicationService.findUserById(acct.ownerId)

		val accountBE = AccountBE(
			name = acct.name,
			ownerName = "${accountOwner.firstName} ${accountOwner.lastName}",
			ownerId = acct.ownerId,
			billingCountry = acct.billingAddress.country,
			billingCity = acct.billingAddress.city,
			billingZip = acct.billingAddress.zipCode,
			billingStreetAndNumber = acct.billingAddress.streetAndNumber,
			shippingCountry = acct.shippingAddress.country,
			shippingCity = acct.shippingAddress.city,
			shippingZip = acct.shippingAddress.zipCode,
			shippingStreetAndNumber = acct.shippingAddress.streetAndNumber,
			accountId = acct.accountId,
			createdDate = acct.createdDate
		)

		return accountBE
	}
}
