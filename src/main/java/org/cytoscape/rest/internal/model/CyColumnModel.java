package org.cytoscape.rest.internal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Cytoscape Column", description="A column definition in a Cytoscape table")
public class CyColumnModel {
	@ApiModelProperty(value = "Column Name", required=true)
	public String name;
	@ApiModelProperty(value = "Column Data Type", allowableValues="Double,String,Boolean,Long,Integer,List", required=true)
	public String type; //"data type, Double, String, Boolean, Long, Integer",
	@ApiModelProperty(value="If the type of this column is list, this specifies the type of data in the list.", required=false)
	public Boolean listType;
	@ApiModelProperty(value="If true, this column is immutable.", required=true)
	public Boolean immutable; //": "Optional: boolean value to specify immutable or not",
	@ApiModelProperty(value="If true, this column acts as the primary key for this table.", required=true)
	public Boolean primaryKey;// "local": "Optional.  If true, it will be a local column"
	
}