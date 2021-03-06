package com.p4ddy.paddycrm.domain.user

import java.time.LocalDate

/**
 * User domain entity
 *
 * @property lastName The last name of the user
 * @property firstName The first name of the user
 * @property password The password of the user
 * @property email The email of the user
 * @property userType What type of users this user is
 * @property managerId The manager of a sales rep. Only fill out for sales rep, leave blank for other user types
 * @property userId The unique id of the user
 * @property createdDate The creation date of this user
 *
 * @constructor Creates a new user entity
 */
data class User(
	var lastName: String,
	var firstName: String,
	var password: String,
	var email: String,
	val userType: UserTypes = UserTypes.SALESREP,
	val managerId: Int = -1,
	val userId: Int = -1,
	val createdDate: LocalDate = LocalDate.now()
) {
	init {
		checkForInvalidParams()
	}

	/**
	 * Checks, if the combination of user type and provided manager id is valid and throws an error if this is not the case
	 */
	private fun checkForInvalidParams() {
		if (userType == UserTypes.SALESREP && managerId == -1) {
			throw Exception("Sales Reps have to have a manager assigned!")
		} else if (userType == UserTypes.MANAGER && managerId != -1) {
			throw Exception("Mangers must not have a manager assigned!")
		} else if (userType == UserTypes.ADMIN && managerId != -1) {
			throw Exception("Admins must not have a manager assigned!")
		}
	}
}
