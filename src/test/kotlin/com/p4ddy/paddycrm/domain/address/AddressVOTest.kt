package com.p4ddy.paddycrm.domain.address

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AddressVOTest {
	@Test
	fun createAddressVOPositive() {
		val address = AddressVO(
			"Germany",
			"Karlsruhe",
			"76133",
			"Erzbergerstraße 121"
		)

		val address2 = AddressVO(
			"Country",
			"City",
			"1111",
			"Street Number"
		)
	}

	@Test
	fun createAddressVONegative() {
		val exception = assertThrows<Exception> {
			val address2 = AddressVO(
				"Country",
				"City",
				"111111",
				"Street Number"
			)
		}
		assertEquals("zipCode must be between 0 and 99999", exception.message)

		val exception2 = assertThrows<Exception> {
			val address2 = AddressVO(
				"Country",
				"City",
				"NotANumber",
				"Street Number"
			)
		}
		assertEquals("zipCode must be a number", exception2.message)

		val exception3 = assertThrows<Exception> {
			val address2 = AddressVO(
				"",
				"City",
				"12345",
				"Street Number"
			)
		}
		assertEquals("No empty values allowed in address", exception3.message)
	}

	@Test
	fun testToString() {
		val address = AddressVO(
			"Germany",
			"Karlsruhe",
			"76133",
			"Erzbergerstraße 121"
		)

		assertEquals("Erzbergerstraße 121, 76133 Karlsruhe, Germany", address.toString())
	}

	@Test
	fun copy() {
		val address = AddressVO(
			"Germany",
			"Karlsruhe",
			"76133",
			"Erzbergerstraße 121"
		)

		val copy = address.copy()

		assertEquals("Germany", copy.country)
		assertEquals("Karlsruhe", copy.city)
		assertEquals("76133", copy.zipCode)
		assertEquals("Erzbergerstraße 121", copy.streetAndNumber)
	}
}
