import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;



public class Sub {
	public static void WriteToLog(String FTPError , String LocalError , int ErrorCount , File LogFile){
		try {
	        if(!LogFile.exists()){
	        	LogFile.createNewFile();
	        }
	        BufferedWriter out = new BufferedWriter(new FileWriter(LogFile,true));
            out.write(String.valueOf(ErrorCount));
            out.write(" ");
            out.write(FTPError);
            out.write(" ");
            out.write(LocalError);
            out.write(" ");
            out.newLine();
            out.close();
	        } catch (IOException e) {}
	}
	//THIS IS THE GAME
	public static void Displaylogfile(File file){
	}
	
	
	public static String getdate(){
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		String currentdate = df.format(date);
		return currentdate;
	}
	
	public static String CheckFilesysVSFTP(String Traget, String localFileFullName, String fileName, long bytes, String filedate) throws IOException{
		// TODO Auto-generated method stub
		//long result = 0 ;
		String ftpclient = "ftpclient";
		String ftppassword = "ftppassword";
		String ftphost = "ftphost";
		FTPClient ftpClient = new FTPClient();
		FTPFile[] files;
		try {
			ftpClient.connect(ftphost);
			ftpClient.login(ftpclient, ftppassword);
			//System.out.println(Traget);
			ftpClient.changeWorkingDirectory(Traget);
			ftpClient.changeWorkingDirectory(Traget);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//boolean tst = ftpClient.changeWorkingDirectory(Traget);
		
		files = ftpClient.listFiles();
		for (FTPFile FTPfile : files){
        	if (FTPfile.isFile()) {
        		long reply = FTPfile.getSize();
        		String FTPfileString = String.valueOf(Traget + "\\" +  FTPfile.getName());
        		String FTPDate = ftpClient.getModificationTime(FTPfileString);
        		if(FTPDate!=null){
        			FTPDate = FTPDate.substring(4);        			
        		}
        		
        		
            	if(FTPfile.getName().toLowerCase().equals(fileName.toLowerCase())){
            		if(reply != bytes){// || FTPDate!=filedate){
            			return (Traget + "\\" + FTPfile.getName() + " " + reply + " " + FTPDate);
            		}       		
            	}
        	}
        }
		return null;
	}	
}