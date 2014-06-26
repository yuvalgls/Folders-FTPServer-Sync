import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class TR32 {

	private JFrame frame;
	private JTextField txtClient;
	private JTextField txtFolder;
	private JTextField txtFile;
	private JTextField txtSize;
	private JTextField txtDate;
	private JTextField txtPath;
	private JTextField txtLog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TR32 window = new TR32();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TR32() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 867, 374);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLocal = new JLabel("Local");
		lblLocal.setBounds(168, 35, 46, 14);
		frame.getContentPane().add(lblLocal);
		
		JLabel lblClient = new JLabel("Client : ");
		lblClient.setBounds(10, 12, 46, 14);
		frame.getContentPane().add(lblClient);
		
		txtClient = new JTextField();
		txtClient.setBounds(66, 9, 324, 20);
		frame.getContentPane().add(txtClient);
		txtClient.setColumns(10);
		
		JButton btnSync = new JButton("Sync");
		btnSync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//txtClient.setText("Starting www.terem.co.il Calculation");
				//txtClient.update(txtClient.getGraphics());
				
				String Traget = null;
				String localFileFullName =  null;
				String ClientBUDir =  null;
				String fileName = null;
				int[] Clients = {84449};
				
				//run on all clients listed in Clients array
				System.out.println();
				for(int i=0 ; i<Clients.length ; i++){
					String Client = String.valueOf(Clients[i]);
					txtClient.setText(Client);
					txtClient.update(txtClient.getGraphics());
					ClientBUDir =  ("X:\\" + Clients[i] + "\\BakRPS\\CLOUDIN\\");
					System.out.println("Client[" + i + "] is " + Clients[i]);
					//Find the client back up folders
					String ClientBUFolders[] = FindSubFilesOrFolders.FindSubFolders(ClientBUDir);
					//for loop to run on BUFolder subfolders
					for (int a =0 ; a<ClientBUFolders.length ; a++){
						//find all files under the a bu folder
						txtFolder.setText(ClientBUDir + ClientBUFolders[a]);
						txtFolder.update(txtFolder.getGraphics());
						String BUFolderFiles[] = FindSubFilesOrFolders.FindSubFiles(ClientBUDir + "\\" +ClientBUFolders[a]);
						
						for(int b =0 ; b<BUFolderFiles.length ; b++ ){
							//upload all files under the a bu folder
							Traget = ("\\teremcoil\\terem.co.il\\FTPBU\\" + Clients[i] + "\\" + ClientBUFolders[a]);
							localFileFullName =  (ClientBUDir + ClientBUFolders[a] + "\\" + BUFolderFiles[b]);
							//System.out.println("localFileFullName : " + localFileFullName);
							txtFile.setText(BUFolderFiles[b]);
							txtFile.update(txtFile.getGraphics());
							
							
							
							File f1 = new File(ClientBUDir + ClientBUFolders[a] + "\\" + BUFolderFiles[b]);
							long bytes = f1.length();
							txtSize.setText(String.valueOf(bytes));
							txtSize.update(txtSize.getGraphics());
							Date date = new Date(f1.lastModified());
							SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
							String Filedate = df.format(date);
							txtDate.setText(Filedate);
							txtDate.update(txtDate.getGraphics());
							txtPath.setText(String.valueOf(Traget));
							txtPath.update(txtPath.getGraphics());
							FTPUploadOrDownload.UpLoad(Traget ,  localFileFullName,  BUFolderFiles[b], bytes, Filedate);
						}
						//find all subfolders under the a bu folder
						String[] BUSubFolders = FindSubFilesOrFolders.FindSubFolders(ClientBUDir + ClientBUFolders[a]);
						
						for(int c = 0 ; c < BUSubFolders.length ; c++){
							//find all files under and subfolders
							String[] BUSubFoldersFiles = FindSubFilesOrFolders.FindSubFiles(ClientBUDir + ClientBUFolders[a] + "\\" + BUSubFolders[c]);
							for(int d=0 ; d < BUSubFoldersFiles.length ; d++ ){
								//upload subfolders files
								Traget = ("\\teremcoil\\terem.co.il\\FTPBU\\" + Clients[i] + "\\" + ClientBUFolders[a] + "\\" + BUSubFolders[c]);
								//System.out.println("Traget : " + Traget);
								localFileFullName =  (ClientBUDir + "\\"  + ClientBUFolders[a] + "\\" + BUSubFolders[c] + "\\" + BUSubFoldersFiles[d]);
								//System.out.println("localFileFullName : " + localFileFullName);
								fileName = BUSubFoldersFiles[d];
								File f2 = new File(ClientBUDir + "\\"  + ClientBUFolders[a] + "\\" + BUSubFolders[c] + "\\" + BUSubFoldersFiles[d]);
								txtFolder.setText(ClientBUDir + "\\"  + ClientBUFolders[a] + "\\" + BUSubFolders[c] + "\\");
								txtFolder.update(txtFolder.getGraphics());
								txtFile.setText(BUSubFoldersFiles[d]);
								txtFile.update(txtFile.getGraphics());
								long bytes = f2.length();
								txtSize.setText(String.valueOf(bytes));
								txtSize.update(txtSize.getGraphics());
								Date date = new Date(f2.lastModified());
								SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
								String Filedate = df.format(date);
								txtDate.setText(Filedate);
								txtDate.update(txtDate.getGraphics());
								txtPath.setText(String.valueOf(Traget));
								txtPath.update(txtPath.getGraphics());
								FTPUploadOrDownload.UpLoad(Traget ,  localFileFullName,  fileName, bytes, Filedate);
							}
						}
					}
				}
			}
		});
		btnSync.setBounds(10, 300, 120, 25);
		frame.getContentPane().add(btnSync);
		
		JLabel lblFolder = new JLabel("Folder : ");
		lblFolder.setBounds(10, 57, 46, 14);
		frame.getContentPane().add(lblFolder);
		
		JLabel lblFile = new JLabel("File : ");
		lblFile.setBounds(10, 82, 46, 14);
		frame.getContentPane().add(lblFile);
		
		txtFolder = new JTextField();
		txtFolder.setBounds(66, 54, 324, 20);
		frame.getContentPane().add(txtFolder);
		txtFolder.setColumns(10);
		
		txtFile = new JTextField();
		txtFile.setBounds(66, 79, 324, 20);
		frame.getContentPane().add(txtFile);
		txtFile.setColumns(10);
		
		JLabel lblSize = new JLabel("Size : ");
		lblSize.setBounds(10, 110, 46, 14);
		frame.getContentPane().add(lblSize);
		
		txtSize = new JTextField();
		txtSize.setBounds(66, 104, 143, 20);
		frame.getContentPane().add(txtSize);
		txtSize.setColumns(10);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setBounds(219, 110, 39, 14);
		frame.getContentPane().add(lblDate);
		
		txtDate = new JTextField();
		txtDate.setBounds(248, 107, 142, 20);
		frame.getContentPane().add(txtDate);
		txtDate.setColumns(10);
		
		JLabel lblRemote = new JLabel("Remote");
		lblRemote.setBounds(168, 146, 46, 14);
		frame.getContentPane().add(lblRemote);
		
		JLabel lblPath = new JLabel("Path : ");
		lblPath.setBounds(10, 175, 46, 14);
		frame.getContentPane().add(lblPath);
		
		txtPath = new JTextField();
		txtPath.setBounds(66, 172, 324, 20);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
		JButton btnFilesystemVsFtp = new JButton("FileSystem VS FTP");
		btnFilesystemVsFtp.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				int[] Clients = {84449};
				String Result = null;
				String Traget = null;
				String localFileFullName =  null;
				String ClientBUDir =  null;
				String fileName = null;
				int errorcount = 0;
				for(int i=0 ; i<Clients.length ; i++){
					String Client = String.valueOf(Clients[i]);
					txtClient.setText(Client);
					txtClient.update(txtClient.getGraphics());
					ClientBUDir =  ("X:\\" + Clients[i] + "\\BakRPS\\CLOUDIN\\");
					System.out.println("Client[" + i + "] is " + Clients[i]);
					//Find the client back up folders
					String BUFolders[] = FindSubFilesOrFolders.FindSubFolders(ClientBUDir);
					
					String currentdate = Sub.getdate();
					//for loop to run on BUFolder subfolders
					for (int a =0 ; a<BUFolders.length ; a++){
						//find all files under the a bu folder
						String BUFolderFiles[] = FindSubFilesOrFolders.FindSubFiles(ClientBUDir + BUFolders[a]);
						txtFolder.setText(BUFolders[a]);
						txtFolder.update(txtFolder.getGraphics());
						
						for(int b =0 ; b<BUFolderFiles.length ; b++ ){
							//upload all files under the a bu folder
							Traget = ("\\teremcoil\\terem.co.il\\FTPBU\\" + Clients[i] + "\\" + BUFolders[a]);
							localFileFullName =  (ClientBUDir + BUFolders[a] + "\\" + BUFolderFiles[b]);
							
							fileName = BUFolderFiles[b];
							//System.out.println("localFileFullName : " + localFileFullName);
							txtFile.setText(BUFolderFiles[b]);
							txtFile.update(txtFile.getGraphics());
							
							
							
							File f1 = new File(ClientBUDir + BUFolders[a] + "\\" + BUFolderFiles[b]);
							long bytes = f1.length();
							txtSize.setText(String.valueOf(bytes));
							txtSize.update(txtSize.getGraphics());
							Date date = new Date(f1.lastModified());
							SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
							String Filedate = df.format(date);
							txtDate.setText(Filedate);
							txtDate.update(txtDate.getGraphics());
							txtPath.setText(String.valueOf(Traget));
							txtPath.update(txtPath.getGraphics());
							try {
								Result = Sub.CheckFilesysVSFTP(Traget ,  localFileFullName,  fileName, bytes, Filedate);
								
								if(Result != null && !Result.isEmpty()){
									
									File logfile = new File("/Selenium/workspace/UpLoadTR32/LogFiles/" + currentdate + ".txt");
									if (logfile.exists() == false) {
										logfile.createNewFile();
										System.out.println("File created : " + logfile);
									}
									errorcount++;
									Sub.WriteToLog(Result, localFileFullName + " " + bytes + " " + Filedate, errorcount, logfile);
								}else{
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						//find all subfolders under the a bu folder
						String[] BUSubFolders = FindSubFilesOrFolders.FindSubFolders(ClientBUDir + BUFolders[a]);
						
						for(int c = 0 ; c < BUSubFolders.length ; c++){
							//System.out.println(ClientBUDir + BUFolders[a] + "\\" + BUSubFolders[c]);
							String BUSubFoldersFiles[] = FindSubFilesOrFolders.FindSubFiles(ClientBUDir + BUFolders[a] + "\\" + BUSubFolders[c]);
							for(int d=0 ; d < BUSubFoldersFiles.length ; d++ ){
								//upload subfolders files
								Traget = ("\\teremcoil\\terem.co.il\\FTPBU\\" + "\\" + Clients[i] + "\\" + BUFolders[a] + "\\" + BUSubFolders[c]);
								//System.out.println("Traget : " + Traget);
								localFileFullName =  (ClientBUDir + "\\"  + BUFolders[a] + "\\" + BUSubFolders[c] + "\\" + BUSubFoldersFiles[d]);
								//System.out.println("localFileFullName : " + localFileFullName);
								fileName = BUSubFoldersFiles[d];
								File f2 = new File(ClientBUDir + "\\"  + BUFolders[a] + "\\" + BUSubFolders[c] + "\\" + BUSubFoldersFiles[d]);
								txtFolder.setText(ClientBUDir + "\\"  + BUFolders[a] + "\\" + BUSubFolders[c] + "\\");
								txtFolder.update(txtFolder.getGraphics());
								txtFile.setText(BUSubFoldersFiles[d]);
								txtFile.update(txtFile.getGraphics());
								long bytes = f2.length();
								txtSize.setText(String.valueOf(bytes));
								txtSize.update(txtSize.getGraphics());
								Date date = new Date(f2.lastModified());
								SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
								String Filedate = df.format(date);
								txtDate.setText(Filedate);
								txtDate.update(txtDate.getGraphics());
								txtPath.setText(String.valueOf(Traget));
								txtPath.update(txtPath.getGraphics());
								try {
									Result = Sub.CheckFilesysVSFTP(Traget ,  localFileFullName,  fileName, bytes, Filedate);
									
									if(Result != null && !Result.isEmpty()){
										
										File logfile = new File("/Selenium/workspace/UpLoadTR32/LogFiles/" + currentdate + ".txt");
										//FileWriter fw = null;
										//BufferedWriter bw = null;
										if (logfile.exists() == false) {
											logfile.createNewFile();
											System.out.println("File created : " + logfile);
										}
										errorcount++;
										
										Sub.WriteToLog(Result, localFileFullName + " " + bytes + " " + Filedate, errorcount, logfile);
									}else{
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
		btnFilesystemVsFtp.setBounds(140, 300, 120, 25);
		frame.getContentPane().add(btnFilesystemVsFtp);
		
		JButton btnFtpVsFilesystem = new JButton("FTP VS FileSystem");
		btnFtpVsFilesystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("lets go");
				//Sub.CheckFilesysVSFTP(Traget, localFileFullName, fileName, bytes, filedate)
			}
		});
		btnFtpVsFilesystem.setBounds(270, 300, 120, 25);
		frame.getContentPane().add(btnFtpVsFilesystem);
		
		txtLog = new JTextField();
		txtLog.setBounds(411, 9, 430, 316);
		frame.getContentPane().add(txtLog);
		txtLog.setColumns(10);
	}
}
