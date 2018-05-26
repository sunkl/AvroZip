package com.mo.avroTest.avro.schema;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.SchemaBuilder.FieldBuilder;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Array;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumWriter;

public class AvroHelpper {
	public static Record createTable(String tableName,int colNum,String ownerDb,Schema schcema){
		Record tableRecord = new GenericData.Record(schcema);		
		tableRecord.put("tableName",tableName);
		tableRecord.put("colNum", colNum);
		tableRecord.put("ownerDb", ownerDb);
		return tableRecord;
	}
	public static Array<Record> createTableRecords(Schema tableSchema){
		Array<Record> tableRecords = new GenericData.Array<Record>(3, Schema.createArray(tableSchema));
		tableRecords.add(createTable("table1", 2, "dbName", tableSchema));
		tableRecords.add(createTable("table2", 3, "dbName", tableSchema));
		tableRecords.add(createTable("table3", 4, "dbName", tableSchema));
		return tableRecords;
	}
	
	public static Record createColumn(int colId,String colName,String descript,Schema schcema){
		Record columnRcoRecord = new GenericData.Record(schcema);		
		columnRcoRecord.put("colId",colId);
		columnRcoRecord.put("colName", colName);
		columnRcoRecord.put("descript", descript); 
		return columnRcoRecord;
	}
	public static Array<Record> createColumnRecords(Schema colSchema){
		Array<Record> tableRecords = new GenericData.Array<Record>(3, Schema.createArray(colSchema)); 
		tableRecords.add(createColumn(1, "col1","this col1" ,colSchema));
		tableRecords.add(createColumn(2, "col2","this col2", colSchema));
		tableRecords.add(createColumn(3, "col3", "this col3",colSchema));
		return tableRecords;
	}
	public static Record createTableScriptRecord(Schema tableSchema,Schema columnSchema,Schema tableSrpritSchema){ 
		Record tableScrpitRecord = new GenericData.Record(tableSrpritSchema);
		Array<Record> columnRecords = createColumnRecords(columnSchema);
		Array<Record> tableRecords = createTableRecords(tableSchema);
		tableScrpitRecord.put("tables",tableRecords );
		tableScrpitRecord.put("columns", columnRecords);
		return tableScrpitRecord;
	}
	public static void parseJson(OutputStream out) throws Exception{
		Schema tableSchema = Schema.parse(DBShema.buildTableSchame().toString()); 
		Schema columnSchema = Schema.parse(DBShema.buildColumnSchema().toString()); 
		Schema tableScriptSchema = Schema.parse(DBShema.buildTableScriptSchema().toString()); 
		DataFileWriter<Record> writer = new DataFileWriter<GenericData.Record>(new GenericDatumWriter<Record>(tableScriptSchema)).create(tableScriptSchema, out);
		writer.append(createTableScriptRecord(tableSchema, columnSchema, tableScriptSchema));
		writer.close();
	}
	
}
