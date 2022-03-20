package com.p4ddy.paddycrm.application.user

import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.domain.user.UserRepo
import com.p4ddy.paddycrm.domain.user.UserTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
		assertEquals("Only system administrators may create users", exceptionAdmin.message)

		val exceptionManager = assertThrows<Exception> {
			userService!!.createManager("Last", "First", "Password", "email@manager.org")
		}
		assertEquals("Only system administrators may create users", exceptionManager.message)

		val exceptionSalesrep = assertThrows<Exception> {
			userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)
		}
		assertEquals("Only system administrators may create users", exceptionSalesrep.message)
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
		assertEquals("Only system administrators may create users", exceptionAdmin.message)

		val exceptionManager = assertThrows<Exception> {
			userService!!.createManager("Last", "First", "Password", "email@manager.org")
		}
		assertEquals("Only system administrators may create users", exceptionManager.message)

		val exceptionSalesrep = assertThrows<Exception> {
			userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)
		}
		assertEquals("Only system administrators may create users", exceptionSalesrep.message)
	}

	@Test
	fun createUserNotLoggedIn() {
		UserSingleton.user = null

		val exceptionAdmin = assertThrows<Exception> {
			userService!!.createAdmin("Last", "First", "Password", "email@admin.org")
		}
		assertEquals("Can't create user when no user is logged in", exceptionAdmin.message)

		val exceptionManager = assertThrows<Exception> {
			userService!!.createManager("Last", "First", "Password", "email@manager.org")
		}
		assertEquals("Can't create user when no user is logged in", exceptionManager.message)

		val exceptionSalesrep = assertThrows<Exception> {
			userService!!.createSalesRep("Last", "First", "Password", "email@salesrep.org", 1337)
		}
		assertEquals("Can't create user when no user is logged in", exceptionSalesrep.message)
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
		assertEquals(1337, user1.userId)


		salesrep.email = "test2@unit.test"
		UserSingleton.user = salesrep
		val user2 = userService!!.updateUser(salesrep)
		assertEquals("test2@unit.test", user2.email)
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
		assertEquals("Only admins and the users themselves may update a user record", exception.message)
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
		assertEquals("Only admins may delete a user record", exception.message)
	}
}
