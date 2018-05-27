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
	public static Record creadFieldRecord(com.mo.avro.bean.Schema.Field fieldData,Schema fieldMateData){
		Record fieldRecord = new GenericData.Record(fieldMateData);	 
		 fieldRecord.put("id", fieldData.getId().longValue());
		 fieldRecord.put("name", fieldData.getName());
		 fieldRecord.put("desc", fieldData.getDesc());
		 fieldRecord.put("type", fieldData.getType());
		return fieldRecord;
	}
	public static Array<Record> createFieldRecords(List<com.mo.avro.bean.Schema.Field> fields,Schema fieldMateData){
		Array<Record> fieldRecords = new GenericData.Array<Record>(fields.size(), Schema.createArray(fieldMateData));
		for(com.mo.avro.bean.Schema.Field field:fields){
			fieldRecords.add(creadFieldRecord(field, fieldMateData));
		}
		return fieldRecords;
	}
	
	public static Record createIndexInfoRecord(IndexInfo indexInfo,Schema indexInfoMateData){
		Record indexInforRecord = new GenericData.Record(indexInfoMateData);
        indexInforRecord.put("indexId", indexInfo.getIndexId().longValue());
        indexInforRecord.put("indexName",indexInfo.getIndexName());
        indexInforRecord.put("indexDesc", indexInfo.getIndexDesc());
        indexInforRecord.put("indexType", indexInfo.getIndexType()); 
        indexInforRecord.put("indexColumns", Arrays.asList(indexInfo.getIndexColumns())); 
        
		return indexInforRecord;
	}
	public static Array<Record> createIndexInfoRecords(List<IndexInfo> indexInfos ,Schema indexInfoMateData){
		Array<Record> indexInfoRecords = new GenericData.Array<Record>(indexInfos.size(), Schema.createArray(indexInfoMateData)); 
		for(IndexInfo indexInfo:indexInfos){
			indexInfoRecords.add(createIndexInfoRecord(indexInfo, indexInfoMateData));
		}
		return indexInfoRecords;
	}
	public static Record createTableScriptRecord(com.mo.avro.bean.Schema data,Schema indexInfoMateData,Schema fieldMateData,Schema schemaMateData){ 
		Record schemaRecord = new GenericData.Record(schemaMateData);
		Array<Record> indexInfoRecords= createIndexInfoRecords(data.getIndexLists(),indexInfoMateData);
		Array<Record> fieldRecords = createFieldRecords(data.getFieldList(),fieldMateData);
		schemaRecord.put("indexLists",indexInfoRecords );
		schemaRecord.put("fieldList", fieldRecords);
		schemaRecord.put("schemaId", data.getSchemaId());
		schemaRecord.put("schemaName", data.getSchemaName());
		schemaRecord.put("switchInfo", data.getSwitchInfo());
		schemaRecord.put("desc", data.getDesc());
		schemaRecord.put("ddlDesc", data.getDdlDesc()); 
		return schemaRecord;
	}
	public static void parseJson(OutputStream out,com.mo.avro.bean.Schema date) throws Exception{
		Schema fieldMateData = Schema.parse(DBShema.buildFieldMateData().toString()); 
		Schema indexInfoMateData = Schema.parse(DBShema.buildIndexInfoMateData().toString()); 
		Schema schemaMateData = Schema.parse(DBShema.buildSchemaMateData().toString()); 
		DataFileWriter<Record> writer = new DataFileWriter<GenericData.Record>(new GenericDatumWriter<Record>(schemaMateData)).create(schemaMateData, out);
		Record record = createTableScriptRecord(date,indexInfoMateData, fieldMateData, schemaMateData);
		writer.append(record);
		writer.close();
	}
	
}
