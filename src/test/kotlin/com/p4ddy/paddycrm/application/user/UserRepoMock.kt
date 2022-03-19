package com.p4ddy.paddycrm.application.user

import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.domain.user.UserRepo
import com.p4ddy.paddycrm.domain.user.UserTypes

internal class UserRepoMock : UserRepo {
	override fun findAll(): List<User> {
		return listOf(
			User(
				"Mueller",
				"Patrick",
				"PROTECTED",
				"patrick@mueller-patrick.tech",
				UserTypes.ADMIN,
				-1,
				1
			)
		)
	}

	override fun findById(id: Int): User {
		return User(
			"Mueller",
			"Patrick",
			"PROTECTED",
			"patrick@mueller-patrick.tech",
			UserTypes.ADMIN,
			-1,
			id
		)
	}

	override fun findByEmail(email: String): User {
		return User(
			"Mueller",
			"Patrick",
			"PROTECTED",
			email,
			UserTypes.ADMIN,
			-1,
			1
		)
	}

	override fun checkPasswordValid(userId: Int, password: String): Boolean {
		return true
	}

	override fun save(user: User): User {
		return user
	}

	override fun update(user: User): User {
		return user
	}

	override fun delete(user: User) {

	}
}
