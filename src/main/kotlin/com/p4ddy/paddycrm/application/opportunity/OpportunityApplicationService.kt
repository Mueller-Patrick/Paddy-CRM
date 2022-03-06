package com.p4ddy.paddycrm.application.opportunity

import com.p4ddy.paddycrm.domain.opportunity.Opportunity
import com.p4ddy.paddycrm.domain.opportunity.OpportunityRepo
import java.time.LocalDate

/**
 * Opportunity application service class
 *
 * @param opptyRepo Opportunity repository to use for persistence
 */
class OpportunityApplicationService(
	val opptyRepo: OpportunityRepo
) {
	/**
	 * Find all oppties (that are visible to the current user)
	 *
	 * @return A list of oppties that have been found
	 */
	fun findAllOpportunities(): List<Opportunity> {
		return opptyRepo.findAll()
	}

	/**
	 * Find an oppty by its id (if it is visible to the current user)
	 *
	 * @param id The id of the oppty
	 * @return The oppty, if it exists and is visible
	 */
	fun findOpportunityById(id: Int): Opportunity {
		return opptyRepo.findById(id)
	}

	/**
	 * Create a new oppty and save it to the database
	 *
	 * @param name The name of the opportunity
	 * @param accountId The id of the related account
	 * @param amount The amount of the Opportunity in €
	 * @param closeDate The closing date of the Opportunity
	 * @param ownerId The id of the record owner
	 * @param product The product that this opportunity is about
	 * @param probability The probability of this opportunity being won (in percent)
	 * @param quantity The quantity of products in this opportunity
	 * @return The created oppty object
	 */
	fun createOpportunity(
		name: String,
		accountId: Int,
		amount: Float,
		closeDate: LocalDate,
		ownerId: Int,
		product: String,
		probability: Int,
		quantity: Int
	): Opportunity {
		val oppty = Opportunity(name, accountId, amount, closeDate, ownerId, product, probability, quantity)
		return opptyRepo.save(oppty)
	}

	/**
	 * Update the given oppty in the database
	 *
	 * @param oppty: The oppty to update
	 * @return The updated oppty object
	 */
	fun updateOpportunity(oppty: Opportunity): Opportunity {
		return opptyRepo.update(oppty)
	}

	/**
	 * Deletes the given oppty in the database
	 *
	 * @param oppty: The oppty to delete
	 */
	fun deleteOpportunity(oppty: Opportunity) {
		return opptyRepo.delete(oppty)
	}
}
