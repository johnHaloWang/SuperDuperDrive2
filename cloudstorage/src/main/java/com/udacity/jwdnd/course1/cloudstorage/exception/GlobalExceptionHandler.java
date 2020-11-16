package com.udacity.jwdnd.course1.cloudstorage.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public final static String TAG_ = "GlobalExceptionHandler ";

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError1(HttpServletRequest req, MultipartException e, RedirectAttributes redirectAttributes, Model model) throws Exception {
        log.debug(TAG_ + "-> inside the GlobalExceptionHandler ");

        String errorMsgStr = "Error: Maximum upload size exceeded!";//
        model.addAttribute("pageTitle", errorMsgStr);
        model.addAttribute("errorMsg", e);
        return "error";
    }
}
