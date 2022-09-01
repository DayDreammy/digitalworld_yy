package edu.tongji.cc.digitalworld.api;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Log stream access.
 * Also demonstrate the date type variable transmission in restful API.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
@RestController
@RequestMapping(value="/api/logstream/")
public class LogController {

    // http://localhost:8080/api/logstream/search?date=2028-09-09
    @GetMapping(value = "/search")
    public Map<String, Object> search(Date date) throws ParseException {
        //log.warn("date={}", date);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "TestName");
        map.put("age", 30);
        map.put("date", date);
        return map;
    }

    /**
     * @InitBinder标注的方法,只针对当前Controller有效!
     * MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.util.Date!
     */
    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        //前端传入的时间格式必须是"yyyy-MM-dd"效果!
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
}
