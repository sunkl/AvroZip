package com.mo.avroTest.avro.schema;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.io.JsonDecoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mo.avro.bean.Schema.IndexInfo;

public class DBShema {
	private final static ObjectMapper om = new ObjectMapper();
	public static ObjectNode createFieldElement(String name,String type){
		ObjectNode field = om.createObjectNode();
		field.put("name", name);
		field.put("type", type);
		return field;
	}
	public static ObjectNode createArrayFieldElement(String name,String items){
		ObjectNode fieldNode = om.createObjectNode();
		fieldNode.put("name", name);
		ObjectNode type = om.createObjectNode();
		type.put("type","array");
		type.put("items",items);
		fieldNode.put("type", type);
		return fieldNode;
	}
	public static ObjectNode buildIndexInfoMateData(){
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "indexinfo");
		ArrayNode fields = om.createArrayNode();
		fields.add( DBShema.createFieldElement("indexId","long" ));
		fields.add(DBShema.createFieldElement("indexName","string" ));
		fields.add(DBShema.createFieldElement("indexDesc", "string"));
		fields.add(DBShema.createFieldElement("indexType", "string"));
		fields.add(DBShema.createArrayFieldElement("indexColumns", "string"));
		on.put("fields", fields);
		return on;
	}
	public static ObjectNode buildFieldMateData(){
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "field");
		ArrayNode fields = om.createArrayNode();
		fields.add(DBShema.createFieldElement("id", "long"));
		fields.add(DBShema.createFieldElement("name", "string"));
		fields.add(DBShema.createFieldElement("desc","string"));
		fields.add(DBShema.createFieldElement("type","string"));
		on.put("fields",fields);
		return on;
	}
	public static ObjectNode buildArrayType(String name,ObjectNode items){
		ObjectNode on = om.createObjectNode();
		on.put("name", name);
		ObjectNode typeNode = om.createObjectNode();
		typeNode.put("type", "array");
		typeNode.put("items", items);
		on.put("type",typeNode);
		return on;
	}

	public static String buildSchemaMateData(){
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "schema");
		ArrayNode fields = om.createArrayNode();
		fields.add(buildArrayType("indexLists", buildIndexInfoMateData()));
		fields.add(buildArrayType("fieldList", buildFieldMateData()));
		fields.add(DBShema.createFieldElement("schemaId", "long"));
		fields.add(DBShema.createFieldElement("schemaName", "string")); 
		fields.add(DBShema.createFieldElement("switchInfo", "string"));
		fields.add(DBShema.createFieldElement("desc", "string"));
		fields.add(DBShema.createFieldElement("ddlDesc", "string"));  
		on.put("fields",fields);
		return on.toString();
	}
	public static  void main(String[] args) { 
	}
}
