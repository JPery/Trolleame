package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

public final class ImageHelper {

	public static final long DEFAULT_SIZE = 50 * 1024;
	
	public static boolean uploadImage(Part filePart, String path, String filename) throws IOException {
		boolean success = true;
		OutputStream out = null;
		InputStream filecontent = null;
		try {
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			file = new File(path+filename);
			if(!file.exists()) {
				file.createNewFile();
			} 
			out = new FileOutputStream(file);
			filecontent = filePart.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
		}
		return success;
	}
}
