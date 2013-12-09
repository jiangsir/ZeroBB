/**
 * idv.jiangsir.Beans - CourseBean.java
 * 2009/12/20 下午5:07:14
 * nknush-001
 */
package jiangsir.zerobb.Beans;

import jiangsir.zerobb._Tools.Uploader;

/**
 * @author nknush-001
 * 
 */
public class UpfileBean {

	public UpfileBean() {
	}

	// ===================================================================================

	public int getMAX_FILESIZE() {
		return Uploader.MAX_FILESIZE;
	}
}
