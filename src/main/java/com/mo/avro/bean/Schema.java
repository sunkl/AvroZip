package com.mo.avro.bean;
import java.util.List;

public class Schema {
    private long schemaId;
    private String schemaName;
    private String switchInfo;
    private String desc;
    private List<Field> fieldList;
    private List<IndexInfo> indexLists;
    private String ddlDesc; 
    public static class Field{
        private Long id;
        private String name;
        private String desc;
        private String type;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
        
    }
 
    public static class IndexInfo{
        private Long indexId;
        private String indexName;
        private String indexDesc;
        private String indexType;
        private String[] indexColumns;
		public Long getIndexId() {
			return indexId;
		}
		public void setIndexId(Long indexId) {
			this.indexId = indexId;
		}
		public String getIndexName() {
			return indexName;
		}
		public void setIndexName(String indexName) {
			this.indexName = indexName;
		}
		public String getIndexDesc() {
			return indexDesc;
		}
		public void setIndexDesc(String indexDesc) {
			this.indexDesc = indexDesc;
		}
		public String getIndexType() {
			return indexType;
		}
		public void setIndexType(String indexType) {
			this.indexType = indexType;
		}
		public String[] getIndexColumns() {
			return indexColumns;
		}
		public void setIndexColumns(String[] indexColumns) {
			this.indexColumns = indexColumns;
		}
        
    }



	public long getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(long schemaId) {
		this.schemaId = schemaId;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getSwitchInfo() {
		return switchInfo;
	}

	public void setSwitchInfo(String switchInfo) {
		this.switchInfo = switchInfo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	public List<IndexInfo> getIndexLists() {
		return indexLists;
	}

	public void setIndexLists(List<IndexInfo> indexLists) {
		this.indexLists = indexLists;
	}

	public String getDdlDesc() {
		return ddlDesc;
	}

	public void setDdlDesc(String ddlDesc) {
		this.ddlDesc = ddlDesc;
	}
    
}