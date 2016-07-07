package pl.kotlidin

import org.hibernate.validator.constraints.NotBlank
import javax.persistence.Column
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Person(
	@field:NotBlank
	@Column(nullable = false)
	var firstName: String? = null,
	
	@field:NotBlank
	@Column(nullable = false)
	var lastName: String? = null
) : AbstractEntity<Long>() {
	fun copy(firstName: String? = this.firstName, lastName: String? = this.lastName): Person {
		return Person(firstName = firstName, lastName = lastName)
	}
}