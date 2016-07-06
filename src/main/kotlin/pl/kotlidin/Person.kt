package pl.kotlidin

import org.hibernate.validator.constraints.NotBlank
import javax.persistence.Column
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Person(
	@field:NotNull
	@field:NotBlank
	@field:Column(nullable = false)
	var firstName: String? = null,
	
	@field:NotNull
	@field:NotBlank
	@field:Column(nullable = false)
	var lastName: String? = null
) : AbstractEntity<Long>()