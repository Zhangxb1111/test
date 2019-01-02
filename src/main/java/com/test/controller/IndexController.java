package com.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.entity.MonUnit;
import com.test.service.MonUnitServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private MonUnitServiceI monUnitServiceI;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/index")
    public String index2(HttpServletRequest request, ModelMap modelMap){
        modelMap.addAttribute("info",request.getParameter("info"));
        return "index";
    }

    /**
     * WebSocket Controller
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello")
    @SendTo("/queue/hello")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, client! I get your message";
    }
    @RequestMapping(value = "/printSql")
    @ResponseBody
    public String testMysqlData(){
        List<MonUnit> list = monUnitServiceI.getList();
        return JSONObject.toJSONString(list);
    }

}
