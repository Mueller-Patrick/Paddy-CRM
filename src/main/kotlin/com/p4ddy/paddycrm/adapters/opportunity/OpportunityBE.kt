package com.p4ddy.paddycrm.adapters.opportunity

import com.p4ddy.paddycrm.domain.opportunity.OpportunityStage
import java.time.LocalDate

/**
 * Opportunity Business Entity for usage in the GUI
 *
 * @property name The name of the opportunity
 * @property accountName The name of the related account
 * @property accountId The id of the related account
 * @property amount The amount of the opportunity in €
 * @property closeDate The date when the opportunity closes
 * @property ownerName The name of the record owner
 * @property ownerId The id of the record owner
 * @property product The product that the Opportunity is about
 * @property probability The probability of the opportunity being won (in percent)
 * @property quantity The quantity of products included in the opportunity
 * @property opportunityId The id of the opportunity
 * @property createdDate The creation date of the opportunity
 */
data class OpportunityBE(
	var name: String,
	val accountName: String,
	var accountId: Int,
	var amount: Float,
	var closeDate: LocalDate,
	val ownerName: String,
	val ownerId: Int,
	var product: String,
	var probability: Int,
	var quantity: Int,
	var stage: OpportunityStage = OpportunityStage.PROSPECTING,
	var opportunityId: Int = -1,
	val createdDate: LocalDate = LocalDate.now()
)
