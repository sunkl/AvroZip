package com.mo.avroTest;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.mo.avro.bean.Schema;
import com.mo.avro.bean.Schema.Field;
import com.mo.avro.bean.Schema.IndexInfo;
import com.mo.avroTest.avro.schema.AvroHelpper;
import com.mo.commonTool.ZipCompressHelper;

/**
 * Hello world!
 *
 */
public class App 
{
	public static Schema buildTestSchema(){
		Schema schema = new Schema();
		schema.setSchemaId(1);
		schema.setSchemaName("中文");
		schema.setSwitchInfo("switchInfo");
		schema.setDdlDesc("ddlDesc");
		schema.setDesc("desc");
		Field field = new Field();
		field.setDesc("desc");
		field.setId(2L);
		field.setName("name");
		field.setType("type");
		List<Field> fields = Arrays.asList(field);
		schema.setFieldList(fields);
		IndexInfo indexInfo= new IndexInfo();
		indexInfo.setIndexColumns(new String[]{"a","b"});
		indexInfo.setIndexDesc("indexDesc");
		indexInfo.setIndexId(4L);
		indexInfo.setIndexName("indexName");
		indexInfo.setIndexType("indexType");
		List<IndexInfo> indexInfos = Arrays.asList(indexInfo);
		schema.setIndexLists(indexInfos);
		return schema;
	}
    public static void main( String[] args )
    {
        try {
        	String path = "D://file.db";
        	OutputStream out = new FileOutputStream(path); 
			AvroHelpper.parseJson(out,buildTestSchema());
			System.out.println("完成avro序列化");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
