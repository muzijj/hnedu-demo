package com.eagle.demo.authcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.eagle.demo.authcode.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @author lijie
 * @create 2020-06-10
 */
@Controller
public class AuthCodeLoginController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${edu.sso-url}")
    String eduSsoUrl;

    @GetMapping("home")
    public String home(Model model){
        model.addAttribute("eduSsoUrl", eduSsoUrl);
        return "home";
    }

    @PostMapping("login")
    @ResponseBody
    public Object login(String username, String password) {
        String url = eduSsoUrl+"/oauth/user/token?username="+username+"&password="+StringUtils.base642String(password);
        // 对应用id与应用凭证进行base64加签，此为示例应用
        String sign = StringUtils.string2Base64("test:123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic "+ sign);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return JSONObject.parseObject(response.getBody());
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
