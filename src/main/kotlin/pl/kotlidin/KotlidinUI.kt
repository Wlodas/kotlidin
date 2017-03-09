package pl.kotlidin

import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.data.BeanValidationBinder
import com.vaadin.server.ErrorMessage
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import org.springframework.data.domain.PageRequest
import pl.kotlidin.model.Person
import pl.kotlidin.model.PersonRepository


@Push
@Theme(ValoTheme.THEME_NAME)
@Title("Kotlidin Demo")
@SpringUI
class KotlidinUI(@JvmField protected final val personRepository: PersonRepository) : UI() {
	private lateinit var grid: Grid<Person>
	
	private fun savePerson(person: Person) {
		personRepository.save(person)
		grid.dataCommunicator.reset()
	}
	
	private fun deletePerson(person: Person) {
		personRepository.delete(person)
		grid.dataCommunicator.reset()
	}
	
	override fun init(request: VaadinRequest?) {
		content = VerticalLayout().apply {
			setSizeFull()
			
			this += button("Create person", KotlidinIcons.CREATE) { showPersonForm(Person()) }
			
			grid = Grid<Person>("Persons").apply {
				setSizeFull()
				setDataProvider(
					{ /* TODO */ sortOrder, offset, limit -> personRepository.findAll(PageRequest(offset / limit, limit)).content.stream() },
					{ personRepository.count().toInt() }
				)
				
				addColumn(Person::firstName::get).caption = "First Name"
				addColumn(Person::lastName::get).caption = "Last Name"
				addColumn(Person::gender::get).caption = "Gender"
				addColumn(Person::birthDate::get).caption = "Birth Date"
				
				setDetailsGenerator { person -> Panel().apply {
					styleName = ValoTheme.PANEL_BORDERLESS
					content = HorizontalLayout().apply {
						setMargin(true)
						this += button("Edit", KotlidinIcons.EDIT) { showPersonForm(person) }
						this += button("Copy", KotlidinIcons.COPY) { showPersonForm(person.copy()) }
						this += button("Delete", KotlidinIcons.DELETE) { deletePerson(person) }
					}
				} }
				asSingleSelect().addValueChangeListener { event ->
					grid.setDetailsVisible(event.oldValue, false)
					grid.setDetailsVisible(event.value, true)
				}
				
//				addColumn({ "Edit" }, ButtonRenderer { event -> showPersonForm(event.item) }).isSortable = false
//				addColumn({ "Copy" }, ButtonRenderer { event -> showPersonForm(event.item.copy()) }).isSortable = false
//				addColumn({ "Delete" }, ButtonRenderer { event -> deletePerson(event.item) }).isSortable = false
			}
			this.addComponentsAndExpand(grid)
		}
	}
	
	private class PersonForm : FormLayout() {
		val firstName = TextField("First Name")
		val lastName = TextField("Last Name")
		val gender = RadioButtonGroup("Gender", setOfAll<Person.Gender>())
		val birthDate = DateField("Birth Date").apply { dateFormat = "yyyy-MM-dd" }
		
		val saveButton = Button("Save", KotlidinIcons.SUBMIT)
		val cancelButton = Button("Cancel", KotlidinIcons.CLOSE)
		
		init {
			this += firstName
			this += lastName
			this += gender
			this += birthDate
			
			this += HorizontalLayout(saveButton, cancelButton)
		}
	}
	
	private fun showPersonForm(person: Person) {
		val form = PersonForm().apply {
			setMargin(true)
		}
		val binder = BeanValidationBinder(Person::class.java).apply {
			bindInstanceFields(form)
//			bind(form.firstName, Person::firstName.name)
//			bind(form.lastName, Person::lastName.name)
//			bind(form.gender, Person::gender.name)
//			bind(form.birthDate, Person::birthDate.name)
			readBean(person)
		}
		val window = Window(if (person.id == null) "New person" else "Edit person").apply {
			icon = KotlidinIcons.PERSON
			content = form
			isModal = true
			isResizable = false
			center()
		}
		
		form.saveButton.addClickListener {
			if (binder.validate().isOk) {
				binder.writeBean(person)
				savePerson(person)
				window.close()
			} else {
				form.saveButton.componentError = object : ErrorMessage {
					override fun getErrorLevel() = ErrorMessage.ErrorLevel.WARNING
					override fun getFormattedHtmlMessage() = "Form still contains some invalid fields"
				}
			}
		}
		form.cancelButton.addClickListener { window.close() }
		
		addWindow(window)
	}
}