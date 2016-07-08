package pl.kotlidin

import com.vaadin.data.Container
import com.vaadin.server.Resource
import com.vaadin.ui.*

inline fun button(caption: String, init: Button.() -> Unit) : Button {
	val button = Button(caption)
	button.init()
	return button
}

inline fun button(caption: String, listener: Button.ClickListener, init: Button.() -> Unit) : Button {
	val button = Button(caption, listener)
	button.init()
	return button
}

inline fun button(caption: String, icon: Resource, listener: Button.ClickListener) : Button {
	val button = Button(caption, listener)
	button.icon = icon
	return button
}

inline fun table(init: Table.() -> Unit) : Table {
	val table = Table()
	table.init()
	return table
}

inline fun table(caption: String, dataSource: Container, init: Table.() -> Unit) : Table {
	val table = Table(caption, dataSource)
	table.init()
	return table
}

inline fun verticalLayout(init: VerticalLayout.() -> Unit) : VerticalLayout {
	val layout = VerticalLayout()
	layout.init()
	return layout
}

inline fun verticalLayout(vararg children: Component, init: VerticalLayout.() -> Unit) : VerticalLayout {
	val layout = VerticalLayout(*children)
	layout.init()
	return layout
}

inline fun horizontalLayout(init: HorizontalLayout.() -> Unit) : HorizontalLayout {
	val layout = HorizontalLayout()
	layout.init()
	return layout
}

inline fun horizontalLayout(vararg children: Component, init: HorizontalLayout.() -> Unit) : HorizontalLayout {
	val layout = HorizontalLayout(*children)
	layout.init()
	return layout
}

inline operator fun ComponentContainer.plusAssign(component: Component) = this.addComponent(component)

inline operator fun ComponentContainer.minusAssign(component: Component) = this.removeComponent(component)

class ComponentWithExpandRatio(val component: Component, val expandRatio: Float)

inline infix fun Component.withExpandRatio(expandRatio: Float) = ComponentWithExpandRatio(this, expandRatio)

inline operator fun AbstractOrderedLayout.plusAssign(wrapper: ComponentWithExpandRatio) {
	this.addComponent(wrapper.component)
	this.setExpandRatio(wrapper.component, wrapper.expandRatio)
}