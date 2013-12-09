package jiangsir.zerobb._Tools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Uploader {
	public static final int MAX_FILESIZE = 5 * 1000 * 1000; // bytes
	private HashMap<String, String> formfields = new HashMap<String, String>();
	private HashMap<String, ArrayList<FileItem>> filefields = new HashMap<String, ArrayList<FileItem>>();
	private List<?> items = null;

	public Uploader(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory(MAX_FILESIZE,
				new File("/tmp"));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(Uploader.MAX_FILESIZE); // bytes
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		Iterator<?> it = items.iterator();
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			if (item.isFormField()) {
				this.formfields.put(item.getFieldName(), item
						.getString("UTF-8"));
			} else {
				ArrayList<FileItem> fileitems = new ArrayList<FileItem>();
				if (this.filefields.containsKey(item.getFieldName())) {
					fileitems = this.filefields.get(item.getFieldName());
				}
				fileitems.add(item);
				this.filefields.put(item.getFieldName(), fileitems);
			}
		}
	}

	public String getFormField(String fieldname) {
		return this.formfields.get(fieldname);
	}

	public ArrayList<FileItem> getFileItems(String fieldname) {
		ArrayList<FileItem> items = new ArrayList<FileItem>();
		ArrayList<FileItem> fieldnames = this.filefields.get(fieldname);
		if (fieldnames == null) {
			return items;
		}
		Iterator<FileItem> it = fieldnames.iterator();
		while (it.hasNext()) {
			items.add((FileItem) it.next());
		}
		return items;
	}

	public ArrayList<String> getFilenames(String fieldname) {
		ArrayList<String> filenames = new ArrayList<String>();
		Iterator<?> it = this.filefields.get(fieldname).iterator();
		while (it.hasNext()) {
			filenames.add(((FileItem) it.next()).getName());
		}
		return filenames;
	}

	public ArrayList<String> getFileContentTypes(String fieldname) {
		ArrayList<String> ContentTypes = new ArrayList<String>();
		Iterator<?> it = this.filefields.get(fieldname).iterator();
		while (it.hasNext()) {
			ContentTypes.add(((FileItem) it.next()).getContentType());
		}
		return ContentTypes;
	}

	public ArrayList<Long> getFileSizes(String fieldname) {
		ArrayList<Long> filesizes = new ArrayList<Long>();
		Iterator<?> it = this.filefields.get(fieldname).iterator();
		while (it.hasNext()) {
			filesizes.add(((FileItem) it.next()).getSize());
		}
		return filesizes;
	}

	/**
	 * 檔名加上 prefix 才能保證檔名不會相同造成覆蓋, 一般可以傳入 upfilesid
	 * 
	 * @param fieldname
	 * @param prefix
	 */
	public void uploadFile(FileItem item, String path, String filename) {
		try {
			System.out.println("!FormField: item.getFieldName()="
					+ item.getFieldName() + " -- 欄位名稱");
			System.out.println("!FormField: item.getName()=" + item.getName()
					+ " -- 使用者上傳檔案的檔名");
			System.out.println("!FormField: item.getContentType()="
					+ item.getContentType() + " -- ");
			System.out.println("!FormField: item.isInMemory()="
					+ item.isInMemory() + " -- ");
			System.out.println("!FormField: item.getSize()=" + item.getSize()
					+ " -- ");
			// File uploadedFile = new File("/tmp/", "test.doc");
			File uploadedFile = new File(path, filename);
			System.out.println("寫入檔案 " + uploadedFile.getName());
			System.out.println("uploadedFile=" + uploadedFile.getPath());
			item.write(uploadedFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
