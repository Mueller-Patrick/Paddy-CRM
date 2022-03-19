package com.p4ddy.paddycrm.plugins.persistence.exposed.opportunity

import com.p4ddy.paddycrm.application.user.UserSingleton
import com.p4ddy.paddycrm.domain.opportunity.Opportunity
import com.p4ddy.paddycrm.domain.opportunity.OpportunityRepo
import com.p4ddy.paddycrm.domain.opportunity.OpportunityStage
import com.p4ddy.paddycrm.domain.user.UserTypes
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.accountId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.amount
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.closeDate
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.createdDate
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.name
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.opportunityId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.ownerId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.probability
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.product
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.quantity
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable.stage
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction

class OpportunityExposedRepo : OpportunityRepo {
	override fun findAll(): List<Opportunity> {
		val opptyList: MutableList<Opportunity> = mutableListOf()

		transaction {
			for (opptyRow in OpportunityTable.select(getUserVisibilityQueryClause())) {
				opptyList.add(
					Opportunity(
						name = opptyRow[name],
						accountId = opptyRow[accountId],
						amount = opptyRow[amount],
						closeDate = opptyRow[closeDate],
						ownerId = opptyRow[ownerId],
						product = opptyRow[product],
						probability = opptyRow[probability],
						quantity = opptyRow[quantity],
						stage = OpportunityStage.valueOf(opptyRow[stage]),
						opportunityId = opptyRow[opportunityId],
						createdDate = opptyRow[createdDate]
					)
				)
			}
		}

		return opptyList
	}

	override fun findById(id: Int): Opportunity {
		var resultRow: ResultRow? = null

		transaction {
			resultRow = OpportunityTable.select(opportunityId.eq(id) and getUserVisibilityQueryClause()).first()
		}

		if (resultRow == null) {
			throw Exception("Error reading from the database")
		}

		return Opportunity(
			name = resultRow!![name],
			accountId = resultRow!![accountId],
			amount = resultRow!![amount],
			closeDate = resultRow!![closeDate],
			ownerId = resultRow!![ownerId],
			product = resultRow!![product],
			probability = resultRow!![probability],
			quantity = resultRow!![quantity],
			stage = OpportunityStage.valueOf(resultRow!![stage]),
			opportunityId = resultRow!![opportunityId],
			createdDate = resultRow!![createdDate]
		)
	}

	override fun save(oppty: Opportunity): Opportunity {
		var opptyId = -1

		transaction {
			opptyId = OpportunityTable.insert {
				it[name] = oppty.name
				it[accountId] = oppty.accountId
				it[amount] = oppty.amount
				it[closeDate] = oppty.closeDate
				it[ownerId] = getCurrentUserId()
				it[product] = oppty.product
				it[probability] = oppty.probability
				it[quantity] = oppty.quantity
				it[stage] = oppty.stage.toString()
				it[createdDate] = oppty.createdDate
			}[OpportunityTable.opportunityId]
		}

		if (opptyId == -1) {
			throw Exception("Error inserting the opportunity into the database")
		}

		return oppty.copy(opportunityId = opptyId, ownerId = getCurrentUserId())
	}

	override fun update(oppty: Opportunity): Opportunity {
		transaction {
			OpportunityTable.update({ opportunityId.eq(oppty.opportunityId) and getUserVisibilityQueryClause() }) {
				it[name] = oppty.name
				it[accountId] = oppty.accountId
				it[amount] = oppty.amount
				it[closeDate] = oppty.closeDate
				it[product] = oppty.product
				it[probability] = oppty.probability
				it[quantity] = oppty.quantity
				it[stage] = oppty.stage.toString()
			}
		}

		return findById(oppty.opportunityId)
	}

	override fun delete(oppty: Opportunity) {
		transaction {
			OpportunityTable.deleteWhere { opportunityId.eq(oppty.opportunityId) and getUserVisibilityQueryClause() }
		}
	}

	/**
	 * Returns the user id of the user that is currently logged in
	 *
	 * @throws Exception When no user is logged in
	 */
	private fun getCurrentUserId(): Int {
		if (UserSingleton.user == null) {
			throw Exception("No user logged in")
		}

		return UserSingleton.user!!.userId
	}

	/**
	 * Returns query clause to filter accounts that should not be visible to the user that is logged in
	 *
	 * @throws Exception When no user is logged in
	 */
	private fun getUserVisibilityQueryClause(): Op<Boolean> {
		val user = UserSingleton.user ?: throw Exception("No user logged in")

		val queryFilter = when (user.userType) {
			UserTypes.ADMIN -> {
				opportunityId.eq(opportunityId)
			}
			UserTypes.SALESREP -> {
				ownerId.eq(user.userId)
			}
			UserTypes.MANAGER -> {
				ownerId.eq(user.userId) or ownerId.inList(
					UserTable
						.slice(UserTable.userId)
						.select(UserTable.managerId.eq(user.userId))
						.map { it[UserTable.userId] }
				)
			}
		}

		return queryFilter
	}
}
