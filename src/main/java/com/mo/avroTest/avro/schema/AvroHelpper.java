package com.mo.avroTest.avro.schema;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.SchemaBuilder.FieldBuilder;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Array;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumWriter;

import com.mo.avro.bean.Schema.IndexInfo;

public class AvroHelpper {
	/**
	 * 序列化field对象
	 * @param fieldData
	 * @param fieldMateData
	 * @return
	 */
	public static Record creadFieldRecord(com.mo.avro.bean.Schema.Field fieldData,Schema fieldMateData){
		Record fieldRecord = new GenericData.Record(fieldMateData);	 
		 fieldRecord.put("id", fieldData.getId().longValue());
		 fieldRecord.put("name", fieldData.getName());
		 fieldRecord.put("desc", fieldData.getDesc());
		 fieldRecord.put("type", fieldData.getType());
		return fieldRecord;
	}
	/**
	 * 将field容器中对象序列化
	 * @param fields
	 * @param fieldMateData
	 * @return
	 */
	public static Array<Record> createFieldRecords(List<com.mo.avro.bean.Schema.Field> fields,Schema fieldMateData){
		Array<Record> fieldRecords = new GenericData.Array<Record>(fields.size(), Schema.createArray(fieldMateData));
		for(com.mo.avro.bean.Schema.Field field:fields){
			/*序列化field对象*/
			fieldRecords.add(creadFieldRecord(field, fieldMateData));
		}
		return fieldRecords;
	}
	/**
	 * 化indexInfo对序列象
	 * @param indexInfo
	 * @param indexInfoMateData
	 * @return
	 */
	public static Record createIndexInfoRecord(IndexInfo indexInfo,Schema indexInfoMateData){
		Record indexInforRecord = new GenericData.Record(indexInfoMateData);
        indexInforRecord.put("indexId", indexInfo.getIndexId().longValue());
        indexInforRecord.put("indexName",indexInfo.getIndexName());
        indexInforRecord.put("indexDesc", indexInfo.getIndexDesc());
        indexInforRecord.put("indexType", indexInfo.getIndexType()); 
        indexInforRecord.put("indexColumns", Arrays.asList(indexInfo.getIndexColumns())); 
		return indexInforRecord;
	}
	/**
	 * 将IndexInfo容器中对象序列化成avro对象
	 * @param indexInfos
	 * @param indexInfoMateData
	 * @return
	 */
	public static Array<Record> createIndexInfoRecords(List<IndexInfo> indexInfos ,Schema indexInfoMateData){
		Array<Record> indexInfoRecords = new GenericData.Array<Record>(indexInfos.size(), Schema.createArray(indexInfoMateData)); 
		for(IndexInfo indexInfo:indexInfos){
			/*化indexInfo对序列象*/
			indexInfoRecords.add(createIndexInfoRecord(indexInfo, indexInfoMateData));
		}
		return indexInfoRecords;
	}
	/***
	 * 将schema 序列化程avro的record
	 * @param data
	 * @param schemaMateData 数据元（json格式）
	 * @return
	 */
	public static Record createTableScriptRecord(com.mo.avro.bean.Schema data,Schema schemaMateData){ 
		/*获取field对象的数据元，并编译*/
		Schema fieldMateData = Schema.parse(DBShema.buildFieldMateData().toString()); 
		/*获取indexInfo对象的数据元，并编译*/
		Schema indexInfoMateData = Schema.parse(DBShema.buildIndexInfoMateData().toString()); 
		/*初始化schema对象的序列化器*/
		Record schemaRecord = new GenericData.Record(schemaMateData);
		/*将indexInfo容器中的数据序列化成avro对象*/
		Array<Record> indexInfoRecords= createIndexInfoRecords(data.getIndexLists(),indexInfoMateData);
		/*将field容器中的数据序列化成avro对象*/
		Array<Record> fieldRecords = createFieldRecords(data.getFieldList(),fieldMateData);
		/** 序列化schema*/
		schemaRecord.put("indexLists",indexInfoRecords );
		schemaRecord.put("fieldList", fieldRecords);
		schemaRecord.put("schemaId", data.getSchemaId());
		schemaRecord.put("schemaName", data.getSchemaName());
		schemaRecord.put("switchInfo", data.getSwitchInfo());
		schemaRecord.put("desc", data.getDesc());
		schemaRecord.put("ddlDesc", data.getDdlDesc()); 
		return schemaRecord;
	}
	/**
	 * 将schema序列化程avro的文件
	 * @param out 输出流
	 * @param date 输入数据
	 * @throws Exception
	 */
	public static void seriToAvro(OutputStream out,com.mo.avro.bean.Schema date) throws Exception{
		/*获取schema的数据元配置（json格式，json格式不需要编译）*/
		Schema schemaMateData = Schema.parse(DBShema.buildSchemaMateData().toString()); 
		/*初始化avro序列化流*/
		DataFileWriter<Record> writer = new DataFileWriter<GenericData.Record>(new GenericDatumWriter<Record>(schemaMateData)).create(schemaMateData, out);
		/*将bean转化为可以avro的record*/
		Record record = createTableScriptRecord(date,schemaMateData);
		/*将序列化完成record写到流中*/
		writer.append(record);
		writer.close();
	}
	
}
