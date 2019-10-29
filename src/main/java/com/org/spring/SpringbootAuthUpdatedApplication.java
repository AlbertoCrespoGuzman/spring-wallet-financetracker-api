package com.org.spring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.org.spring.model.StreamGobbler;

@EnableScheduling
@SpringBootApplication
public class SpringbootAuthUpdatedApplication {

    @Autowired
    private Environment env;
	
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	public static void main(String[] args) { 
		SpringApplication.run(SpringbootAuthUpdatedApplication.class, args);
	}
	
	
	
	
	@Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
	public void scheduleFixedRateTask() {
	    System.out.println(
	      "STARTING - Mysql DB backup every 24h -> " + new Date());
	    try {
	    	mysqlBackup();
	    	System.out.println("DONE - Mysql DB backup every 24h-> " + new Date());
	    	removingFilesMoreOneWeek();
	    	System.out.println("DONE - Mysql DB Removing files every 24h-> " + new Date());
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    
	    
	}
	
	private void mysqlBackup() throws Exception {
		
		boolean isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		
		String homeDirectory = System.getProperty("user.home");
		
		SimpleDateFormat format = new SimpleDateFormat();
		format = new SimpleDateFormat("dd_MM_yyyy");
		ProcessBuilder builder = new ProcessBuilder();
		
		builder.command("sh","-c",
				String.format(env.getProperty("cronjob.backup"), format.format(new Date())));
		
		builder.directory(new File(System.getProperty("user.home") + "/mysql_backup/"));
		Process process = builder.start();
				
		StreamGobbler streamGobbler = 
		  new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		
		if(exitCode == 0){
		    InputStream inputStream = process.getInputStream();
		    byte[] buffer = new byte[inputStream.available()];
		    inputStream.read(buffer);

		    String str = new String(buffer);
		    System.out.println(str);
		}
		else{
		    InputStream errorStream = process.getErrorStream();
		    byte[] buffer = new byte[errorStream.available()];
		    errorStream.read(buffer);

		    String str = new String(buffer);
		    System.out.println(str);
		}
	}
private void removingFilesMoreOneWeek() throws Exception {
	
		ProcessBuilder builder = new ProcessBuilder();
		
		builder.command("sh","-c",
				String.format(env.getProperty("cronjob.removing.files")));
		
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
				 
		StreamGobbler streamGobbler = 
		  new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		 
		if(exitCode == 0){
		    InputStream inputStream = process.getInputStream();
		    byte[] buffer = new byte[inputStream.available()];
		    inputStream.read(buffer);

		    String str = new String(buffer);
		    System.out.println(str);
		}
		else{
		    InputStream errorStream = process.getErrorStream();
		    byte[] buffer = new byte[errorStream.available()];
		    errorStream.read(buffer);

		    String str = new String(buffer);
		    System.out.println(str);
		}
	}
	
}
