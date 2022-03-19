package com.p4ddy.paddycrm.plugins.gui.compose.misc

import com.p4ddy.paddycrm.application.account.AccountApplicationService
import com.p4ddy.paddycrm.application.contact.ContactApplicationService
import com.p4ddy.paddycrm.application.opportunity.OpportunityApplicationService
import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.application.user.UserSingleton
import com.p4ddy.paddycrm.domain.address.AddressVO
import com.p4ddy.paddycrm.domain.opportunity.OpportunityStage
import com.p4ddy.paddycrm.plugins.persistence.exposed.account.AccountExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.contact.ContactExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.opportunity.OpportunityExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.swing.text.DateFormatter

fun generateDemoData() {
	val acctService = AccountApplicationService(AccountExposedRepo())
	val contService = ContactApplicationService(ContactExposedRepo())
	val opptyService = OpportunityApplicationService(OpportunityExposedRepo())
	val userService = UserApplicationService(UserExposedRepo())

	val admin = userService.createAdmin("Admin", "Demo", "admin", "admin@demo.com")
	UserSingleton.user = admin
	val manager = userService.createManager("Manager", "Demo", "manager", "manager@demo.com")
	val sr1 = userService.createSalesRep("Salesrep1", "Demo", "salesrep", "salesrep1@demo.com", manager.userId)
	val sr2 = userService.createSalesRep("Salesrep2", "Demo", "salesrep", "salesrep2@demo.com", manager.userId)

	val address1 = AddressVO("Germany", "Karlsruhe", "76227", "Zur Gießerei 19-27b")
	val address2 = AddressVO("Germany", "Karlsruhe", "76133", "Erzbergerstraße 131")
	val address3 = AddressVO("Germany", "Karlsruhe", "76133", "Waldstraße 32")

	UserSingleton.user = sr1
	val acct1 = acctService.createAccount("DIGITALL Nature Germany GmbH", sr1.userId, address1)
	val acct2 = acctService.createAccount("DHBW Karlsruhe", sr1.userId, address2)
	contService.createContact(acct1.accountId, sr1.userId, "Herr", "Müller", "Patrick", address1)
	contService.createContact(acct2.accountId, sr1.userId, "Herr", "Schenkel", "Stephan", address2)
	opptyService.createOpportunity(
		"Programmentwurf",
		acct2.accountId,
		420.00F,
		LocalDate.parse("2022-05-16", DateTimeFormatter.ISO_DATE),
		sr1.userId,
		"Kotlin",
		100,
		1,
		OpportunityStage.CLOSED_WON
	)

	UserSingleton.user = sr2
	val acct3 = acctService.createAccount("Kebabi", sr2.userId, address2)
	opptyService.createOpportunity(
		"Döner mit alles",
		acct3.accountId,
		6.00F,
		LocalDate.now().plusDays(1),
		sr2.userId,
		"Döner Kebab",
		90,
		4,
		OpportunityStage.PROPOSAL
	)

	UserSingleton.user = admin
}
