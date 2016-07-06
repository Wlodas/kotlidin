package pl.kotlidin

import com.vaadin.data.Container
import com.vaadin.ui.*
import java.math.BigInteger

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
	BigInteger.valueOf(1L) + BigInteger.valueOf(1L)
	return layout
}

inline operator fun ComponentContainer.plusAssign(component: Component) = this.addComponent(component)

inline operator fun ComponentContainer.minusAssign(component: Component) = this.removeComponent(component)

inline fun AbstractOrderedLayout.addComponentWithRatio(component: Component, ratio: Float) {
	this.addComponent(component)
	this.setExpandRatio(component, ratio)
}