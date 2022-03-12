package com.p4ddy.paddycrm.adapters.user

import com.p4ddy.paddycrm.domain.user.User

/**
 * User converter class to convert user objects to user business entity objects and vice versa
 */
class UserConverter() {
	/**
	 * Converts a UserBE object to a User object
	 *
	 * @param userBE The UserBE object to convert
	 * @return The converted User object
	 */
	fun convertBEToUser(userBE: UserBE): User {
		val user = User(
			lastName = userBE.lastName,
			firstName = userBE.firstName,
			password = userBE.password,
			email = userBE.email,
			userType = userBE.userType,
			managerId = userBE.managerId,
			userId = userBE.userId,
			createdDate = userBE.createdDate
		)

		return user
	}

	/**
	 * Converts a User object to a UserBE object
	 *
	 * @param user The User object to convert
	 * @return The converted UserBE object
	 */
	fun convertUserToBE(user: User): UserBE {
		val userBE = UserBE(
			lastName = user.lastName,
			firstName = user.firstName,
			password = user.password,
			email = user.email,
			userType = user.userType,
			managerId = user.managerId,
			userId = user.userId,
			createdDate = user.createdDate
		)

		return userBE
	}
}
