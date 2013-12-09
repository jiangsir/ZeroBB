/**
 * jiangsir.zerobb.Web - WebObject.java
 * 2012/4/9 下午1:28:16
 * nknush-001
 */
package jiangsir.zerobb.Web;

/**
 * idv.jiangsir.Objects - WebObject.java
 * 2011/6/13 下午4:13:11
 * nknush-001
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author nknush-001
 * 
 */
public class WebObject {
	private URL url;
	private String[] querystrings;
	private String method;
	private String charset;

	public WebObject(URL url, String[] querystrings, String method,
			String charset) {
		this.setUrl(url);
		this.setMethod(method);
		this.setQuerystrings(querystrings);
		this.setCharset(charset);
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String[] getQuerystrings() {
		return querystrings;
	}

	public void setQuerystrings(String[] querystrings) {
		for (int i = 0; querystrings != null && i < querystrings.length; i++) {
			System.out.println("querystring[i]=" + querystrings[i]);
		}
		this.querystrings = querystrings;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		if (!"POST".equals(method)) {
			method = "GET";
		}
		this.method = method;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		if (!"UTF8".equals(charset) && !"big5".equals(charset)) {
			charset = "UTF8";
		}
		this.charset = charset;
	}

	public String getHtml() {
		System.out.println("getHtml: method=" + method);
		try {
			if ("POST".equals(method)) {
				return getPostdata();
			} else {
				return getGetdata();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String getGetdata() throws IOException {
		System.out.println("GET: protocol=" + url.getProtocol() + ", query="
				+ url.getQuery() + ", host=" + url.getHost() + ", path="
				+ url.getPath() + ", ref=" + url.getRef());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true); // 这一句不要
		// conn.getOutputStream().write(querystring.getBytes());
		// conn.getOutputStream().write(url.getQuery().getBytes());
		// conn.getOutputStream().write("".getBytes());
		conn.getOutputStream().flush();
		conn.getOutputStream().close();

		// httpurlconnection.setRequestMethod("GET"); 这一句不要，缺省就是get
		// httpurlconnection.getOutputStream().write(getFileString().getBytes());
		// conn.setRequestProperty("Cookie", "");
		// conn.setRequestProperty("Connection", "Keep-Alive");
		// int code = conn.getResponseCode();
		// System.out.println(httpurlconnection.getContentEncoding());
		System.out.println(conn.getContent());
		// //System.out.println(httpurlconnection.getOutputStream());
		// System.out.println("code " + code);

		// java.io.DataOutputStream dos = new java.io.DataOutputStream(conn
		// .getOutputStream());
		// dos.writeBytes(url.getQuery());

		java.io.BufferedReader rd = new java.io.BufferedReader(
				new java.io.InputStreamReader(conn.getInputStream(), charset));
		StringBuffer webdata = new StringBuffer(10000);
		webdata.append("METHOD=" + conn.getRequestMethod() + "\n");
		webdata.append("回應訊息" + conn.getResponseMessage() + "\n");
		webdata.append("回應代號" + conn.getResponseCode() + "\n");
		webdata.append("表頭第一行" + conn.getHeaderField(0) + "\n");
		webdata.append("內容長度" + conn.getContentLength() + "\n");
		webdata.append("內容種類" + conn.getContentType() + "\n");
		String line;
		while ((line = rd.readLine()) != null) {
			webdata.append(line + "\n");
			System.out.println(line);
		}
		rd.close();
		return webdata.toString();
	}

	public String getPostdata() throws IOException {
		System.out.println("POST: " + url.getProtocol() + "," + url.getQuery()
				+ "," + url.getHost() + url.getPath() + "," + url.getRef());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);// 发送POST请求必须设置允许输出
		conn.setUseCaches(false);// 不使用Cache
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("Content-Length", String.valueOf(qs.length));
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		DataOutputStream outStream = new DataOutputStream(conn
				.getOutputStream());
		String qs = "";
		for (int i = 0; querystrings != null && i < querystrings.length; i++) {
			qs += "&" + querystrings[i];
		}
		outStream.write(qs.getBytes());
		outStream.flush();

		java.io.BufferedReader br = new java.io.BufferedReader(
				new java.io.InputStreamReader(conn.getInputStream(), charset));

		StringBuffer webdata = new StringBuffer(10000);
		webdata.append("METHOD=" + conn.getRequestMethod() + "\n");
		webdata.append("回應訊息" + conn.getResponseMessage() + "\n");
		webdata.append("回應代號" + conn.getResponseCode() + "\n");
		webdata.append("表頭第一行" + conn.getHeaderField(0) + "\n");
		webdata.append("內容長度" + conn.getContentLength() + "\n");
		webdata.append("內容種類" + conn.getContentType() + "\n");
		String line;
		while ((line = br.readLine()) != null) {
			webdata.append(line + "\n");
			System.out.println(line);
		}
		br.close();
		return webdata.toString();
	}
}
