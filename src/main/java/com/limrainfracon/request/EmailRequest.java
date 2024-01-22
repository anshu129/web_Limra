package com.limrainfracon.request;

import lombok.Data;

@Data
public class EmailRequest {
	
	private String name;
	private String email;
	//private String contactNumber;
	private String emailSubject;
	private String emailBody;

}
