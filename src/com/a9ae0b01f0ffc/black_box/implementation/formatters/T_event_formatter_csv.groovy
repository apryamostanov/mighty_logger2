package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_csv extends T_event_formatter {

    String format_event(T_event i_source_event) {
        return i_source_event.get_event_type() + c().GC_LOG_CSV_SEPARATOR + i_source_event.get_datetimestamp() + c().GC_LOG_CSV_SEPARATOR + nvl(i_source_event.get_execution_node().get_throwable()?.getMessage(), i_source_event.get_message()) + System.lineSeparator()
    }
}
