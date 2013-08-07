/**
 * 
 */
package us.codecraft.webmagic.samples.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zhangdong zhangdong147896325@163.com
 * 
 *         2013-7-15 下午2:48:55
 */
public class SinaPicDownLoadManager {

    public static final String path = "/data/temp/pic/";

    public static void main(String[] args) {
        String url = "http://src.house.sina.com.cn/imp/imp/deal/b8/4f/5/85124a5e3ba57cdde065ee6b376_p7_mk7_os373af0_cm440X330.jpg";
        System.out.println(DigestUtils.md5Hex(url) + ".jpg");
    }

    public static void downLoad(String url) {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            String sName = DigestUtils.md5Hex(url) + ".jpg";
            //存放地址
            File img = new File(path + sName);
            //生成图片
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(img));
            byte[] buf = new byte[2048];
            int length = in.read(buf);
            while (length != -1) {
                out.write(buf, 0, length);
                length = in.read(buf);
            }
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downLoadOriginPic(String url) {
        downLoad(getOriginalUrl(url));
    }

    public static void downLoadPic(String url) {

    }

    public static String getOriginalUrl(String url) {
        if (url == null) {
            return null;
        }
        return url.replaceAll("(_cm\\d+X\\d+)", "");
    }

}
