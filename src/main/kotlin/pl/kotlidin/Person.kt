package pl.kotlidin

import org.hibernate.validator.constraints.NotBlank
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

@Entity
class Person(
	@field:NotBlank
	@Column(nullable = false)
	var firstName: String? = null,
	
	@field:NotBlank
	@Column(nullable = false)
	var lastName: String? = null,
	
	@field:NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var gender: Gender? = null
) : AbstractEntity<Long>() {
	enum class Gender {
		MALE, FEMALE;
		
		companion object {
			@JvmField val ALL = setOfAll<Gender>()
		}
	}
	
	fun copy(firstName: String? = this.firstName, lastName: String? = this.lastName, gender: Gender? = this.gender): Person {
		return Person(firstName = firstName, lastName = lastName, gender = gender)
	}
}