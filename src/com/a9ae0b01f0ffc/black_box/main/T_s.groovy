package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.commons.ioc.T_class_loader
import com.a9ae0b01f0ffc.commons.main.T_common_shortcuts
import com.a9ae0b01f0ffc.commons.static_string.T_static_string

class T_s extends T_common_shortcuts {

    static I_logger l() {
        return x().get_logger()
    }

    static I_trace r(Object i_object, String i_trace_name) {
        I_trace l_trace = l().object2trace(i_object, T_logging_const.GC_TRACE_SOURCE_RUNTIME)
        l_trace.set_name(i_trace_name)
        return l_trace
    }

    static I_trace t(Object i_object, T_static_string i_trace_name) {
        return r(i_object, i_trace_name.toString())
    }

    static T_logging_context x() {
        return (T_logging_context) T_logging_context.get_context()
    }

    static T_logging_commons c() {
        return x().get_commons()
    }

    static T_class_loader ioc() {
        return x().get_ioc()
    }

}
