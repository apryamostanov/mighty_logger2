package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.black_box.interfaces.I_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_method_invocation
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.static_string.T_static_string

class T_logger implements I_logger {

    private ArrayList<I_destination> p_destinations = new ArrayList<I_destination>()
    private String p_logger_id = UUID.randomUUID().toString()
    private Stack<I_method_invocation> p_method_invocation_stack = new LinkedList<I_method_invocation>()
    private ArrayList<I_trace> p_trace_context_list = new ArrayList<I_trace>()
    private I_method_invocation PC_DEFAULT_METHOD_INVOCATION = T_s.c().GC_NULL_OBJ_REF as I_method_invocation
    private String p_mode = T_s.c().GC_EMPTY_STRING
    private Boolean p_is_init = init()
    private HashMap<String, Integer> p_statistics_method_calls = new HashMap<String, Integer>()

    @Override
    Boolean init() {
        PC_DEFAULT_METHOD_INVOCATION = T_s.ioc().instantiate("I_method_invocation") as I_method_invocation
        PC_DEFAULT_METHOD_INVOCATION.set_class_name(T_s.c().GC_DEFAULT_CLASS_NAME)
        PC_DEFAULT_METHOD_INVOCATION.set_method_name(T_s.c().GC_DEFAULT_METHOD_NAME)
        return T_s.c().GC_TRUE
    }

    @Override
    void set_mode(String i_mode) {
        p_mode = i_mode
    }

    @Override
    I_event create_event(String i_event_type, String i_class_name, String i_method_name) {
        I_event l_event = T_s.ioc().instantiate(i_event_type) as I_event
        l_event.set_event_type(i_event_type)
        l_event.set_class_name(i_class_name)
        l_event.set_method_name(i_method_name)
        l_event.set_depth(p_method_invocation_stack.size())
        l_event.set_datetimestamp(new Date())
        return l_event
    }

    @Override
    I_trace spawn_trace(I_trace i_trace_new, I_trace i_trace_config) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace.set_name(i_trace_new.get_name())
        l_trace.set_source(i_trace_new.get_source())
        if (i_trace_config != T_s.c().GC_NULL_OBJ_REF) {
            l_trace.set_mask(i_trace_config.get_mask())
            l_trace.set_muted(i_trace_config.is_muted())
            l_trace.set_source(T_s.nvl(l_trace.get_source(), i_trace_config.get_source()) as String)
            l_trace.set_formatter(i_trace_config.get_formatter())
            l_trace.set_class(i_trace_config.get_config_class())
        }
        if (p_mode == T_s.c().GC_LOGGER_MODE_PRODUCTION) {
            l_trace.set_ref(i_trace_new.get_ref())
            l_trace.set_val(i_trace_new.get_val())

        } else if (p_mode == T_s.c().GC_LOGGER_MODE_DIAGNOSTIC) {
            l_trace.set_ref(i_trace_new.get_ref())
            if (i_trace_new.get_val() != T_s.c().GC_EMPTY_STRING) {
                l_trace.set_val(i_trace_new.get_val())
            } else {
                l_trace.set_val(i_trace_new.toString())
            }
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_LOGGER_MODE, p_mode)
        }
        return l_trace
    }

    @Override
    I_trace object2trace(Object i_object, String i_source) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        if (p_mode == T_s.c().GC_LOGGER_MODE_PRODUCTION) {
            l_trace.set_ref(i_object)
        } else if (p_mode == T_s.c().GC_LOGGER_MODE_DIAGNOSTIC) {
            l_trace.set_ref(i_object)
            l_trace.set_val(i_object.toString())
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_LOGGER_MODE, p_mode)
        }
        l_trace.set_source(i_source)
        return l_trace
    }

    @Override
    ArrayList<I_trace> objects2traces(Collection<Object> i_objects, String i_source) {
        ArrayList<I_trace> l_method_args = new ArrayList<I_trace>()
        for (Object l_object : i_objects) {
            if (l_object instanceof I_trace) {
                l_method_args.add((I_trace) l_object)
            } else {
                I_trace l_trace = object2trace(l_object, i_source)
                l_method_args.add(l_trace)
            }
        }
        return l_method_args
    }

    @Override
    ArrayList<I_trace> objects2traces_array(Object[] i_objects, String i_source) {
        return objects2traces(Arrays.asList(i_objects), i_source)
    }

    void pop() {
        if (!p_method_invocation_stack.isEmpty()) {
            p_method_invocation_stack.pop()
        }
    }

    @Override
    void log_generic(I_event i_event) {
        for (I_destination l_destination : p_destinations) {
            l_destination.log_generic(i_event)
        }
    }

    @Override
    ArrayList<Object> get_trace_context_list() {
        return p_trace_context_list
    }

    @Override
    void put_to_context(Object i_object, String i_name) {
        I_trace l_trace = object2trace(i_object, T_s.c().GC_TRACE_SOURCE_CONTEXT)
        l_trace.set_name(i_name)
        p_trace_context_list.add(l_trace)
    }

    @Override
    void add_destination(I_destination i_destination) {
        p_destinations.add(i_destination)
    }

    @Override
    String get_logger_id() {
        p_logger_id
    }

    static Boolean method_arguments_exist(Object[] i_traces) {
        return (i_traces != T_s.c().GC_SKIPPED_ARG && i_traces != T_s.c().GC_NULL_OBJ_REF && i_traces.size() > T_s.c().GC_EMPTY_SIZE)
    }

    @Override
    void print_stats() {
        TreeMap<Integer, String> l_sorted_statistics_method_calls = new TreeMap<Integer, String>()
        for (String l_key in p_statistics_method_calls.keySet()) {
            l_sorted_statistics_method_calls.put(p_statistics_method_calls.get(l_key), l_key)
        }
        for (Map.Entry<Float, String> l_entry : l_sorted_statistics_method_calls.entrySet()) {
            System.out.println(l_entry.getValue() + ":" + l_entry.getKey())
        }
    }

    @Override
    void log_enter(String i_class_name, String i_method_name, I_trace... i_traces = T_s.c().GC_SKIPPED_ARG) {
        I_method_invocation l_method_invocation = T_s.ioc().instantiate("I_method_invocation") as I_method_invocation
        l_method_invocation.set_class_name(i_class_name)
        l_method_invocation.set_method_name(i_method_name)
        ArrayList<I_trace> l_method_arguments = objects2traces_array(i_traces, T_s.c().GC_TRACE_SOURCE_RUNTIME)
        l_method_invocation.set_method_arguments(l_method_arguments)
        p_method_invocation_stack.push(l_method_invocation)
        I_event l_event = create_event("enter", i_class_name, i_method_name)
        if (method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(l_method_arguments)
        }
        log_generic(l_event)
        String l_stat_key = i_class_name + "->" + i_method_name
        if (p_statistics_method_calls.containsKey(l_stat_key)) {
            p_statistics_method_calls.put(l_stat_key, p_statistics_method_calls.get(l_stat_key) + 1)
        } else {
            p_statistics_method_calls.put(l_stat_key, 1)
        }
    }

    @Override
    void log_exit(String i_class_name, String i_method_name, I_trace... i_traces = T_s.c().GC_SKIPPED_ARG as I_trace[]) {
        I_event l_event = create_event("exit", i_class_name, i_method_name)
        if (method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, T_s.c().GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
        pop()
    }

    @Override
    Object log_exit_automatic(String i_class_name, String i_method_name, I_trace i_return_object_trace) {
        log_exit(i_class_name, i_method_name, i_return_object_trace)
        return i_return_object_trace.get_ref() //wow smart code like a real programmar is doin lol
    }

    @Override
    void log_exception(String i_class_name, String i_method_name, Exception i_exception, I_trace... i_traces = T_s.c().GC_SKIPPED_ARG) {
        I_event l_event = create_event("error", i_class_name, i_method_name)
        l_event.set_exception(i_exception)
        l_event.add_traces_runtime(objects2traces_array(i_traces, T_s.c().GC_TRACE_SOURCE_RUNTIME))
        l_event.add_traces_runtime(get_current_method_invocation().get_method_arguments())
        log_generic(l_event)
        pop()
    }

    @Override
    void log_debug(T_static_string i_static_string_message, I_trace... i_traces = T_s.c().GC_SKIPPED_ARG) {
        I_event l_event = create_event("debug", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_message)
        if (method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, T_s.c().GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_info(T_static_string i_static_string_info, Object... i_traces = T_s.c().GC_SKIPPED_ARG as Object[]) {
        I_event l_event = create_event("info", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_info)
        if (method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, T_s.c().GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_warning(T_static_string i_static_string_warning, I_trace... i_traces = T_s.c().GC_SKIPPED_ARG as I_trace[]) {
        I_event l_event = create_event("warning", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_warning)
        if (method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, T_s.c().GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    Integer get_depth() {
        return p_method_invocation_stack.size()
    }

    I_method_invocation get_current_method_invocation() {
        if (!p_method_invocation_stack.isEmpty()) {
            return p_method_invocation_stack.peek()
        } else {
            return PC_DEFAULT_METHOD_INVOCATION
        }
    }

    @Override
    LinkedList<I_method_invocation> get_invocation_stack() {
        return p_method_invocation_stack
    }
}
