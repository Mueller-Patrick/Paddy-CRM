package com.p4ddy.paddycrm.domain.user

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
 *
 * @constructor Creates a new user entity
 */
class User(
	var lastName: String,
	var firstName: String,
	var password: String,
	var email: String,
	val userType: UserTypes = UserTypes.SALESREP,
	val managerId: Int = -1,
	val userId: Int = -1
) {
	init {
		if (userType == UserTypes.SALESREP && managerId == -1) {
			throw Exception("Sales Reps have to have a manager assigned!")
		} else if (userType == UserTypes.MANAGER && managerId != -1) {
			throw Exception("Mangers must not have a manager assigned!")
		} else if (userType == UserTypes.ADMIN && managerId != -1) {
			throw Exception("Admins must not have a manager assigned!")
		}
	}
}
