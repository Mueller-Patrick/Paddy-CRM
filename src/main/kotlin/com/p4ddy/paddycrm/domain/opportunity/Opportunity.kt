package com.p4ddy.paddycrm.domain.opportunity

import java.time.LocalDate

/**
 * Opportunity domain entity
 *
 * @property name The name of the opportunity
 * @property accountId The id of the related account
 * @property amount The amount of the opportunity in €
 * @property closeDate The date when the opportunity closes
 * @property ownerId The id of the record owner
 * @property product The Product that this opportunity is about
 * @property probability The probability that this opportunity will be won (in percent)
 * @property quantity The quantity of products included in this opportunity
 * @property opportunityId The id of this opportunity
 *
 * @constructor Creates a new opportunity entity
 */
class Opportunity(
	var name: String,
	val accountId: Int,
	var amount: Float,
	var closeDate: LocalDate,
	val ownerId: Int,
	var product: String,
	var probability: Int,
	var quantity: Int,
	val opportunityId: Int = -1,
) {
}
