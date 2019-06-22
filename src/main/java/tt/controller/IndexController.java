package tt.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tt.util.WeiXinUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author JerryTan
 */
@Controller
public class IndexController {
    @Value("${appID}")
    private String appID;
    @Value("${appsecret}")
    private String appsecret;
    @RequestMapping("/index")
    public String Index(HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String backUrl = "http://www.jk520lml.top/callback";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" +appID+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        model.addAttribute("url",url);
        return  "login";
//        return "redirect:"+url;
    }
    @RequestMapping("/callback")
    public String  callback(String code,Model model) throws IOException {
        //获取access_token
        System.out.println("code : "+code);
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" +appID+
                "&secret=" +appsecret+
                "&code=" +code+
                "&grant_type=authorization_code";
        JSONObject jsonObject = WeiXinUtils.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token =jsonObject.getString("access_token");
        //刷新access_token 拉取用户信息(需scope为 snsapi_userinfo)
        String infoUrl ="https://api.weixin.qq.com/sns/userinfo?access_token=" + token
                + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject info = WeiXinUtils.doGetJson(infoUrl);
        model.addAttribute("info",info);
        System.out.println(info);
        return "weixin";
    }
}
