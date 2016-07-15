package pl.kotlidin

import com.p6spy.engine.spy.appender.MessageFormattingStrategy

class MultiLineFormat : MessageFormattingStrategy {
	override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?): String {
		return when(sql!!.isNotEmpty()) {
			true -> "took $elapsed ms | $category | connection $connectionId\n$sql"
			else -> "took $elapsed ms | $category | connection $connectionId"
		}
	}
}