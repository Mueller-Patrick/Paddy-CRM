package com.p4ddy.paddycrm.adapters.opportunity

import com.p4ddy.paddycrm.application.account.AccountApplicationService
import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.domain.opportunity.Opportunity
import com.p4ddy.paddycrm.domain.user.UserRepo

/**
 * Opportunity converter class to convert opportunity objects to opportunity business entity objects and vice versa
 */
class OpportunityConverter(
	val userRepo: UserRepo,
	val acctRepo: AccountRepo,
	val userApplicationService: UserApplicationService = UserApplicationService(userRepo),
	val accountApplicationService: AccountApplicationService = AccountApplicationService(acctRepo)
) {
	/**
	 * Converts an OpportunityBE object to an Opportunity object
	 *
	 * @param opptyBE The OpportunityBE object to convert
	 * @return The converted Opportunity object
	 */
	fun convertBEToOpportunity(opptyBE: OpportunityBE): Opportunity {
		val oppty = Opportunity(
			name = opptyBE.name,
			accountId = opptyBE.accountId,
			amount = opptyBE.amount,
			closeDate = opptyBE.closeDate,
			ownerId = opptyBE.ownerId,
			product = opptyBE.product,
			probability = opptyBE.probability,
			quantity = opptyBE.quantity,
			stage = opptyBE.stage,
			opportunityId = opptyBE.opportunityId,
			createdDate = opptyBE.createdDate
		)

		return oppty
	}

	/**
	 * Converts an Opportunity object to an OpportunityBE object
	 *
	 * @param oppty The Opportunity object to convert
	 * @return The converted OpportunityBE object
	 */
	fun convertOpportunityToBE(oppty: Opportunity): OpportunityBE {
		val relatedAccount = accountApplicationService.findAccountById(oppty.accountId)
		val opptyOwner = userApplicationService.findUserById(oppty.ownerId)

		val opptyBE = OpportunityBE(
			name = oppty.name,
			accountName = relatedAccount.name,
			accountId = oppty.accountId,
			amount = oppty.amount,
			closeDate = oppty.closeDate,
			ownerName = "${opptyOwner.firstName} ${opptyOwner.lastName}",
			ownerId = oppty.ownerId,
			product = oppty.product,
			probability = oppty.probability,
			quantity = oppty.quantity,
			stage = oppty.stage,
			opportunityId = oppty.opportunityId,
			createdDate = oppty.createdDate
		)

		return opptyBE
	}
}
