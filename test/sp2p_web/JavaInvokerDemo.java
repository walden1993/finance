package sp2p_web;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import com.shove.web.util.TdMerSign;

public class JavaInvokerDemo {
	public static final String invokeHttpRepeater(String url, byte[] data,
			String contentType, String sign, String ticket, String access_key,
			String method) {
		String result = "";
		if (url == null || "".equals(url)) {
			System.out.println("Please check the post url.");
			return result;
		}
		if (method == null) {
			method = "POST";
		}
		try {
			URL _url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) _url
					.openConnection();
			connection.setRequestMethod(method);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("sign", sign);
			connection.setRequestProperty("ticket", ticket);
			connection.setRequestProperty("access_key", access_key);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			if (StringUtils.isNotBlank(contentType)) {
				connection.setRequestProperty("Content-Type", contentType);
			}
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			connection.setAllowUserInteraction(true);
			connection.connect();

			if (data != null) {
				OutputStream out = connection.getOutputStream();
				if (out != null) {
					out.write(data);
					out.flush();
					out.close();
				}
			}

			InputStream in = connection.getInputStream();
			if (in != null) {
				ByteArrayOutputStream streams = new ByteArrayOutputStream();
				int r;
				byte[] buf = new byte[1024];
				while ((r = in.read(buf, 0, buf.length)) != -1) {
					streams.write(buf, 0, r);
				}
				streams.flush();
				streams.close();
				result = new String(streams.toByteArray(), "UTF-8");
			}
			in.close();
			System.out.println("Network callback: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://172.16.3.157:8888/api/v1.0/hr.fund.support";
		String data = "{\"currentPage\":1,\"perPageCount\":10}";
		String ticket = "";
		String md5key="909*&EDFDD";
		String deskey="#87EWSE_";
		
		String access_key = "082e568f-fe11-49f0-925c-259e0b210015";
		String sign = TdMerSign.MD5Sign(access_key+"&"+ticket+md5key);
		try {
			invokeHttpRepeater(url, data.getBytes("UTF-8"), "application/json",
					sign, ticket, access_key, "POST");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
