package pl.kotlidin

import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.data.fieldgroup.BeanFieldGroup
import com.vaadin.data.fieldgroup.FieldGroup
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.server.ErrorMessage
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired

@Push
@Theme(ValoTheme.THEME_NAME)
@Title("Kotlidin Demo")
@SpringUI
class KotlidinUI @Autowired constructor(private val personRepository: PersonRepository) : UI(), Loggable {
	private val persons = BeanItemContainer(Person::class.java);
	
	private fun refreshRows() {
		persons.removeAllItems()
		persons.addAll(personRepository.findAll())
	}
	
	private fun savePerson(person: Person) {
		logger.info { "Saving person: $person" }
		personRepository.save(person)
		refreshRows()
	}
	
	private fun deletePerson(person: Person) {
		logger.info { "Deleting person: $person" }
		personRepository.delete(person)
		refreshRows()
	}
	
	override fun init(request: VaadinRequest?) {
		session.converterFactory = KotlidinConverterFactory
		refreshRows()
		
		// TODO: remove in Kotlin 1.1
		val cachedSavePersonFunction: (Person) -> Unit = { savePerson(it) }
		
		content = verticalLayout() {
			setSizeFull()
			setMargin(true)
			isSpacing = true
			
			this += button("Create person", KotlidinIcons.CREATE, Button.ClickListener {
				showPersonForm("New person", Person(), cachedSavePersonFunction)
			})
			
			this += table("Persons", persons) {
				setSizeFull()
				addGeneratedColumn("", { table, itemId, columnId ->
					horizontalLayout() {
						isSpacing = true
						this += button("Edit", KotlidinIcons.EDIT, Button.ClickListener {
							showPersonForm("Edit person", itemId as Person, cachedSavePersonFunction)
						})
						this += button("Copy", KotlidinIcons.COPY, Button.ClickListener {
							showPersonForm("New person", (itemId as Person).copy(), cachedSavePersonFunction)
						})
						this += button("Delete", KotlidinIcons.DELETE, Button.ClickListener { deletePerson(itemId as Person) })
					}
				})
				setVisibleColumns("id", "firstName", "lastName", "gender", "birthDate", "")
				setColumnHeaders("Id", "First name", "Last name", "Gender", "Birth date", "")
			} withExpandRatio 1F
		}
	}
	
	private class PersonForm : FormLayout() {
		val firstName = TextField("First name")
		val lastName = TextField("Last name")
		val gender = OptionGroup("Gender", setOfAll<Person.Gender>())
		val birthDate = DateField("Birth date")
		
		val saveButton = Button("Save", KotlidinIcons.SUBMIT)
		val cancelButton = Button("Cancel", KotlidinIcons.CLOSE)
		
		init {
			firstName.isNullSettingAllowed = true
			firstName.nullRepresentation = ""
			
			lastName.isNullSettingAllowed = true
			lastName.nullRepresentation = ""
			
			birthDate.dateFormat = "yyyy-MM-dd"
			
			this += firstName
			this += lastName
			this += gender
			this += birthDate
			
			this += horizontalLayout(saveButton, cancelButton) {
				isSpacing = true
			}
		}
	}
	
	private fun showPersonForm(formCaption: String, person: Person, onSubmit: (Person) -> Unit) {
		val form = PersonForm()
		val beanFieldGroup = BeanFieldGroup.bindFieldsBuffered(person, form)
		val window = Window(formCaption)
		
		form.setMargin(true)
		form.saveButton.addClickListener {
			try {
				beanFieldGroup.commit();
				onSubmit(person);
				window.close();
			} catch (e: FieldGroup.CommitException) {
				when (e.cause) {
					is FieldGroup.FieldGroupInvalidValueException -> {
						form.saveButton.componentError = object : ErrorMessage {
							override fun getErrorLevel() = ErrorMessage.ErrorLevel.WARNING
							override fun getFormattedHtmlMessage() = "Form still contains some invalid fields"
						}
					}
					else -> throw e
				}
			}
		}
		form.cancelButton.addClickListener { window.close(); }
		
		window.icon = KotlidinIcons.PERSON
		window.content = form
		window.isModal = true
		window.isResizable = false
		window.center()
		addWindow(window)
	}
}