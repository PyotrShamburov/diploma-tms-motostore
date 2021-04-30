package by.tms.home.controller;

import by.tms.home.entity.exception.UserAlreadyExistException;
import by.tms.home.entity.exception.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public String UserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public String UserAlreadyExist(UserAlreadyExistException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }
}
