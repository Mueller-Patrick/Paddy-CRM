package com.p4ddy.paddycrm.adapters.user

import com.p4ddy.paddycrm.domain.user.UserTypes
import java.time.LocalDate

/**
 * User Business Entity for usage in the GUI
 *
 * @property lastName The last name of the user
 * @property firstName The first name of the user
 * @property password The password of the user
 * @property email The email of the user
 * @property userType What type of users the user is
 * @property managerId The manager of a sales rep. Only fill out for sales rep, leave blank for other user types
 * @property userId The unique id of the user
 * @property createdDate The creation date of the user
 */
data class UserBE(
	var lastName: String,
	var firstName: String,
	var password: String,
	var email: String,
	val managerName: String,
	var managerId: Int = -1,
	var userType: UserTypes = UserTypes.SALESREP,
	val userId: Int = -1,
	val createdDate: LocalDate = LocalDate.now()
)
