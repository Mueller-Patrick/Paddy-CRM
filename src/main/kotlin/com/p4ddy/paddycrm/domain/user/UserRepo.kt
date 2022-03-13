package com.p4ddy.paddycrm.domain.user

/**
 * User repo interface
 */
interface UserRepo {
	/**
	 * Find all users (that are visible to the current user)
	 */
	fun findAll(): List<User>

	/**
	 * Find the user by the given id
	 */
	fun findById(id: Int): User

	/**
	 * Find the user by the given email
	 */
	fun findByEmail(email: String): User

	/**
	 * Check, if the given password is valid for the user with the given id
	 */
	fun checkPasswordValid(userId: Int, password: String): Boolean

	/**
	 * Save the given user to the database
	 */
	fun save(user: User): User

	/**
	 * Update the given user in the database
	 */
	fun update(user: User): User

	/**
	 * Delete the given user in the database
	 */
	fun delete(user: User)
}
