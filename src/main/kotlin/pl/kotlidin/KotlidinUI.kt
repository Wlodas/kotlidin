package pl.kotlidin

import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.Button
import com.vaadin.ui.UI
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired

@Push
@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = "")
class KotlidinUI @Autowired constructor(private val personRepository: PersonRepository) : UI() {
	private val persons = BeanItemContainer(Person::class.java);
	
	private fun refreshRows() {
		persons.removeAllItems()
		persons.addAll(personRepository.findAll())
	}
	
	override fun init(request: VaadinRequest?) {
		refreshRows()
		
		content = verticalLayout() {
			setSizeFull()
			setMargin(true)
			isSpacing = true
			
			this += Button("Create person", { event ->
				personRepository.save(Person("ggg", "hhh"))
				refreshRows()
			})
			
			addComponentWithRatio(table("Persons", persons) {
				setSizeFull()
				addGeneratedColumn("", { table, itemId, columnId -> horizontalLayout() {
					isSpacing = true
					this += Button("Copy", { event ->
						val person = personRepository.findOne((itemId as Person).id)
						personRepository.save(person.copy(lastName = person.id.toString()))
						refreshRows()
					})
					this += Button("Delete", { event ->
						personRepository.delete(itemId as Person)
						refreshRows()
					})
				} })
				setVisibleColumns("id", "firstName", "lastName", "")
			}, ratio = 1F)
		}
	}
	
//	private val selectedPerson = ObjectProperty(null, Person::class.java)
//
//	override fun init(request: VaadinRequest) {
//		refreshRows()
//		
//		val createButton = Button("Create person", { event ->
//			personRepository.save(Person("ggg", "hhh"))
//			refreshRows()
//		})
//		
//		val copyButton = Button("Copy person", { event ->
//			val person = personRepository.findOne(selectedPerson.value.id)
//			personRepository.save(person.copy(lastName = person.id.toString()))
//			refreshRows()
//		})
//		copyButton.isEnabled = selectedPerson.value != null
//		selectedPerson.addValueChangeListener { event -> copyButton.isEnabled = selectedPerson.value != null }
//		
//		val table = Table("Persons", persons)
//		table.propertyDataSource = selectedPerson
//		table.isSelectable = true
//		table.isImmediate = true
//		table.setVisibleColumns("id", "firstName", "lastName")
//		
//		content = VerticalLayout(
//			HorizontalLayout(
//				createButton,
//				copyButton
//			),
//			table
//		)
//	}
	
//	override fun init(request: VaadinRequest) {
//		refreshRows()
//		
//		content = VerticalLayout(
//			HorizontalLayout(
//				Button("Create person", { event ->
//					personRepository.save(Person("ggg", "hhh"))
//					refreshRows()
//				}),
//				object : Button("Copy person", { event ->
//					val person = personRepository.findOne(selectedPerson.value.id)
//					personRepository.save(person.copy(lastName = person.id.toString()))
//					refreshRows()
//				}) {
//					init {
//						isEnabled = selectedPerson.value != null
//						selectedPerson.addValueChangeListener { event -> isEnabled = selectedPerson.value != null }
//					}
//				}
//			),
//			object : Table("Persons", persons) {
//				init {
//					propertyDataSource = selectedPerson
//					isSelectable = true
//					isImmediate = true
//					setVisibleColumns("id", "firstName", "lastName")
//				}
//			}
//		)
//	}
}