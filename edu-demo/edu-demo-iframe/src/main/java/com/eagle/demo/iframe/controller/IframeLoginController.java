package com.eagle.demo.iframe.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @author lijie
 * @create 2020-06-10
 */
@Controller
public class IframeLoginController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${edu.sso-url}")
    String eduSsoUrl;

    @GetMapping("/")
    public String login(Model model){
        // 所申请的应用id，此为示例应用，该应用回调地址为http://127.0.0.1:81/home，请确保该部署环境为127.0.0.1:81
        String module = "/hneduSsoLogin.jsp?clientId=test";
        model.addAttribute("eduSsoUrl", eduSsoUrl + module);
        return "login";
    }

    @GetMapping("home")
    public String home(Model model, String tokenKey){
        if(tokenKey == null || tokenKey.length() == 0){
            return "redirect:/";
        }
        model.addAttribute("eduSsoUrl", eduSsoUrl);
        model.addAttribute("tokenKey", tokenKey);
        return "home";
    }

    @GetMapping("user")
    @ResponseBody
    public Object user(String accessToken) {
        if(accessToken == null || accessToken.length() == 0){
            return "";
        }
        String url = eduSsoUrl+"/data/user?accessToken="+accessToken;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return JSONObject.parseObject(response.getBody());
    }
}
