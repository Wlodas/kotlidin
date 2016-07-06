package pl.kotlidin

import javax.persistence.Entity

@Entity
class Person(
	var firstName: String? = null,
	var lastName: String? = null
) : AbstractEntity<Long>()