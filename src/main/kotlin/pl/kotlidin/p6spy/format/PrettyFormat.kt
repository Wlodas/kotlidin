package pl.kotlidin.p6spy.format

import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl

class PrettyFormat : MessageFormattingStrategy {
	private val formatter = BasicFormatterImpl()
	
	override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?): String {
		return when(sql.isNullOrEmpty()) {
			false -> "took $elapsed ms | $category | connection $connectionId${formatter.format(sql)}"
			else -> "took $elapsed ms | $category | connection $connectionId"
		}
	}
}