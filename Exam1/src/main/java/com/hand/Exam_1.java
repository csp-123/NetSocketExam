package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Hello world!
 *
 */
public class Exam_1 {

	public static void main(String[] args) {
		new ReadByGet().start();
	}
}

class ReadByGet extends Thread {
	public void run() {
		InputStream is = null;
		BufferedInputStream bis = null;

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			URL url = new URL("http://files.saas.hand-china.com/java/target.pdf");

			URLConnection connection = url.openConnection();

			is = connection.getInputStream();
			bis = new BufferedInputStream(is);

			byte[] b = new byte[1024];

			File f = new File("target.pdf");

			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);

			int l = -1;

			while ((l = bis.read(b)) != -1) {
				bos.write(b, 0, l);
				bos.flush();
			}
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}

				if (is != null) {
					is.close();
				}
				if (bos != null) {
					bos.close();
				}

				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
