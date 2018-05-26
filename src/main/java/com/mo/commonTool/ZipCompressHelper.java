package com.mo.commonTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;  
import java.util.zip.ZipOutputStream;

public class ZipCompressHelper {
	public static ZipOutputStream buildZipString(OutputStream out){
		ZipOutputStream outputStream = new ZipOutputStream(out); 
		return outputStream;
	}
}
