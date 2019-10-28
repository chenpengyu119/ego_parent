package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengyu
 * @date 2019/10/9 16:17.
 */
@Controller
public class PassportController {

    @Autowired
    private PassportService passportService;

    /**
     * 显示登录页面
     * @param model
     * @param ref
     * @return
     */
    @RequestMapping("/user/showLogin")
    @CrossOrigin
    public String showLogin(Model model,
                            @RequestHeader(value = "Referer",required = false,defaultValue = "")String ref){
        // 设置登录成功后跳转的地址，一般都是从哪来回哪去，只有注册页面不能回去，如果是从注册页面过来，可以设置该url为“”，前台就可以处理
        if (ref.endsWith("/user/showRegister")){
            ref = "";
        }
        model.addAttribute("redirect", ref);
        return "login";
    }

    /**
     * 登录验证
     * @param tbUser
     * @param model
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public EgoResult login(TbUser tbUser,Model model){
        return passportService.login(tbUser);
    }


    /**
     * 登录后显示用户信息
     * @param token
     * @return
     */
    @GetMapping("/user/token/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult showUser(@PathVariable String token){
        return passportService.showUser(token);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @PostMapping("/user/logout/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult logout(@PathVariable String token){
        return passportService.logout(token);
    }

    @RequestMapping("/user/showRegister")
    public String showRegister(){
        return "register";
    }

    /**
     * 验证用户名是否已存在
     * @return
     */
    @GetMapping("/user/check/{param}/{type}")
    @ResponseBody
    public EgoResult check(@PathVariable String param,@PathVariable int type){
        return passportService.check(param, type);
    }

    /**
     * 用户注册
     * @param tbUser
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public EgoResult register(TbUser tbUser){
        return passportService.register(tbUser);
    }


}
