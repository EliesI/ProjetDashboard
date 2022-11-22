package com.doubledash.service;

import com.doubledash.dto.SignUpRequest;
import com.doubledash.dto.UserByIdDTO;
import com.doubledash.dto.WidgetDTO;
import com.doubledash.exception.user.UserNotFoundException;
import com.doubledash.exception.widget.WidgetNotFoundException;
import com.doubledash.model.Role;
import com.doubledash.model.Widget;
import com.doubledash.repository.WidgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WidgetServiceImpl implements widgetService {

    private final WidgetRepository WidgetRepository;

    @Override
    public Widget createWidget(WidgetDTO Widget) {

        Widget widget = buildWidget(Widget);
        widget = WidgetRepository.save(widget);
        WidgetRepository.flush();
        return widget;
    }

    private Widget buildWidget(final WidgetDTO formDTO) {
        Widget widget = new Widget();
        widget.setUser_id(Math.toIntExact(formDTO.getUser_id()));
        widget.setWidget_id(formDTO.getWidget_id());
        widget.setRefresh_rate(formDTO.getRefresh_rate());
        widget.setParam_1(formDTO.getParam_1());
        widget.setParam_2(formDTO.getParam_2());
        return widget;
    }

    @Override
    public List<Widget> getAllWidget() {
        return WidgetRepository.findAll();
    }

    @Override
    public WidgetDTO getWidgetById(Long WidgetId) throws WidgetNotFoundException{
        if (!WidgetRepository.existsById(WidgetId)) {
            throw new WidgetNotFoundException();
        }

        WidgetDTO widgetDTO = new WidgetDTO();

        WidgetRepository.findById(WidgetId).map(widget ->{
            widgetDTO.setParam_1(widget.getParam_1());
            widgetDTO.setParam_2(widget.getParam_2());
            widgetDTO.setRefresh_rate(widget.getRefresh_rate());
            widgetDTO.setId(widget.getId());
                    return widgetDTO;
                }
        );
        return widgetDTO;
    }

    @Override
    public Widget updateWidgetById(WidgetDTO Widget, Long widgetId) throws WidgetNotFoundException {
        if (!WidgetRepository.existsById(widgetId)) {
            throw new WidgetNotFoundException();
        }
        WidgetRepository.findById(widgetId).map(widget ->{
            widget.setRefresh_rate(Widget.getRefresh_rate());
            widget.setParam_1(Widget.getParam_1());
            widget.setParam_2(Widget.getParam_2());
            return WidgetRepository.save(widget);
                }
        );
        return null;
    }

    @Override
    public List<Widget> getWidgetByUserId(Long userId) {
        List<Widget> widgetList = new ArrayList<>();
        for (Widget o : WidgetRepository.findAll()) {
            if (o.getUser_id() == userId) {
                widgetList.add(o);
            }
        }
        return widgetList;
    }

    @Override
    public void deleteWidgetById(Long WidgetId) throws WidgetNotFoundException {
        if (!WidgetRepository.existsById(WidgetId)) {
            throw new WidgetNotFoundException();
        }

        WidgetRepository.deleteById(WidgetId);
    }
}
