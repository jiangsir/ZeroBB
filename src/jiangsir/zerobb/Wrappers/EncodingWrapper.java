package jiangsir.zerobb.Wrappers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jiangsir.zerobb.Filter.EncodingFilter;
import jiangsir.zerobb.Filter.EncodingFilter.ENCODING;

public class EncodingWrapper extends HttpServletRequestWrapper {
	EncodingFilter.ENCODING encoding;

	public EncodingWrapper(HttpServletRequest request,
			EncodingFilter.ENCODING encoding) {
		super(request);
		this.encoding = encoding;
	}

	/**
	 * 判断字符串的编码
	 * 
	 * @param str
	 * @return
	 */
	public ENCODING getEncoding(String str) {
		if (str == null) {
			return null;
		}
		for (ENCODING encoding : ENCODING.values()) {
			try {
				if (str.equals(new String(str.getBytes(encoding.getValue()),
						encoding.getValue()))) {
					return encoding;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
		// String encode = "ISO-8859-1";
		// try {
		// if (str.equals(new String(str.getBytes(encode), encode))) {
		// String s1 = encode;
		// return s1;
		// }
		// } catch (Exception exception1) {
		// }
		// encode = "UTF-8";
		// try {
		// if (str.equals(new String(str.getBytes(encode), encode))) {
		// String s2 = encode;
		// return s2;
		// }
		// } catch (Exception exception2) {
		// }
		// return "";
	}

	@Override
	public String getParameter(String name) {
		String value = this.getRequest().getParameter(name);
		System.out.println("name=" + name + ", value("
				+ this.getEncoding(value) + ")=" + value);
		if (value != null && this.getEncoding(value) != null
				&& this.getEncoding(value) == ENCODING.ISO_8859_1) {
			byte[] b;
			try {
				b = value.getBytes(ENCODING.ISO_8859_1.getValue());
				value = new String(b, ENCODING.UTF_8.getValue());
				System.out.println("value(" + ENCODING.UTF_8 + ")=" + value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return value;
	}
}
