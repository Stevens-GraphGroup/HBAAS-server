package com.java.maven.test.SpringTest1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.servlet.annotation.MultipartConfig;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.java.maven.test.wrap.Wrap;
import com.java.maven.test.SpringTest1.FileValidator;

@MultipartConfig
public class Query {

	//@Autowired
	//FileValidator fileValidator;

	// private long id;
	// private String content;
	// private String user_input_query;
	private String result;
	private String sequence;
	private File upload_file;
	private MultipartFile upload_file_hmmProfle;
	private String accession_id;
	private String database;
	private String database_chose;
	//private Hashtable<String, String> databaseHashtable = new Hashtable<String, String>();
	private String upload_file_sequence;
	private String taxonomy;
	private String file_path;
	private String uploadFilePath;
	private String upload_file_temp_path;
	private String hmmProf;


	public Query() {
//		databaseHashtable.put("1", "NR");
//		databaseHashtable.put("2", "RefSeq");
//		databaseHashtable.put("3", "UniProtKB");
//		databaseHashtable.put("4", "Pfamseq");
//		databaseHashtable.put("5", "SwissProt");
//		databaseHashtable.put("6", "PDB");
//		databaseHashtable.put("7", "UniMes");
//		databaseHashtable.put("8", "env NR");
//		databaseHashtable.put("9", "rp75");
//		databaseHashtable.put("10", "rp55");
//		databaseHashtable.put("11", "rp35");
//		databaseHashtable.put("12", "rp15");
//		databaseHashtable.put("13", "reference Protemoes");

	}

	// public String buildResult(){
	//
	// return Wrap.wrapWork(user_input_query);
	// }
	//

	public void getUpload_file_sequence() {
//		this.upload_file_sequence = this.upload_file.getName();
//		try {
//			Scanner in = new Scanner(this.upload_file);
//			while (in.hasNextLine()) {
//				System.out.println(in.nextLine());
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println(this.upload_file.getName());
//		if (this.upload_file.isFile()) {
//			// this.upload_file_sequence = "this file can be read";
//			System.out.println("this file can be read");
//			// try {
//			// byte[] bytes = file.getBytes();
//			// BufferedOutputStream stream =
//			// new BufferedOutputStream(new FileOutputStream(new File(name)));
//			// stream.write(bytes);
//			// stream.close();
//			// return "You successfully uploaded " + name + "!";
//			// } catch (Exception e) {
//			// return "You failed to upload " + name + " => " + e.getMessage();
//			// }
//		} else {
//			// this.upload_file_sequence= "this file can NOT be read";
//			System.out.println("You failed to upload " + this.upload_file
//					+ " because the file was empty.");
//		}
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getUpload_file_temp_path() {
		return upload_file_temp_path;
	}

	public void setUpload_file_temp_path(String upload_file_temp_path) {
		this.upload_file_temp_path = upload_file_temp_path;
	}
	
	public File getUpload_file() {
		if(upload_file!= null) 
			return upload_file.getAbsoluteFile();
		else
			return upload_file;
	}

	public void setUpload_file(File upload_file) {
		this.upload_file = upload_file;
	}

	public void setUpload_file_hmmProfle(MultipartFile upload_file_hmmProfle) {
		this.upload_file_hmmProfle = upload_file_hmmProfle;
	}

	public MultipartFile getUpload_file_hmmProfle() {
		return upload_file_hmmProfle;
	}

	public String getAccession_id() {
		return accession_id;
	}

	public void setAccession_id(String accession_id) {
		this.accession_id = accession_id;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public void getSeqFromAccumulon(Connector conn) {
		// AAA00002.1
		List<String> accession_list = new ArrayList<String>();
		String[] accession_array;
		accession_array = this.accession_id.split(",");
		System.out.println(accession_id);
		for (int i = 0; i < accession_array.length; i++) {
			accession_list.add(accession_array[i]);
		}

		try {
			Map<String, String> mapAccessionSeq;
			mapAccessionSeq = ConnectAccumulo
					.getSequencesFromAccumulo(accession_list, conn);
			for (Entry<String, String> entry : mapAccessionSeq.entrySet()) {
				System.out.println("key: " + entry.getKey() + " value : "
						+ entry.getValue());
				this.result += ("key: " + entry.getKey() + " value : "
						+ entry.getValue() + "\n");
			}
		} catch (AccumuloSecurityException | AccumuloException
				| TableNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//save out put as file and then give user the link of the file to let them download it
	public void fileSaveOnServer(String ip) {
		String fileName = "";
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
				.format(new Date());
		String newip = ip.replace(':', '_');
		fileName += newip;
		fileName += timeStamp;
		fileName += ".txt";

		File newFile = new File("output/" + fileName);
		if (!newFile.exists()) {
			try {
				newFile.createNewFile();

				BufferedWriter output = new BufferedWriter(new FileWriter(
						newFile));
				//output.write("hmm file: ");
				output.write(this.result + "\r\nDONE! Thanks for using HBAAS");
				output.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String path = newFile.getPath();
		System.out.println(fileName);
		System.out.println(path);
		this.file_path = fileName;
	}

	public void saveUploadHmmFile(BindingResult file_err_result, String ip) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		MultipartFile file = this.upload_file_hmmProfle;
		// file validation
		//fileValidator.validate(file, file_err_result);
		// makeup new file name, add ip and time stamp
		String originalFileName = file.getOriginalFilename();
		String fileName = "";
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
				.format(new Date());
		String newip = ip.replace(':', '_');
		fileName += newip;
		fileName += timeStamp;
		fileName += originalFileName;

		if (file_err_result.hasErrors()) {// file has error
			System.err.println("input file has error: "
					+ file_err_result.getFieldError().toString());
		}

		try {
			inputStream = file.getInputStream();
			File newFile = new File("input_files/" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void safeUploadFileHandler(File file,BindingResult file_err_result, String ip){
		String originalFileName = file.getName();
		String fileName = "";
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
				.format(new Date());
		String newip = ip.replace(':', '_');
		fileName += newip;
		fileName += timeStamp;
		fileName += originalFileName;

		
		String content = null;
		   try {
		       FileReader reader = new FileReader(file);
		       char[] chars = new char[(int) file.length()];
		       reader.read(chars);
		       content = new String(chars);
		       reader.close();
		   } catch (IOException e) {
		       e.printStackTrace();
		   }
		  
		   
		   
		File newFile = new File("input_files/" + fileName);
		if (!newFile.exists()) {
			try {
				newFile.createNewFile();

				BufferedWriter output = new BufferedWriter(new FileWriter(
						newFile));
				output.write(content + "\r\nDONE! Thanks for using HBAAS");
				output.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String path = newFile.getAbsolutePath();
		System.out.println(fileName);
		System.out.println(path);
		//this.file_path = path;
	}
	
	
	public void read_fileUpload(){
		InputStream in = null;
		try {
			URL url = new URL(this.upload_file_temp_path);
			URLConnection urlConnection = url.openConnection();
			in = new URL( this.upload_file_temp_path ).openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 try {
		   System.out.println( IOUtils.toString( in ) );
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		   IOUtils.closeQuietly(in);
		 }
	}

	public String getHmmProf() {
		return hmmProf;
	}

	public void setHmmProf(String hmmProf) {
		this.hmmProf = hmmProf;
	}
}