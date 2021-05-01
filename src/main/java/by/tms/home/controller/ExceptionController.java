package by.tms.home.controller;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.exception.UserAlreadyExistException;
import by.tms.home.entity.exception.UserNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

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

    @ExceptionHandler(EntityAlreadyExistException.class)
    public String EntityAlreadyExist(EntityAlreadyExistException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }
}
