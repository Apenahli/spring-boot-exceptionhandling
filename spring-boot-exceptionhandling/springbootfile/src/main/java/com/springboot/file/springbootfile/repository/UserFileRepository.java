package com.springboot.file.springbootfile.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.springboot.file.springbootfile.model.UserFiles;

@Repository
public interface UserFileRepository extends CrudRepository<UserFiles, Long>{

	@Query("select f from UserFiles as f where f.user.id= ?1")
	List<UserFiles> findFilesByUserId(Long userId);
	
	

}
