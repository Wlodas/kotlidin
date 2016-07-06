package pl.kotlidin

import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.data.fieldgroup.BeanFieldGroup
import com.vaadin.data.fieldgroup.FieldGroup
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.data.validator.BeanValidator
import com.vaadin.server.ErrorHandler
import com.vaadin.server.ErrorMessage
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
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
				showPersonForm("New person", Person(), {
					personRepository.save(it)
					refreshRows()
				})
			})
			
			this += table("Persons", persons) {
				setSizeFull()
				addGeneratedColumn("", { table, itemId, columnId -> horizontalLayout() {
					isSpacing = true
					this += Button("Edit", { event ->
						showPersonForm("Edit person", itemId as Person, {
							personRepository.save(it)
							refreshRows()
						})
					})
					this += Button("Copy", { event ->
						showPersonForm("New person", (itemId as Person).copy(), {
							personRepository.save(it)
							refreshRows()
						})
					})
					this += Button("Delete", { event ->
						personRepository.delete(itemId as Person)
						refreshRows()
					})
				} })
				setVisibleColumns("id", "firstName", "lastName", "")
				setColumnHeaders("Id", "First name", "Last name", "")
			} withExpandRatio 1F
		}
	}
	
	private class PersonForm : FormLayout() {
		val firstName = TextField("First name")
		val lastName = TextField("Last name")
		val saveButton = Button("Save")
		val cancelButton = Button("Cancel")
		
		init {
			firstName.nullRepresentation = ""
			lastName.nullRepresentation = ""
			
			this += firstName
			this += lastName
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
			} catch(e: FieldGroup.CommitException) {
				when(e.cause) {
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
		
		window.content = form
		window.isModal = true
		window.isResizable = false
		window.center()
		addWindow(window)
	}
}