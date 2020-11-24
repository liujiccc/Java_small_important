package cn.itcast.util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/24 10:50
 */



public class DownLoadUtils {

    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
