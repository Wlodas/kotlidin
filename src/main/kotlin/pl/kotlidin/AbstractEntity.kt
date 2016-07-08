package pl.kotlidin

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<ID : Serializable> {
	@Id
	@GeneratedValue
	var id: ID? = null
		private set
	
	override fun hashCode(): Int {
		return id?.hashCode() ?: super.hashCode()
	}
	
	override fun equals(other: Any?): Boolean {
		if (other === this) return true
		if (other !is AbstractEntity<*> || other.javaClass !== this.javaClass) return false
		return this.id != null && this.id == other.id
	}
}