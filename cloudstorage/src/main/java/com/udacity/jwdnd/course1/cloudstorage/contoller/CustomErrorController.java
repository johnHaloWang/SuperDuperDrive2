package com.udacity.jwdnd.course1.cloudstorage.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";
    public final static String TAG_ = "CustomErrorController";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // display generic error
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String pageTitle = "Error";
        String errorPage = "error";
        String errorMsg = "";
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                pageTitle = "Page Not Found";
                errorMsg = "404 Error --- Page Can't be Found";
                errorPage = "error";
                log.error(TAG_+ "-> Error 404");
            }else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                pageTitle = "Internal Server Error";
                errorMsg = "500 Error --- Internal Server Error";
                errorPage = "error";
                log.error(TAG_+ "-> Error 500");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                pageTitle = "Forbidden";
                errorMsg = "406 Error --- Page is Forbidden";
                errorPage = "error";
                log.error(TAG_ + "-> Error 406");
            }else{
                pageTitle = "Error";
                errorMsg = String.valueOf(statusCode) + " Error";
                errorPage = "error";
                log.error(TAG_ + "-> Error other");
            }
        }
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("errorMsg", errorMsg);

        return errorPage;
    }

    @Override
    public String getErrorPath() {
        //LOGGER.debug("call... getErrorPath function");
        log.debug(TAG_ + "-> call... getErrorPath function");
        return "/error";
    }
}
