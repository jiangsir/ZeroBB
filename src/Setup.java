
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;

import jiangsir.zerobb.Scopes.ApplicationScope;

/**
 *  - Setup.java
 * 2009/3/13 下午 02:11:13
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class Setup {
	private String BasePath;
	// private String webxml_path;
	private String contextxml_path;
	// private String myproperties_path;
	// Properties props = null;
	public String contextxml = "";
	// public String properties_path = "";
	private Document doc = null;
	private Document doc_contextxml = null;
	private String dbhost = "";
	private String dbname = "";
	private String dbusername = "";
	private String dbpassword = "";

	public enum CONTEXT_PARAM {
		username, //
		connectionName, //
		password, //
		connectionPassword, //
		url, //
		connectionURL;//
	}

	public Setup(String BasePath) {
		this.BasePath = BasePath;
		this.contextxml_path = this.BasePath + "/META-INF/context.xml";
		this.contextxml = this.readContextxml();
	}

	private String readContextxml() {
		BufferedReader breader = null;
		StringBuffer text = new StringBuffer(50000);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(this.contextxml_path);
			breader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		try {
			while ((line = breader.readLine()) != null) {
				text.append(line + " ");
			}
			breader.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString();
	}

	void writeContextxml() {
		XMLOutputter outp = new XMLOutputter();
		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(this.contextxml_path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			outp.output(this.doc_contextxml, fo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 設定 context.xml
	 * 
	 * @param key
	 * @param value
	 */
	public void setContextParam_OLD(String key, String value) {
		if ("".equals(value.trim())) {
			return;
		}
		if (this.contextxml.matches(".*" + key + "=\".*\".*")) {
			this.contextxml = this.contextxml.replaceAll(key + "=\".[^\"]*\"", key + "=\"" + value + "\"");
		}
	}

	public void setContextDBSetting(String dbhost, String dbname, String dbusername, String dbpassword) {
		SAXBuilder builder = new SAXBuilder();
		try {
			if (this.doc_contextxml == null) {
				this.doc_contextxml = builder.build(new File(this.contextxml_path));
			}
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Get the root element
		Element root = this.doc_contextxml.getRootElement();
		// Print servlet information
		// List<?> children = root.getChildren();
		// Iterator<?> i = children.iterator();
		// while (i.hasNext()) {
		// Element child = (Element) i.next();
		Element resource = root.getChild("Resource");
		System.out.println(resource);
		resource.setAttribute(CONTEXT_PARAM.url.name(), "jdbc:mysql://" + this.getDbhost() + ":3306/" + this.getDbname()
				+ "?useUnicode=true&characterEncoding=UTF-8");
		resource.setAttribute(CONTEXT_PARAM.username.name(), dbusername);
		resource.setAttribute(CONTEXT_PARAM.password.name(), dbpassword);
		Element manager = root.getChild("Manager");
		if (manager != null) {
			Element store = manager.getChild("Store");
			store.setAttribute(CONTEXT_PARAM.connectionName.name(), dbusername);
			store.setAttribute(CONTEXT_PARAM.connectionPassword.name(), dbpassword);
			store.setAttribute(CONTEXT_PARAM.connectionURL.name(), "jdbc:mysql://" + this.getDbhost() + ":3306/"
					+ this.getDbname() + "?useUnicode=true&characterEncoding=UTF-8");
		}
	}

	/**
	 * 取得 context.xml
	 * 
	 * @param key
	 * @return
	 */
	public String getContextParam_OLD(String key) {
		String tmp = this.contextxml;
		String value = "";
		System.out.println("this.contextxml=" + this.contextxml);
		System.out.println("key=" + key);
		if (tmp.matches(".*" + key + "=\".*\".*")) {
			tmp = tmp.substring(tmp.indexOf(key + "=\""), tmp.length());
			System.out.println("tmp=" + tmp);
			value = tmp.substring(key.length() + 1, tmp.indexOf("\""));
		}
		return value;
	}

	public String getContextParam(CONTEXT_PARAM key) {
		SAXBuilder builder = new SAXBuilder();
		try {
			if (this.doc_contextxml == null) {
				this.doc_contextxml = builder.build(new File(this.contextxml_path));
			}
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Get the root element
		Element root = this.doc_contextxml.getRootElement();
		// Print servlet information
		List<?> children = root.getChildren();
		Iterator<?> i = children.iterator();

		while (i.hasNext()) {
			Element child = (Element) i.next();
			if ("Resource".equals(child.getName())) {
				return child.getAttributeValue(key.name());
			}
		}
		return null;
	}

	public String getDbhost() {
		return dbhost;
	}

	public void setDbhost(String dbhost) {
		if ("".equals(dbhost)) {
			return;
		}
		this.dbhost = dbhost;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		if ("".equals(dbname)) {
			return;
		}
		this.dbname = dbname;
	}

	public String getDefault_DBHost() {
		String url = getContextParam(CONTEXT_PARAM.url);
		// jdbc: mysql: //
		// 127.0.0.1:3306/zerojudge?useUnicode=true&characterEncoding=UTF-8
		return url.substring(url.indexOf("//") + 2, url.indexOf(":3306"));
	}

	//
	// public void setDBHost(String host) {
	// if ("".equals(host)) {
	// return;
	// }
	// String url = "jdbc:mysql://" + host
	// + ":3306/zerojudge?useUnicode=true&amp;characterEncoding=UTF-8";
	// // String url = this.getContextParam(CONTEXT_PARAM.url);
	// url = url.replaceFirst(this.getDBHost(), host);
	// this.setContextParam(CONTEXT_PARAM.url, url);
	// this.setContextParam(CONTEXT_PARAM.connectionURL, url);
	// }

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		if ("".equals(dbusername)) {
			return;
		}
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		if ("".equals(dbpassword)) {
			return;
		}
		this.dbpassword = dbpassword;
	}

	public String getDefault_DBName() {
		String url = getContextParam(CONTEXT_PARAM.url);
		// jdbc: mysql: //
		// 127.0.0.1:3306/zerojudge_dev?useUnicode=true&characterEncoding=UTF-8
		return url.substring(url.indexOf("3306/") + 5, url.indexOf("?"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("/****************************************************************/");
		System.out.println("// 設定程式                     ");

		System.out.println("// 說明：");
		System.out.println("// 每個選項後的括號代表預設值，直接按 ENTER 將直接套用預設值");
		System.out.println("");
		System.out.println("/***************************************************************/");
		System.out.print("您的 webapps 路徑是否為 \"" + args[0] + "\"? (Y/N)");
		if ("Y".equals(scanner.nextLine())) {
			ApplicationScope.setAppRoot(new File(args[0]));
		} else {
			System.out.print("請自行輸入 webapps 絕對路徑：");
			File appRoot = new File(scanner.nextLine());
			ApplicationScope.setAppRoot(appRoot);
		}
		Setup setup = new Setup(ApplicationScope.getAppRoot().getPath());
		setup.setDbhost(setup.getDefault_DBHost());
		System.out.print("請指定資料庫主機位置： (" + setup.getDbhost() + ")：");
		setup.setDbhost(scanner.nextLine());

		setup.setDbname(setup.getDefault_DBName());
		System.out.print("請指定資料庫名稱： (" + setup.getDbname() + ")：");
		setup.setDbname(scanner.nextLine());

		setup.setDbusername(setup.getContextParam(CONTEXT_PARAM.username));
		System.out.print("請指定資料庫使用者帳號： (" + setup.getDbusername() + ")：");
		setup.setDbusername(scanner.nextLine());

		setup.setDbpassword(setup.getContextParam(CONTEXT_PARAM.password));
		System.out.print("請指定資料庫使用者密碼：(" + setup.getDbpassword() + ")：");
		setup.setDbpassword(scanner.nextLine());

		setup.setContextDBSetting(setup.getDbhost(), setup.getDbname(), setup.getDbusername(), setup.getDbpassword());

		System.out.println("\n注意!! 若系統正在執行當中，修改這些設定 apps 將會自動重啟。");
		System.out.print("確定(yes/no)?");
		if ("yes".equals(scanner.nextLine())) {
			setup.writeContextxml();
			// setup.writetoWebxml();
			// ConfigFactory.writeAppConfig(appConfig);
			// System.out.println("設定已寫入!!");
		} else {
			System.out.println("設定未生效!!");
		}

	}
}
