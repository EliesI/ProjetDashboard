package com.doubledash.exception.widget;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Widget non trouvé")
public class WidgetNotFoundException extends Exception{
}
