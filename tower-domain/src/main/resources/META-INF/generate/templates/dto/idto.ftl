package ${package}.dto;

import com.tower.service.domain.AbsDTO;
import com.tower.service.domain.IResult;


public class ${name}Dto extends AbsDTO implements IResult {
	
<#list fields as field>

	private ${field.y.type.name} ${field.y.name};
	public ${field.y.type.name} get${field.x}(){
		return ${field.y.name};
	}
	
	public void set${field.x}(${field.y.type.name} ${field.y.name}){
		this.${field.y.name} = ${field.y.name};
	}
	
</#list>	
	
}
