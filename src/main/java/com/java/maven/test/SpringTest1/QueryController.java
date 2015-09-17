package com.java.maven.test.SpringTest1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.*;
import org.springframework.web.multipart.MultipartFile;

import com.java.maven.test.SpringTest1.*;
import com.java.maven.test.wrap.Wrap;

@MultipartConfig
@Controller
public class QueryController {
	//@Autowired
	//FileValidator fileValidator;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String queryForm(Model model) {
		model.addAttribute("query", new Query());
		// initModelList(model);

		return "query";
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public String querySubmit(@ModelAttribute Query query, Model model,
			HttpServletRequest request, BindingResult file_err_result) {
		model.addAttribute("query", query);
		// query.getUpload_file_sequence();
		// uploadFileHandler(query, model);
		// query.setResult(query.buildResult());

		// get client ip address
		System.out.println(request.toString());
		String remoteAddress_ip = request.getRemoteAddr();
		System.out.println(remoteAddress_ip);
		
		//connectToAccumulo
		Connector conn = null;
		try {
			conn = ConnectAccumulo.connectToAccumulo();
		} catch (AccumuloSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AccumuloException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// for input accession id
		if (query.getAccession_id() != "" && query.getAccession_id() != null) {
			System.out.println("here");
			query.getSeqFromAccumulon(conn);
		}
		// for input upload_file_hmmProfle
//		else if (query.getUpload_file_hmmProfle() != null) {
//			
//			System.out.println("have upload_file_hmmProfle");
//			query.saveUploadHmmFile(file_err_result, remoteAddress_ip);
//		}
//		else if(query.getUpload_file() != null){
//			System.out.println("have upload file");
//			query.read_fileUpload();
//			File newfile = query.getUpload_file().getAbsoluteFile();
//			//query.safeUploadFileHandler(newfile,file_err_result,remoteAddress_ip);
//		}
		else if(query.getTaxonomy()!= "" && query.getTaxonomy()!= null ){
			String taxonomy = makeupTaxonomyInput(query.getTaxonomy());
			try {
				query.setResult(Wrap.taxToRaw(conn, taxonomy,query.getHmmProf()));
			} catch (AccumuloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccumuloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TableNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		query.fileSaveOnServer(remoteAddress_ip);

		return "result";
	}

	// private void initModelList(Model model) {
	// List<String> numberList = new ArrayList<String>();
	// numberList.add("chocolate");
	// numberList.add("banana");
	// numberList.add("strawberry");
	// numberList.add("mango");
	// numberList.add("cherry");
	// model.addAttribute("numberList", numberList);
	//
	//
	//
	// }

	// @RequestMapping(value = "/query", method = RequestMethod.POST)
	// public @ResponseBody
	// String uploadFileHandler(@RequestParam("name") String name,
	// @RequestParam("file") MultipartFile file) {
	//
	public void uploadFileHandler(Query query, Model model) {
		MultipartFile file = (MultipartFile) query.getUpload_file();
		String name = file.getName();
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				// logger.info("Server File Location="
				// + serverFile.getAbsolutePath());

				// return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				// return "You failed to upload " + name + " => " +
				// e.getMessage();
			}
		} else {
			// return "You failed to upload " + name
			// + " because the file was empty.";
		}
	}
	
	
	@SuppressWarnings("resource")
	public String makeupTaxonomyInput(String input){
		java.util.Scanner wScan = new Scanner(input);
        String tInput = "taxonomy|";

        boolean first = true;
        while(wScan.hasNext())
        {
            if(first)
            {
            	tInput += wScan.next();
                first = false;
            }
            else
            {
            	tInput += "; " + wScan.next();
            }
        }
        System.out.println(tInput);
        return tInput;
	}
}