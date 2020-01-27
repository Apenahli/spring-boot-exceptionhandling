package com.springboot.file.springbootfile.service;

import java.io.File;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UploadPathServiceImpl implements UploadPathService {

	@Autowired
	private ServletContext context;

	@Override
	public File getFilePath(String modifirdFileName, String path) {
		
		boolean exists = new File(context.getRealPath("/" + path + "/")).exists();

		if (!exists) {

			new File(context.getRealPath("/" + path + "/")).mkdir();
		}

		String modifiedFilePath = context.getRealPath("/" + path + "/" + File.separator + modifirdFileName);

		File file = new File(modifiedFilePath);

		return file;
	}
}
