package com.doubledash.service;

import com.doubledash.dto.WidgetDTO;
import com.doubledash.exception.widget.WidgetNotFoundException;
import com.doubledash.model.User;
import com.doubledash.model.Widget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface widgetService {

    Widget createWidget(WidgetDTO Widget);

    List<Widget> getAllWidget();

    WidgetDTO getWidgetById(Long WidgetId) throws WidgetNotFoundException;

    Widget updateWidgetById(WidgetDTO Widget, Long widgetId) throws WidgetNotFoundException;

    List<Widget> getWidgetByUserId(Long UserId);

    void deleteWidgetById(Long WidgetId) throws WidgetNotFoundException;

}
