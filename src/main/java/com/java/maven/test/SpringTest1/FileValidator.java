package com.java.maven.test.SpringTest1;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.java.maven.test.SpringTest1.Query;


public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println("this file validator has error");
		return false;
	}

	@Override
	public void validate(Object uploadedFile, Errors errors) {  
		  
		Query file = (Query) uploadedFile;  
		  
		  if (( file.getUpload_file_hmmProfle()).getSize() == 0) {  
		   errors.rejectValue("file", "query.salectFile",  
		     "Please select a file!");  
		  }  
		  
	}  
}
