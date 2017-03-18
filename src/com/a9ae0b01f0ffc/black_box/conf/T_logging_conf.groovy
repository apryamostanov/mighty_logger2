package com.a9ae0b01f0ffc.black_box.conf

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_4_const
import com.a9ae0b01f0ffc.commons.implementation.config.T_common_conf
import groovy.transform.WithReadLock
import groovy.util.logging.Log

@Log
class T_logging_conf extends T_common_conf {

    String GC_BLACK_BOX_ENABLED = T_logging_base_4_const.GC_TRUE_STRING
    String GC_DEFAULT_LOGGER_CONF_FILE_NAME = T_logging_base_4_const.GC_EMPTY_STRING
    String GC_LOG_CSV_SEPARATOR = "|"
    String GC_ARGUMENT_POSITIONAL_NAME = "Argument#"
    String GC_DEFAULT_CLASS_NAME = "Unknown Class"
    String GC_DEFAULT_METHOD_NAME = "Unknown Method"
    String GC_DEFAULT_GUID = "Unknown GUID"
    String GC_DEFAULT_TRACE = "Trace missing"
    String GC_DEFAULT_TRACE_MASKED = "Trace masked"
    String GC_DEFAULT_TRACE_MUTED = "Trace muted"
    String GC_DEFAULT_TRACE_NAME = "unnamed"
    String GC_XML_PAD_DEPTH = "4"
    String GC_JUL_CONF_FILE_NAME = "./conf/logging.properties"
    String GC_PROFILE_ALL = "true"
    String GC_IGNORE_INTERNAL_METHODS_PACKAGE_NAME = "a9ae0b01f0ffc"

    T_logging_conf() {
        super()
    }

    T_logging_conf(String i_conf_file_name) {
        super(i_conf_file_name)
        GC_BLACK_BOX_ENABLED = GC_CONST_CONF.GC_BLACK_BOX_ENABLED(GC_BLACK_BOX_ENABLED)
        GC_DEFAULT_LOGGER_CONF_FILE_NAME = GC_CONST_CONF.GC_DEFAULT_LOGGER_CONF_FILE_NAME(GC_DEFAULT_LOGGER_CONF_FILE_NAME)
        GC_LOG_CSV_SEPARATOR = GC_CONST_CONF.GC_LOG_CSV_SEPARATOR(GC_LOG_CSV_SEPARATOR)
        GC_ARGUMENT_POSITIONAL_NAME = GC_CONST_CONF.GC_ARGUMENT_POSITIONAL_NAME(GC_ARGUMENT_POSITIONAL_NAME)
        GC_DEFAULT_CLASS_NAME = GC_CONST_CONF.GC_DEFAULT_CLASS_NAME(GC_DEFAULT_CLASS_NAME)
        GC_DEFAULT_METHOD_NAME = GC_CONST_CONF.GC_DEFAULT_METHOD_NAME(GC_DEFAULT_METHOD_NAME)
        GC_DEFAULT_GUID = GC_CONST_CONF.GC_DEFAULT_GUID(GC_DEFAULT_GUID)
        GC_DEFAULT_TRACE = GC_CONST_CONF.GC_DEFAULT_TRACE(GC_DEFAULT_TRACE)
        GC_DEFAULT_TRACE_MASKED = GC_CONST_CONF.GC_DEFAULT_TRACE_MASKED(GC_DEFAULT_TRACE_MASKED)
        GC_DEFAULT_TRACE_MUTED = GC_CONST_CONF.GC_DEFAULT_TRACE_MUTED(GC_DEFAULT_TRACE_MUTED)
        GC_XML_PAD_DEPTH = GC_CONST_CONF.GC_XML_PAD_DEPTH(GC_XML_PAD_DEPTH)
        GC_DEFAULT_TRACE_NAME = GC_CONST_CONF.GC_DEFAULT_TRACE_NAME(GC_DEFAULT_TRACE_NAME)
        GC_JUL_CONF_FILE_NAME = GC_CONST_CONF.GC_JUL_CONF_FILE_NAME(GC_JUL_CONF_FILE_NAME)
        GC_PROFILE_ALL = GC_CONST_CONF.GC_PROFILE_ALL(GC_PROFILE_ALL)
        GC_IGNORE_INTERNAL_METHODS_PACKAGE_NAME = GC_CONST_CONF.GC_IGNORE_INTERNAL_METHODS_PACKAGE_NAME(GC_IGNORE_INTERNAL_METHODS_PACKAGE_NAME)
    }

}