package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_maskable
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_trace implements I_trace {

    Boolean p_muted = T_s.c().GC_FALSE
    String p_mask = T_s.c().GC_EMPTY_STRING
    I_trace_formatter p_trace_formatter = T_s.c().GC_NULL_OBJ_REF as I_trace_formatter
    String p_name = T_s.c().GC_EMPTY_STRING
    Object p_ref = T_s.c().GC_NULL_OBJ_REF
    String p_value = T_s.c().GC_EMPTY_STRING
    String p_source = T_s.c().GC_EMPTY_STRING
    String p_config_class = T_s.c().GC_EMPTY_STRING

    @Override
    Boolean is_muted() {
        return p_muted
    }

    @Override
    Boolean is_masked() {
        if (p_mask == T_s.c().GC_EMPTY_STRING || p_mask == T_s.c().GC_FALSE_STRING) {
            return T_s.c().GC_FALSE
        } else {
            return T_s.c().GC_TRUE
        }
    }

    @Override
    void set_muted(Boolean i_is_muted) {
        p_muted = i_is_muted
    }

    @Override
    void set_mask(String i_mask) {
        p_mask = i_mask
    }

    @Override
    String get_mask() {
        return p_mask
    }

    @Override
    String toString() {
        if (p_ref == T_s.c().GC_NULL_OBJ_REF && (p_value == T_s.c().GC_NULL_OBJ_REF || p_value == T_s.c().GC_EMPTY_STRING || p_value == T_s.c().GC_DEFAULT_TRACE)) {
            return T_s.c().GC_DEFAULT_TRACE
        } else if (p_muted) {
            return T_s.c().GC_DEFAULT_TRACE_MUTED
        } else if (p_mask) {
            if (p_ref instanceof I_maskable) {
                if (p_value != T_s.c().GC_EMPTY_STRING) {
                    return T_s.c().GC_DEFAULT_TRACE_MASKED
//there is no control on how objects are serialized, thus it is unknown how to mask their serialized representation.
                } else {
                    return ((I_maskable) p_ref).to_string_masked(p_mask)
                }
            } else {
                return T_s.c().GC_DEFAULT_TRACE_MASKED
            }
        } else {
            if (p_value != T_s.c().GC_EMPTY_STRING) {
                return p_value
            } else {
                return p_ref.toString()
            }
        }
    }

    @Override
    void set_formatter(I_trace_formatter i_trace_formatter) {
        p_trace_formatter = i_trace_formatter
    }

    @Override
    I_trace_formatter get_formatter() {
        return p_trace_formatter
    }

    @Override
    String get_name() {
        return p_name
    }

    @Override
    String get_search_name_config() {
        String l_result = T_s.c().GC_EMPTY_STRING
        if (get_ref() != T_s.c().GC_NULL_OBJ_REF && p_config_class != T_s.c().GC_EMPTY_STRING) {
            l_result = get_ref().getClass().getCanonicalName()
        } else if (get_ref() == T_s.c().GC_NULL_OBJ_REF && p_config_class != T_s.c().GC_EMPTY_STRING) {
            l_result = p_config_class
        } else {
            l_result = p_name
        }
        return l_result
    }

    @Override
    String get_ref_class_name() {
        String l_result = T_s.c().GC_EMPTY_STRING
        if (get_ref() != T_s.c().GC_NULL_OBJ_REF) {
            l_result = get_ref().getClass().getCanonicalName()
        } else {
            l_result = p_name
        }
        return l_result
    }

    @Override
    void set_name(String i_name) {
        p_name = i_name
    }

    @Override
    Object get_ref() {
        return p_ref
    }

    @Override
    void set_ref(Object i_ref) {
        p_ref = i_ref
    }

    @Override
    void set_val(String i_value) {
        p_value = i_value
    }

    @Override
    String get_val() {
        return p_value
    }

    @Override
    void set_source(String i_source) {
        p_source = i_source
    }

    @Override
    String get_source() {
        return p_source
    }

    @Override
    void set_class(String i_class) {
        p_config_class = i_class
    }

    @Override
    String get_config_class() {
        return p_config_class
    }

    Boolean match_trace(I_trace i_trace_new) {
        return (get_search_name_config() == i_trace_new.get_ref_class_name() || get_search_name_config() == i_trace_new.get_name())
    }

}