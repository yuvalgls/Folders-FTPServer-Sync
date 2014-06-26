import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class FTPUploadOrDownload {
	public static void UpLoad(String Traget , String localFileFullName, String fileName, long kilobytes, String date) {
		// get an ftpClient object
		String ftpclient = "ftpclient";
		String ftppassword = "ftppassword";
		String ftphost = "ftphost";
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftphost);
			boolean login = ftpClient.login(ftpclient, ftppassword);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			System.out.println("FTP Path = " + Traget);
			System.out.println("Local Fullpath = " + localFileFullName );
			System.out.println("Filename = " + fileName);
			System.out.println("Local Size = " + kilobytes);
			System.out.println("Local date = " + date);
			System.out.println(" ");
					
			if (login) {
				InputStream input = new FileInputStream(new File(localFileFullName));
				String client = localFileFullName.substring(3, 8);
				ftpClient.makeDirectory("/teremcoil/terem.co.il/FTPBU/" + client);
				ftpClient.makeDirectory(Traget);
				ftpClient.storeFile(Traget + "\\" + fileName, input);
					}
			} catch (SocketException e) {
				e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					} finally {
					}
		try {
			ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				}
	}

}
