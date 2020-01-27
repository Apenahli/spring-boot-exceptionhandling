package com.springboot.file.springbootfile.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.file.springbootfile.model.User;
import com.springboot.file.springbootfile.model.UserFiles;
import com.springboot.file.springbootfile.repository.UserFileRepository;
import com.springboot.file.springbootfile.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UploadPathService uploadPathService;

	@Autowired
	private UserFileRepository userFileRepository;

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User save(User user) {

		user.setCreatedDate(new Date());

		User dbUser = userRepository.save(user);
	
		if (dbUser != null && user.getFiles() != null && user.getFiles().size() > 0) {

			for (MultipartFile file : user.getFiles()) {

				String fileName = file.getOriginalFilename();
			
				System.out.println("****************");
				System.out.println(fileName);
				System.out.println("****************");

				String modifirdFileName = FilenameUtils.getBaseName(fileName) + "" + System.currentTimeMillis() + "."
						+ FilenameUtils.getExtension(fileName);

				File storeFile = uploadPathService.getFilePath(modifirdFileName, "images");

				if (storeFile != null) {
					try {
						FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				UserFiles files = new UserFiles();
				files.setFileExtension(FilenameUtils.getExtension(fileName));
				files.setFileName(fileName);
				files.setModifiedFileName(modifirdFileName);
				files.setUser(dbUser);
				userFileRepository.save(files);

			}
		}

		return dbUser;
	}

	@Override
	public User findById(Long userId) {

		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		}

		return null;
	}

	@Override
	public List<UserFiles> findFilesByUserId(Long userId) {

		return userFileRepository.findFilesByUserId(userId);
	}

}
