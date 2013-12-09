package jiangsir.zerobb.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.Tables.Upfile;

public class FileToBlob {
	/**
	 * 將 各個階段上傳的檔案全面轉成 Blob 存放。
	 * 
	 */

	/**
	 * 從 upfileid=10323 之後 檔案都存放在 /ZeroBB/upfiles 的資料夾當中。
	 * 
	 * @throws IOException
	 */
	public String UpfilesToBlob(int lastupfileid) throws IOException {
		UpfileDAO upfileDao = new UpfileDAO();
		ArrayList<Upfile> upfiles = upfileDao.getNullBlobUpfiles(lastupfileid,
				10323);
		int exist = 0;
		int nonexist = 0;
		String result = "";
		for (Upfile upfile : upfiles) {
			// String REAL_PATH = ENV.APP_REAL_PATH;
			String REAL_PATH = "/Users/jiangsir/DynamicWebProjects/ZeroBB_Dyna/WebContent/";
			File file;
			try {
				file = new File(REAL_PATH + upfile.getINNER_PATH(),
						upfile.getINNER_FILENAME());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("錯誤！！無法處理檔案！" + "upfileid=" + upfile.getId()
						+ ", artilceid=" + upfile.getArticleid()
						+ ", filename=" + upfile.getFilename());
				continue;
			}
			if (file.exists()) {
				FileInputStream stream = new FileInputStream(file);
				upfile.setBinary(stream);
				System.out.println("找到！upfileid=" + upfile.getId()
						+ ", articleid=" + upfile.getArticleid()
						+ ", filename:" + upfile.getFilename() + " file:"
						+ file.getPath());
				try {
					upfileDao.update(upfile);
					// 放入 blob 後刪除！
					if (file.delete()) {
						System.out.println("成功刪除 " + file.getPath());
					}
					result += "找到！upfileid=" + upfile.getId() + ", articleid="
							+ upfile.getArticleid() + ", file:"
							+ file.getPath() + ", size=" + file.length()
							+ "<br>";
					exist++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				nonexist++;
				System.err.println("不存在！ upfileid=" + upfile.getId()
						+ ", articleid=" + upfile.getArticleid()
						+ ", , filename:" + upfile.getFilename() + " ("
						+ file.getPath() + ")");
			}
		}
		return "所有大於 upfileid=" + lastupfileid + "當中 blob 長度為 0 的共有 "
				+ upfiles.size() + "個, 總共處理了" + exist + "個檔案。共 " + nonexist
				+ "個不存在<br>" + result;
	}

	/**
	 * 檔案最多的時期！從 2005 ~ 2010 年都有。特徵是都放在 /ZeroBB/zerobb/userhome 內。後來因為反映個資問題而改成
	 * zerobb_CLOSED
	 * 
	 * @return
	 * @throws IOException
	 */
	public String UserhomeToBlob(int lastupfileid) throws IOException {
		String result = "";
		UpfileDAO upfileDao = new UpfileDAO();
		ArrayList<Upfile> upfiles = upfileDao.getNullBlobUpfiles(lastupfileid,
				5980);
		int exist = 0;
		int nonexist = 0;
		for (Upfile upfile : upfiles) {
			// String REAL_PATH = ENV.APP_REAL_PATH;
			String REAL_PATH = "/Users/jiangsir/DynamicWebProjects/ZeroBB_Dyna/WebContent/zerobb_CLOSED/";
			File file;
			try {
				file = new File(REAL_PATH + upfile.getFilepath(),
						upfile.getFiletmpname());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("錯誤！！無法處理檔案！" + "upfileid=" + upfile.getId()
						+ ", artilceid=" + upfile.getArticleid()
						+ ", filename=" + upfile.getFilename());
				continue;
			}
			if (file.exists()) {
				FileInputStream stream = new FileInputStream(file);
				upfile.setBinary(stream);
				System.out.println("找到！upfileid=" + upfile.getId()
						+ ", articleid=" + upfile.getArticleid()
						+ ", filename:" + upfile.getFilename() + " file:"
						+ file.getPath());
				try {
					upfileDao.update(upfile);
					// 放入 blob 後刪除！
					if (file.delete()) {
						System.out.println("成功刪除 " + file.getPath());
					}
					result += "找到！upfileid=" + upfile.getId() + ", articleid="
							+ upfile.getArticleid() + ", file:"
							+ file.getPath() + ", size=" + file.length()
							+ "<br>";
					exist++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				nonexist++;
				System.err.println("不存在！ upfileid=" + upfile.getId()
						+ ", articleid=" + upfile.getArticleid()
						+ ", , filename:" + upfile.getFilename() + " ("
						+ file.getPath() + ")");
			}
		}
		return "所有大於 upfileid=" + lastupfileid + "當中 blob 長度為 0 的共有 "
				+ upfiles.size() + "個, 總共處理了" + exist + "個檔案。共 " + nonexist
				+ "個不存在<br>" + result;
	}

	/**
	 * 最早期的檔案。在 2005 之前。放置在 userhome/oldfiles 裡。有許多中文檔名的問題。
	 * 
	 * @return
	 */
	public String OldfileToBlob() {
		String result = "";
		return result;
	}
}
