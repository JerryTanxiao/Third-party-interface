package tt.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
/**
 * @author JerryTan
 */
public class WeiXinUtils {
    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null ;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        if(entity != null){
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject=JSONObject.parseObject(result);
//            jsonObject = JSONObject.fromObject(result);
        }
        get.releaseConnection();
        return  jsonObject;
    }
}
