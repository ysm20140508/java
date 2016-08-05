package com.jxnu.spring.validation;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shoumiao_yao
 * @date 2016-08-05
 */
@Controller
@RequestMapping("/binder")
public class DataBinderTest {

    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor=new CustomDateEditor(dateFormat,true);
        binder.registerCustomEditor(Date.class,dateEditor);
        binder.setValidator(null);
    }
}
