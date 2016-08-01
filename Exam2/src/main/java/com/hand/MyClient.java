package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {

	public static String HOST = "";
	public static int PORT = 10000;

	public static void main(String[] args) {

		InputStream soc_is = null;
		BufferedInputStream soc_bis = null;

		FileOutputStream fos = null;
		BufferedOutputStream fbos = null;

		Socket socket = null;

		try {
			socket = new Socket(HOST, PORT);

			soc_is = socket.getInputStream();
			soc_bis = new BufferedInputStream(soc_is);

			byte[] b = new byte[1024];
			int l = -1;

			fos = new FileOutputStream(new File("client/target.pdf"));
			fbos = new BufferedOutputStream(fos);

			while ((l = soc_bis.read(b)) != -1) {
				fbos.write(b, 0, l);
				fbos.flush();
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fbos != null) {
					fbos.close();
				}
				if (fos != null) {
					fos.close();
				}

				if (soc_bis != null) {
					soc_bis.close();
				}
				if (soc_is != null) {
					soc_is.close();
				}

				if (socket != null) {
					socket.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
