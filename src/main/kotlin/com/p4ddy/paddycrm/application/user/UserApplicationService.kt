package com.p4ddy.paddycrm.application.user

import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.domain.user.UserRepo
import com.p4ddy.paddycrm.domain.user.UserTypes
import java.time.LocalDate

/**
 * User application service class
 *
 * @param userRepo User repository to use for persistence
 */
class UserApplicationService(
	val userRepo: UserRepo
) {
	/**
	 * Find all users
	 *
	 * @return A list of users that have been found
	 */
	fun findAllUsers(): List<User> {
		return userRepo.findAll()
	}

	/**
	 * Find a user by their id
	 *
	 * @param id The id of the user
	 * @return The user, if it exists
	 */
	fun findUserById(id: Int): User {
		return userRepo.findById(id)
	}

	/**
	 * Find a user by their id
	 *
	 * @param id The id of the user
	 * @return The user, if it exists
	 */
	fun findUserByEmail(email: String): User {
		return userRepo.findByEmail(email)
	}

	/**
	 * Create a new sales rep user and save it to the database
	 *
	 * @param lastName The last name of the user
	 * @param firstName The first name of the user
	 * @param password The password for this user
	 * @param email The email address of this user
	 * @param managerId The id of the user that is the manager of this user
	 * @return The created user object
	 */
	fun createSalesRep(
		lastName: String,
		firstName: String,
		password: String,
		email: String,
		managerId: Int
	): User {
		val user = User(lastName, firstName, password, email, UserTypes.SALESREP, managerId)
		return userRepo.save(user)
	}

	/**
	 * Create a new manager user and save it to the database
	 *
	 * @param lastName The last name of the user
	 * @param firstName The first name of the user
	 * @param password The password for this user
	 * @param email The email address of this user
	 * @return The created user object
	 */
	fun createManager(
		lastName: String,
		firstName: String,
		password: String,
		email: String,
		managerId: Int
	): User {
		val user = User(lastName, firstName, password, email, UserTypes.MANAGER)
		return userRepo.save(user)
	}

	/**
	 * Create a new admin user and save it to the database
	 *
	 * @param lastName The last name of the user
	 * @param firstName The first name of the user
	 * @param password The password for this user
	 * @param email The email address of this user
	 * @return The created user object
	 */
	fun createAdmin(
		lastName: String,
		firstName: String,
		password: String,
		email: String,
		managerId: Int
	): User {
		val user = User(lastName, firstName, password, email, UserTypes.ADMIN)
		return userRepo.save(user)
	}

	/**
	 * Update the given user in the database
	 *
	 * @param user: The user to update
	 * @return The updated user object
	 */
	fun updateUser(user: User): User {
		return userRepo.update(user)
	}

	/**
	 * Deletes the given user in the database
	 *
	 * @param user: The user to delete
	 */
	fun deleteUser(user: User) {
		return userRepo.delete(user)
	}
}
