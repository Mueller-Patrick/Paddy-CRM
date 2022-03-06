package com.p4ddy.paddycrm.domain.account

import com.p4ddy.paddycrm.domain.address.AddressVO
import java.time.LocalDate

/**
 * Account domain entity
 *
 * @property name The account name
 * @property ownerId The id of the user that owns this record
 * @property billingAddress AddressVO Object of the billing address
 * @property shippingAddress AddressVO Object of the shipping address. Leave empty to use billing address
 * @property accountId The unique ID of the account
 * @property createdDate The date of the account creation
 *
 * @constructor Creates a new account entity
 */
class Account(
	var name: String,
	val ownerId: Int,
	var billingAddress: AddressVO,
	var shippingAddress: AddressVO = billingAddress,
	val accountId: Int = -1,
	val createdDate: LocalDate = LocalDate.now(),
) {
}
