package com.cpqd.wf.rest.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

public class ExceptionUtil {

	private static final String MESSAGE_OBJETO_NULO = "Erro nao identificado";

	private ExceptionUtil() {}
	
	public static String getStackTraceException(Throwable e, boolean detail) {
		if (e == null) {
			return MESSAGE_OBJETO_NULO;
		}
		String message = e.getMessage();

		Throwable cause = getCauseException(e);
		if (cause == null) {
			cause = e;
		}
		if (cause instanceof SQLException) {
			return handlerSQLMessage(cause.getMessage());
		} else {
			if (detail) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				return sw.toString();
			} else {
				return cause.toString().replace(String.format("%s;", cause.getClass().getName()), "").trim();
			}
		}
	}
	
	private static String handlerSQLMessage(String message) {
		if (message.startsWith("Cannot delete or update a parent row: a foreign key constraint fails")) {
			return "Registro nao pode ser excluido por possuir vinculos";
		} else if (message.startsWith("Unique index or primary key violation") || message.contains("unique constraint")) {
			return "Registro ja existente";
		}
		return message;
	}

	private static Throwable getCauseException(Throwable e) {
		while (e != null && !(e instanceof SQLException)) {
			if (e.getCause() != e) {
				e = e.getCause();
			} else {
				return e;
			}
		}
		return e;
	}

}
