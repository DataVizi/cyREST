package org.cytoscape.rest.internal.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Cytoscape Column Values")
public class CyColumnValuesModel {
	@ApiModelProperty(value = "Column Name", required=true)
	public String name;
	@ApiModelProperty(value = "Column Values", required=true)
	public List<?> values;
}