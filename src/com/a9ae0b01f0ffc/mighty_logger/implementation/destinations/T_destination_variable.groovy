package com.a9ae0b01f0ffc.mighty_logger.implementation.destinations

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

class T_destination_variable extends T_destination {

    static String p_log_line = T_s.c().GC_EMPTY_STRING

    @Override
    void store(ArrayList<I_trace> i_trace_list) {
        p_log_line = p_formatter.format_traces(i_trace_list)
    }

}