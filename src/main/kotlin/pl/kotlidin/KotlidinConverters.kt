package pl.kotlidin

import com.vaadin.data.util.converter.Converter
import com.vaadin.data.util.converter.DefaultConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object DateToLocalDateConverter : Converter<Date, LocalDate> {
	override fun convertToModel(value: Date?, targetType: Class<out LocalDate>?, locale: Locale?): LocalDate? {
		return if(value != null) value.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDate() else null
	}
	
	override fun convertToPresentation(value: LocalDate?, targetType: Class<out Date>?, locale: Locale?): Date? {
		return if(value != null) Date.from(value.atStartOfDay(ZoneOffset.systemDefault()).toInstant()) else null
	}
	
	override fun getModelType() = LocalDate::class.java
	
	override fun getPresentationType() = Date::class.java
}

object DateToLocalDateTimeConverter : Converter<Date, LocalDateTime> {
	override fun convertToModel(value: Date?, targetType: Class<out LocalDateTime>?, locale: Locale?): LocalDateTime? {
		return if(value != null) value.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDateTime() else null
	}
	
	override fun convertToPresentation(value: LocalDateTime?, targetType: Class<out Date>?, locale: Locale?): Date? {
		return if(value != null) Date.from(value.atZone(ZoneOffset.systemDefault()).toInstant()) else null
	}
	
	override fun getModelType() = LocalDateTime::class.java
	
	override fun getPresentationType() = Date::class.java
}

object KotlidinConverterFactory : DefaultConverterFactory() {
	override fun <PRESENTATION, MODEL> findConverter(presentationType: Class<PRESENTATION>?, modelType: Class<MODEL>?): Converter<PRESENTATION, MODEL>? {
		if (presentationType === Date::class.java) {
			when (modelType) {
				LocalDate::class.java -> return DateToLocalDateConverter as? Converter<PRESENTATION, MODEL>
				LocalDateTime::class.java -> return DateToLocalDateTimeConverter as? Converter<PRESENTATION, MODEL>
			}
		}
		
		return super.findConverter(presentationType, modelType)
	}
}