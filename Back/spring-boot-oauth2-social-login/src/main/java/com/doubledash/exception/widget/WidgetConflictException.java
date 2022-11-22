package com.doubledash.exception.widget;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Conflict widget")
public class WidgetConflictException extends Exception{
}
