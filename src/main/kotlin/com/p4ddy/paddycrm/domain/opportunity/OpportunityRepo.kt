package com.p4ddy.paddycrm.domain.opportunity

/**
 * Opportunity repo interface
 */
interface OpportunityRepo {
	/**
	 * Find all oppties (that are visible to the current user)
	 */
	fun findAll(): List<Opportunity>

	/**
	 * Find the oppty by the given id (if it is visible to the current user)
	 */
	fun findById(id: Int): Opportunity

	/**
	 * Save the given oppty to the database
	 */
	fun save(cont: Opportunity): Opportunity

	/**
	 * Update the given oppty in the database
	 */
	fun update(cont: Opportunity): Opportunity

	/**
	 * Delete the given oppty in the database
	 */
	fun delete(cont: Opportunity)
}
