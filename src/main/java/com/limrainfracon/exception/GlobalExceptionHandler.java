package com.limrainfracon.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.limrainfracon.model.User;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	
    @ExceptionHandler(RefrencedUserDoesNotExist.class)
    public ModelAndView handleRefrencedUserDoesNotExist(RefrencedUserDoesNotExist e) {
    	ModelAndView mav = new ModelAndView("signup");
        mav.addObject("message", e.getMessage());
        mav.addObject("user", new User());
        return mav;
    }

    @ExceptionHandler(DuplicateLoginIdException.class)
    public ModelAndView handleDuplicateLoginIdException(DuplicateLoginIdException e) {
    	ModelAndView mav = new ModelAndView("signup");
        mav.addObject("message", e.getMessage());
        mav.addObject("user", new User());
        return mav;
    }
    
    
}