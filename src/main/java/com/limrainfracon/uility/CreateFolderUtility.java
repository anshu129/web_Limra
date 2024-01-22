package com.limrainfracon.uility;

import java.io.File;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CreateFolderUtility {
	
	
	public static boolean checkAndCreateDirectory(File file)
	{
		boolean flag = true;
		if (!file.exists())
		{
			flag = file.mkdirs();
			if(flag){
				log.info("Create Directory: " + file.getAbsolutePath());
			}else{
				log.error("Not have sufficient privileges to create Directory: " + file.getAbsolutePath());
			}
		}
		return flag;
	}

}
