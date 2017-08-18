package com.liger.ambition.controller;

import com.alibaba.fastjson.JSON;
import com.liger.ambition.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by roc_peng on 2017/8/17.
 * Email 18817353729@163.com
 * Url https://github.com/RocPeng/
 * Description 这个世界每天都有太多遗憾,所以你好,再见!
 */
@Controller
public class Node2Controller {

    @RequestMapping
    public String home(@RequestParam("callback") String callback, @RequestParam("sid") String sid, RedirectAttributes attr) throws Exception {
        DefaultHttpClient client = new DefaultHttpClient();
        String jsonString = "";
        //发送get请求
        HttpGet request = new HttpGet(callback+"?sid="+sid);
        HttpResponse response = client.execute(request);
        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            /**读取服务器返回过来的json字符串数据**/
            jsonString = EntityUtils.toString(response.getEntity());
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
