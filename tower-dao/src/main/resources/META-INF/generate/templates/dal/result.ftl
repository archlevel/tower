package ${package}.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import com.tower.core.util.SeriObject;

@XmlAccessorType(XmlAccessType.NONE)
public class ${outObjName}Type extends SeriObject{

	<#if outparslist??>
	<#list outparslist as outpar>
	private ${outpar.type?cap_first} ${outpar.name};
	</#list>
	</#if>
	
	public ${outObjName}Type(){
		super();
	}

	public ${outObjName}Type(
		<#if outparslist??>
			<#list outparslist as outpar>
				${outpar.type?cap_first} ${outpar.name}<#if outpar_has_next>,</#if>
			</#list>
		</#if>) {
		super();
		<#if outparslist??>
			<#list outparslist as outpar>
			this.${outpar.name} = ${outpar.name};
			</#list>
		</#if>
		
	}

	<#if outparslist??>
	<#list outparslist as outpar>
	@XmlAttribute(name = "${outpar.name}")
	public ${outpar.type?cap_first} get${outpar.name?cap_first}() {
		return ${outpar.name};
	}
	
	public void set${outpar.name?cap_first}(${outpar.type?cap_first} ${outpar.name}) {
		this.${outpar.name} = ${outpar.name};
	}
	</#list>
	</#if>

}
