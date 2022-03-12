package com.p4ddy.paddycrm.adapters.contact

import com.p4ddy.paddycrm.application.account.AccountApplicationService
import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.domain.address.AddressVO
import com.p4ddy.paddycrm.domain.contact.Contact
import com.p4ddy.paddycrm.domain.user.UserRepo

/**
 * Contact converter class to convert contact objects to contact business entitiy objects and vice versa
 */
class ContactConverter(
	val userRepo: UserRepo,
	val acctRepo: AccountRepo,
	val userApplicationService: UserApplicationService = UserApplicationService(userRepo),
	val accountApplicationService: AccountApplicationService = AccountApplicationService(acctRepo)
) {
	/**
	 * Converts a ContactBE object to a Contact object
	 *
	 * @param contBE The ContactBE object to convert
	 * @return The converted Contact object
	 */
	fun convertBEToContact(contBE: ContactBE): Contact {
		val contact = Contact(
			accountId = contBE.accountId,
			ownerId = contBE.ownerId,
			salutation = contBE.salutation,
			lastName = contBE.lastName,
			firstName = contBE.firstName,
			address = AddressVO(
				country = contBE.addressCountry,
				city = contBE.addressCity,
				zipCode = contBE.addressZip,
				streetAndNumber = contBE.addressStreetAndNumber
			),
			contactId = contBE.contactId,
			createdDate = contBE.createdDate
		)

		return contact
	}

	/**
	 * Converts a Contact object to a ContactBE object
	 *
	 * @param cont The Contact object to convert
	 * @return The converted ContactBE object
	 */
	fun convertContactToBE(cont: Contact): ContactBE {
		val relatedAccount = accountApplicationService.findAccountById(cont.accountId)
		val contactOwner = userApplicationService.findUserById(cont.ownerId)

		val contactBE = ContactBE(
			accountName = relatedAccount.name,
			accountId = cont.accountId,
			ownerName = "${contactOwner.firstName} ${contactOwner.lastName}",
			ownerId = cont.ownerId,
			salutation = cont.salutation,
			lastName = cont.lastName,
			firstName = cont.firstName,
			addressCountry = cont.address.country,
			addressCity = cont.address.city,
			addressZip = cont.address.zipCode,
			addressStreetAndNumber = cont.address.streetAndNumber,
			contactId = cont.contactId,
			createdDate = cont.createdDate
		)

		return contactBE
	}
}
