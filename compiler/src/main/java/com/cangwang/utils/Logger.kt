package com.cangwang.utils

import org.apache.commons.lang3.StringUtils

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

/**
 *
 * Created by cangwang on 2017/8/30.
 */
class Logger(private val msg: Messager) {

    /**
     * Print info log.
     */
    fun info(info: CharSequence) {
        if (StringUtils.isNotEmpty(info)) {
            msg.printMessage(Diagnostic.Kind.NOTE, PREFIX_OF_LOGGER + info)
        }
    }

    fun error(error: CharSequence) {
        if (StringUtils.isNotEmpty(error)) {
            msg.printMessage(Diagnostic.Kind.ERROR, PREFIX_OF_LOGGER + "An exception is encountered, [" + error + "]")
        }
    }

    fun error(error: Throwable?) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.ERROR, PREFIX_OF_LOGGER + "An exception is encountered, [" + error.message + "]" + "\n" + formatStackTrace(error.stackTrace))
        }
    }

    fun warning(warning: CharSequence) {
        if (StringUtils.isNotEmpty(warning)) {
            msg.printMessage(Diagnostic.Kind.WARNING, PREFIX_OF_LOGGER + warning)
        }
    }

    private fun formatStackTrace(stackTrace: Array<StackTraceElement>): String {
        val sb = StringBuilder()
        for (element in stackTrace) {
            sb.append("    at ").append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }

    companion object {
        val PROJECT = "ModuleBus"
        internal val PREFIX_OF_LOGGER = PROJECT + "::Compiler "
    }
}
