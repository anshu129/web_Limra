package com.limrainfracon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_docs")
@Data
public class Userdocs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "file_name", nullable = false)
	private String fileName;
	@Column(name = "file_path", nullable = false)
    private String filePath;
	@Column(name = "file_size")
    private Long fileSize;
	@Column(name = "login_id", nullable = false)
    private String loginId;
	@Column(name = "id_number")
    private String id_number;
	@Column(name = "type")
    private String type;
}
