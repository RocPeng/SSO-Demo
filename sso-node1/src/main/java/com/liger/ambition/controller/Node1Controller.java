package com.liger.ambition.controller;

import com.alibaba.fastjson.JSON;
import com.liger.ambition.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by roc_peng on 2017/8/17.
 * Email 18817353729@163.com
 * Url https://github.com/RocPeng/
 * Description 这个世界每天都有太多遗憾,所以你好,再见!
 */
@Controller
public class Node1Controller {

    @RequestMapping
    public String home(@RequestParam("callback") String callback, @RequestParam("sid") String sid, RedirectAttributes attr) throws Exception {
        System.out.println(callback);
        System.out.println(sid);
        //参数直接加载url后面
        URL url=new URL(callback+"?sid="+sid);
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        String jsonString = "";
        if(conn.getResponseCode()==200){                //200表示请求成功
            InputStream is=conn.getInputStream();       //以输入流的形式返回
            //将输入流转换成字符串
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            byte [] buffer=new byte[1024];
            int len=0;
            while((len=is.read(buffer))!=-1){
                baos.write(buffer, 0, len);
            }
            jsonString=baos.toString();
            baos.close();
            is.close();
            System.out.println(jsonString);
            //转换成json数据处理
        }
        User user = JSON.parseObject(jsonString, User.class);
        attr.addFlashAttribute("msg","欢迎用户"+user.getUsername());
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
