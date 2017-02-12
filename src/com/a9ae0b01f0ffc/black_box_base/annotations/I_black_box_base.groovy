package com.a9ae0b01f0ffc.black_box_base.annotations

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass("com.a9ae0b01f0ffc.black_box.annotations.T_black_box_transformation")
@interface I_black_box_base {
    String value() default ""
}