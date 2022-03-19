package com.p4ddy.paddycrm.application.user

import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.domain.user.UserRepo
import com.p4ddy.paddycrm.domain.user.UserTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

internal class UserApplicationServiceTest {
	var userRepo: UserRepo = UserRepoMock()
	var userService: UserApplicationService? = null

	@BeforeEach
	fun setUp() {
		userService = UserApplicationService(userRepo)
	}

	@Test
	fun createUserAsAdmin() {
		val admin = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.ADMIN
		)
		UserSingleton.user = admin

		val testadmin = userService!!.createAdmin("Last", "First", "Password", "email@admin.org")
		val testmanager = userService!!.createManager("Last", "First", "Password", "email@manager.org")
		val testsalesrep = userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)

		assert(testadmin.email == "email@admin.org")
		assert(testmanager.email == "email@manager.org")
		assert(testsalesrep.email == "email@salesrep.org")
	}

	@Test
	fun createUserAsManager() {
		val manager = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.MANAGER
		)
		UserSingleton.user = manager

		val exceptionAdmin = assertThrows<Exception> {
			userService!!.createAdmin("Last", "First", "Password", "email@admin.org")
		}
		assertEquals(exceptionAdmin.message, "Only system administrators may create users")

		val exceptionManager = assertThrows<Exception> {
			userService!!.createManager("Last", "First", "Password", "email@manager.org")
		}
		assertEquals(exceptionManager.message, "Only system administrators may create users")

		val exceptionSalesrep = assertThrows<Exception> {
			userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)
		}
		assertEquals(exceptionSalesrep.message, "Only system administrators may create users")
	}

	@Test
	fun createUserAsSalesrep() {
		val salesrep = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			1337
		)
		UserSingleton.user = salesrep

		val exceptionAdmin = assertThrows<Exception> {
			userService!!.createAdmin("Last", "First", "Password", "email@admin.org")
		}
		assertEquals(exceptionAdmin.message, "Only system administrators may create users")

		val exceptionManager = assertThrows<Exception> {
			userService!!.createManager("Last", "First", "Password", "email@manager.org")
		}
		assertEquals(exceptionManager.message, "Only system administrators may create users")

		val exceptionSalesrep = assertThrows<Exception> {
			userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)
		}
		assertEquals(exceptionSalesrep.message, "Only system administrators may create users")
	}

	@Test
	fun createUserNotLoggedIn() {
		UserSingleton.user = null

		val exceptionAdmin = assertThrows<Exception> {
			userService!!.createAdmin("Last", "First", "Password", "email@admin.org")
		}
		assertEquals(exceptionAdmin.message, "Can't create user when no user is logged in")

		val exceptionManager = assertThrows<Exception> {
			userService!!.createManager("Last", "First", "Password", "email@manager.org")
		}
		assertEquals(exceptionManager.message, "Can't create user when no user is logged in")

		val exceptionSalesrep = assertThrows<Exception> {
			userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)
		}
		assertEquals(exceptionSalesrep.message, "Can't create user when no user is logged in")
	}

	@Test
	fun updateUserPositive() {
		val admin = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.ADMIN
		)
		UserSingleton.user = admin

		val salesrep = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			managerId = 1,
			userId = 1337
		)

		val user1 = userService!!.updateUser(salesrep)
		assertEquals(user1.userId, 1337)


		salesrep.email = "test2@unit.test"
		UserSingleton.user = salesrep
		val user2 = userService!!.updateUser(salesrep)
		assertEquals(user2.email, "test2@unit.test")
	}

	@Test
	fun updateUserNegative() {
		val salesrep1 = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			managerId = 1,
			userId = 1337
		)
		UserSingleton.user = salesrep1

		val salesrep2 = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			managerId = 1,
			userId = 1338
		)

		val exception = assertThrows<Exception> {
			userService!!.updateUser(salesrep2)
		}
		assertEquals(exception.message, "Only admins and the users themselves may update a user record")
	}

	@Test
	fun deleteUserPositive() {
		val admin = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.ADMIN
		)
		UserSingleton.user = admin

		val salesrep = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			managerId = 1,
			userId = 1337
		)

		assertDoesNotThrow { userService!!.deleteUser(salesrep) }
	}

	@Test
	fun deleteUserNegative() {
		val salesrep1 = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			managerId = 1,
			userId = 1337
		)
		UserSingleton.user = salesrep1

		val salesrep2 = User(
			"Admin",
			"Admin",
			"Admin",
			"admin@admin.admin",
			UserTypes.SALESREP,
			managerId = 1,
			userId = 1338
		)

		val exception = assertThrows<Exception> {
			userService!!.deleteUser(salesrep2)
		}
		assertEquals(exception.message, "Only admins may delete a user record")
	}
}
