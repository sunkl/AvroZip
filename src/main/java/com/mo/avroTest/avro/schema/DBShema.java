package com.mo.avroTest.avro.schema;

import org.apache.avro.Schema;
import org.apache.avro.io.JsonDecoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DBShema {
	private final static ObjectMapper om = new ObjectMapper();
	public static ObjectNode createFieldElement(String name,String type){
		ObjectNode field = om.createObjectNode();
		field.put("name", name);
		field.put("type", type);
		return field;
	}
	public  static ObjectNode createFieldElement(String name,ObjectNode type){
		ObjectNode field = om.createObjectNode();
		field.put("name", name);
		field.put("type", type);
		return field;
	}
	public static ObjectNode buildTableSchame(){
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "table");
		ArrayNode fields = om.createArrayNode();
		fields.add( DBShema.createFieldElement("tableName","string" ));
		fields.add(DBShema.createFieldElement("colNum","int" ));
		fields.add(DBShema.createFieldElement("ownerDb", "string"));
		on.put("fields", fields);
		return on;
	}
	public static ObjectNode buildColumnSchema(){
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "column");
		ArrayNode fields = om.createArrayNode();
		fields.add(DBShema.createFieldElement("colId", "int"));
		fields.add(DBShema.createFieldElement("colName", "string"));
		fields.add(DBShema.createFieldElement("descript","string"));
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
	public static String buildTableScriptSchema(){
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "tableScript");
		ArrayNode fields = om.createArrayNode();
		fields.add(buildArrayType("tables", buildTableSchame()));
		fields.add(buildArrayType("columns", buildColumnSchema()));
		on.put("fields",fields);
		
		return on.toString();
	}
	public static  void main(String[] args) {
		ObjectNode on = om.createObjectNode();
		on.put("type", "record");
		on.put("name", "tableScript");
		ArrayNode fields = om.createArrayNode();
		fields.add(buildArrayType("zz",buildTableSchame()));
//		ObjectNode type = om.createObjectNode();
//		type.put("type", "array");
//		type.put("items", "string");
//		ObjectNode field2 = om.createObjectNode();
//		field2.put("name", "sdf");
//		field2.put("type",type);
//		fields.add(field2);
		on.put("fields",fields);
//		System.out.println(Schema.parse(on.toString()));
		System.out.println(Schema.parse(buildColumnSchema().toString()));
	}
}
