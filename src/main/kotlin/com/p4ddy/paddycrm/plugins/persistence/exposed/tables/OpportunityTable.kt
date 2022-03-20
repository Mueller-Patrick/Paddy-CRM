package com.p4ddy.paddycrm.plugins.persistence.exposed.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object OpportunityTable : Table("opportunity") {
	val opportunityId: Column<Int> = integer("opportunity_id").autoIncrement()
	val name: Column<String> = text("name")
	val accountId: Column<Int> = integer("account_id").references(
		AccountTable.accountId,
		onDelete = ReferenceOption.CASCADE,
		onUpdate = ReferenceOption.CASCADE
	)
	val amount: Column<Float> = float("amount")
	val closeDate: Column<LocalDate> = date("close_date")
	val ownerId: Column<Int> = integer("owner_id").references(
		UserTable.userId,
		onDelete = ReferenceOption.CASCADE,
		onUpdate = ReferenceOption.CASCADE
	)
	val product: Column<String> = text("product")
	val probability: Column<Int> = integer("probability")
	val quantity: Column<Int> = integer("quantity")
	val stage: Column<String> = text("stage")
	val createdDate: Column<LocalDate> = date("created_date")

	override val primaryKey = PrimaryKey(opportunityId)
}
