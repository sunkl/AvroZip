package com.mo.avroTest;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.mo.avroTest.avro.schema.AvroHelpper;
import com.mo.commonTool.ZipCompressHelper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
        	String path = "D://file.db";
        	OutputStream out = new FileOutputStream(path); 
			AvroHelpper.parseJson(out);
			System.out.println("完成avro序列化");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
