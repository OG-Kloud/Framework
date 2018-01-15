package net.kloud.framework;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class KWriter {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	private File dir;
	private boolean debug = true;
	
	public KWriter(File file, String sub) throws IOException {
		File newDir = new File(file, sub);
		if(!newDir.exists()) {
			newDir.mkdirs();
		}
		//Redundancy Call
		if(!newDir.isDirectory()) {
			newDir.mkdirs();
		}
		this.dir = newDir;
		if(debug) {
			System.out.println(newDir.toPath().toString());
			System.out.println("Created KWriter Instance");
		}
	}
	
	public KWriter(File file) throws IOException {
		File newDir = file;
		if(!newDir.exists()) {
			newDir.mkdirs();
		}
		//Redundancy Call
		if(!newDir.isDirectory()) {
			newDir.mkdirs();
		}
		this.dir = newDir;
		if(debug) {
			System.out.println(newDir.toPath().toString());
			System.out.println("Created KWriter Instance");
		}
	}
	
	public void setSubDir(String sub) {
		File newDir = new File(dir, sub);
		if(!newDir.exists()) {
			newDir.mkdirs();
		}
		if(!newDir.isDirectory()) {
			newDir.mkdirs();
		}
		if(debug)System.out.println("Created new sub-directory: " + sub + " in " + dir);
		this.dir = newDir;
	}
	
	public void writeFile(List<String> strings) throws IOException {
		if(strings == null) return;
		String title = "/" + strings.get(0) + ".klouddata";
		Path dirPath = Paths.get(dir.getPath());
		File newFile = new File(dirPath + title);
		if(newFile.exists() == false) newFile.createNewFile();
		List<String> aList = new ArrayList<String>();
		for(String line : strings) aList.add(line);
		write(aList, newFile);
		if(debug)System.out.println("Call to KWriter.writeFile()");
	}

	private void write(List<String> aList, File newFile) throws IOException {
		Path path = Paths.get(newFile.getPath());
		Files.write(path, aList, ENCODING);
		if(debug)System.out.println("Call to KWriter.write()");
	}
	
	private static void staticWrite(List<String> aList, File newFile) throws IOException {
		Path path = Paths.get(newFile.getPath());
		Files.write(path, aList, ENCODING);
		System.out.println("Call to KWriter.write()");
	}
	
	public void writeFiles(List<List<String>> objTower) throws IOException {
		for(List<String> aList : objTower) writeFile(aList);
		if(debug)System.out.println("Call to KWriter.writeFiles()");
	}
	
	private List<String> read(File file) throws IOException {
		List<String> aList = new ArrayList<String>();
		aList = Files.readAllLines(file.toPath());
		if(debug)System.out.println("Call to KWriter.read()");
		return aList;
	}
	
	public List<List<String>> readDir() throws IOException {
		List<List<String>> objTower = new ArrayList<List<String>>();
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			if(files.length == 1) return null;
			for(File file : files) objTower.add(read(file));
		}
		if(debug)System.out.println("Call to KWriter.readDir()");
		return objTower;
	}
	
	public List<String> getFileNames() {
		List<String> aList = new ArrayList<String>();
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			if(files == null) return null;
			for(File file : files) {
				aList.add(file.getName());
			}
		}
		if(debug)System.out.println("Call to KWriter.getFileNames()");
		return aList;
	}
	
	public List<String> readOneFile(String fileName) throws IOException {
		File tempFile = new File(this.dir, fileName);
		List<String> aList = Files.readAllLines(tempFile.toPath());
		return aList;
	}
	
	public void writeOneFile(String fileName, List<String> aList) throws IOException {
		File tempFile = new File(this.dir, fileName);
		if(!tempFile.exists()) tempFile.createNewFile();
		write(aList, tempFile);
	}
	
	public static void writeOneFile(File tempFile, List<String> aList) throws IOException {
		if(!tempFile.exists()) tempFile.createNewFile();
		staticWrite(aList, tempFile);
	}
	
	public KWriter setDebugEnvironment(boolean bugged) {
		this.debug = bugged;
		return this;
	}
	

}
