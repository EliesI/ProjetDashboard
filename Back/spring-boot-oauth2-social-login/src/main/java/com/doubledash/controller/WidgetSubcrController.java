package com.doubledash.controller;

import com.doubledash.dto.WidgetDTO;
import com.doubledash.exception.user.UserNotFoundException;
import com.doubledash.exception.widget.WidgetNotFoundException;
import com.doubledash.model.Widget;
import com.doubledash.service.widgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/widget")
public class WidgetSubcrController {

    private final widgetService WidgetService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public WidgetDTO createWidget(@Valid @RequestBody WidgetDTO WidgetDTO) throws Exception{
        WidgetService.createWidget(WidgetDTO);
        return WidgetDTO;
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{widgetId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteWidgetById(@PathVariable(value = "widgetId") Long WidgetId) throws WidgetNotFoundException {
        WidgetService.deleteWidgetById(WidgetId);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{widgetId}")
    public WidgetDTO updateWidgetById(@RequestBody WidgetDTO WidgetDTO, @PathVariable Long widgetId) throws WidgetNotFoundException {
        WidgetService.updateWidgetById(WidgetDTO, widgetId);
        return WidgetDTO;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public List<Widget> getWidgetByUserId(@PathVariable Long userId) throws WidgetNotFoundException {
        return WidgetService.getWidgetByUserId(userId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getWidget/{widgetId}")
    public WidgetDTO getUserById(@PathVariable(value = "widgetId") Long widgetId) throws WidgetNotFoundException {
        return WidgetService.getWidgetById(widgetId);
    }
}

