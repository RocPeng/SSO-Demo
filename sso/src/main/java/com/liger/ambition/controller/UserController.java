package com.liger.ambition.controller;

import com.alibaba.fastjson.JSON;
import com.liger.ambition.global.UserConfig;
import com.liger.ambition.model.User;
import com.liger.ambition.util.Encrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by roc_peng on 2017/8/17.
 * Email 18817353729@163.com
 * Url https://github.com/RocPeng/
 * Description 这个世界每天都有太多遗憾,所以你好,再见!
 */
@Controller
public class UserController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login/user")
    public String loginUser( String username,String password,HttpSession session) throws Exception{
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(token);
        //登陆成功返回,将用户信息持久化存储
        User user = (User) session.getAttribute(UserConfig.LOGIN_USER);
        File file = new File(UserConfig.FILE_LOCATION);
        FileOutputStream out = new FileOutputStream(file);
        out.write(JSON.toJSONString(user).getBytes());
        out.close();
        return "redirect:/main";
    }

    @RequestMapping("/main")
    public String mainPage(Model model,HttpSession session){
        User user = (User) session.getAttribute(UserConfig.LOGIN_USER);
        String sid = Encrypt.md5(user.getUsername(),user.getPassword());
        model.addAttribute("sid",sid);
        return "main";
    }

    @RequestMapping("/sso")
    @ResponseBody
    public User sso(@RequestParam("sid") String sid,HttpSession session) throws Exception{
        FileInputStream in = new FileInputStream(UserConfig.FILE_LOCATION);
        byte[] arr = new byte[4096];
        in.read(arr);
        in.close();
        String str = new String(arr);
        str = str.trim();
        System.out.println(str);
        User user = JSON.parseObject(str, User.class);
        if(user ==null){
            return null;
        }
        String mySid = Encrypt.md5(user.getUsername(), user.getPassword());
        if(!sid.equals(mySid)){
            return null;
        }
        return user;
    }

}
