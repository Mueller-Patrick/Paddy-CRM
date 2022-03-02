package com.p4ddy.paddycrm.domain.account

import com.p4ddy.paddycrm.domain.address.AddressVO
import java.time.LocalDate

class Account(
	name: String,
	ownerId: Int,
	billingAddress: AddressVO,
	shippingAddress: AddressVO = billingAddress,
	accountId: Int = -1,
	createdDate: LocalDate = LocalDate.now(),
) {
	val accountId: Int
	var name: String
	val createdDate: LocalDate
	val ownerId: Int
	var billingAddress: AddressVO
	var shippingAddress: AddressVO

	init {
		this.accountId = accountId
		this.name = name
		this.createdDate = createdDate
		this.ownerId = ownerId
		this.billingAddress = billingAddress
		this.shippingAddress = shippingAddress
	}
}
