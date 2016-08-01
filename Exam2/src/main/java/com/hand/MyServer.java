package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class MyServer {

	public static int PORT = 10000;

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);

			while (true) {
				Socket socket = serverSocket.accept();
				invoke(socket);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private static void invoke(final Socket socket) {

		new Thread(new Runnable() {

			public void run() {
				FileInputStream fis = null;
				BufferedInputStream fbis = null;
				OutputStream soc_os = null;
				BufferedOutputStream soc_bos = null;

				try {
					fis = new FileInputStream(new File("server/target.pdf"));
					fbis = new BufferedInputStream(fis);

					byte[] b = new byte[1024];

					soc_os = socket.getOutputStream();
					soc_bos = new BufferedOutputStream(soc_os);
					int l = -1;
					while ((l = fbis.read(b)) != -1) {
						soc_bos.write(b, 0, l);
						soc_bos.flush();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fbis != null) {
							fbis.close();
						}

						if (fis != null) {
							fis.close();
						}

						if (soc_bos != null) {
							soc_bos.close();
						}
						if (soc_os != null) {
							soc_os.close();
						}

						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
