package com.p4ddy.paddycrm.plugins.persistence.exposed.user

import com.p4ddy.paddycrm.application.user.UserSingleton
import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.domain.user.UserRepo
import com.p4ddy.paddycrm.domain.user.UserTypes
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.createdDate
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.email
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.firstName
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.lastName
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.managerId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.password
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.userId
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable.userType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserExposedRepo : UserRepo {
	override fun findAll(): List<User> {
		val userList: MutableList<User> = mutableListOf()

		transaction {
			for (userRow in UserTable.selectAll()) {
				userList.add(
					User(
						lastName = userRow[lastName],
						firstName = userRow[firstName],
						password = "PROTECTED",
						email = userRow[email],
						userType = UserTypes.valueOf(userRow[userType]),
						managerId = userRow[managerId],
						userId = userRow[userId],
						createdDate = userRow[createdDate]
					)
				)
			}
		}

		return userList
	}

	override fun findById(id: Int): User {
		var resultRow: ResultRow? = null

		transaction {
			resultRow = UserTable.select(userId.eq(id)).first()
		}

		if (resultRow == null) {
			throw Exception("Error reading from the database")
		}

		return User(
			lastName = resultRow!![lastName],
			firstName = resultRow!![firstName],
			password = "PROTECTED",
			email = resultRow!![email],
			userType = UserTypes.valueOf(resultRow!![userType]),
			managerId = resultRow!![managerId],
			userId = resultRow!![userId],
			createdDate = resultRow!![createdDate]
		)
	}

	override fun findByEmail(email: String): User {
		var resultRow: ResultRow? = null

		transaction {
			resultRow = UserTable.select(UserTable.email.eq(email)).first()
		}

		if (resultRow == null) {
			throw Exception("Error reading from the database")
		}

		return User(
			lastName = resultRow!![lastName],
			firstName = resultRow!![firstName],
			password = "PROTECTED",
			email = resultRow!![UserTable.email],
			userType = UserTypes.valueOf(resultRow!![userType]),
			managerId = resultRow!![managerId],
			userId = resultRow!![userId],
			createdDate = resultRow!![createdDate]
		)
	}

	override fun checkPasswordValid(userId: Int, password: String): Boolean {
		var passwordValid = false

		transaction {
			val correctPassword =
				UserTable.slice(UserTable.password).select(UserTable.userId.eq(userId)).first()[UserTable.password]

			passwordValid = (password == correctPassword)
		}

		return passwordValid
	}

	override fun save(user: User): User {
		var userId = -1

		transaction {
			userId = UserTable.insert {
				it[managerId] = user.managerId
				it[lastName] = user.lastName
				it[firstName] = user.firstName
				it[password] = user.password
				it[email] = user.email
				it[userType] = user.userType.toString()
				it[createdDate] = user.createdDate
			}[UserTable.userId]
		}

		if (userId == -1) {
			throw Exception("Error inserting the user into the database")
		}

		return user.copy(userId = userId, password = "PROTECTED")
	}

	override fun update(user: User): User {
		transaction {
			UserTable.update({ userId.eq(user.userId) }) {
				it[managerId] = user.managerId
				it[lastName] = user.lastName
				it[firstName] = user.firstName
				it[password] = user.password
				it[email] = user.email
				it[userType] = user.userType.toString()
			}
		}

		return findById(user.userId)
	}

	override fun delete(user: User) {
		transaction {
			UserTable.deleteWhere { userId.eq(user.userId) }
			UserTable.deleteWhere { managerId.eq(user.userId) }
			AccountTable.deleteWhere { AccountTable.ownerId.eq(user.userId) }
			ContactTable.deleteWhere { ContactTable.ownerId.eq(user.userId) }
			OpportunityTable.deleteWhere { OpportunityTable.ownerId.eq(user.userId) }
		}
	}
}
