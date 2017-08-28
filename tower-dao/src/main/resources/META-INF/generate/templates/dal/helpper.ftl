package ${package}.dao.model;

import java.util.List;

import ${package}.dao.model.${name}Helpper.Criteria;
import com.tower.service.dao.ibatis.AbsHelpper;
import com.tower.service.dao.ibatis.GeneratedCriteria;

public class ${name}Helpper extends AbsHelpper<Criteria> {

    /*
     * (non-Javadoc)
     * 
     * @see com.tower.service.dao.IHelpper#createCriteriaInternal()
     */
    public Criteria createCriteriaInternal() {
        return new Criteria();
    }

    public static class Criteria extends GeneratedCriteria {
       <#list cols as col>
		/**
		 * ${col.desc}
		 */
		 <#if tab.pkFieldNum==1 && col.isPK="yes"> 
		 	<#if col.type.javaType="Integer" || col.type.javaType="Long" || col.type.javaType="Float" || col.type.javaType="Double" || col.type.javaType="java.math.BigInteger">
		  	// is null
		  	public Criteria andIdIsNull() {
		    	addCriterion("${col.name} is null");
		    	return (Criteria) this;
		  	}
		
		  	// is not null
		  	public Criteria andIdIsNotNull() {
		    	addCriterion("${col.name} is not null");
		    	return (Criteria) this;
		  	}
		
		  	// =
		  	public Criteria andIdEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} =", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// <>
		  	public Criteria andIdNotEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} <>", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// >
		  	public Criteria andIdGreaterThan(${col.type.javaType} value) {
		    	addCriterion("${col.name} >", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// >=
		  	public Criteria andIdGreaterThanOrEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} >=", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// <
		  	public Criteria andIdLessThan(${col.type.javaType} value) {
		    	addCriterion("${col.name} <", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// <=
		  	public Criteria andIdLessThanOrEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} <=", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// like
		  	public Criteria andIdLike(${col.type.javaType} value) {
		    	addCriterion("${col.name} like", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// not like
		  	public Criteria andIdNotLike(${col.type.javaType} value) {
		    	addCriterion("${col.name} not like", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// in
		  	public Criteria andIdIn(List<${col.type.javaType}> values) {
		    	addCriterion("${col.name} in", values, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// not in
		  	public Criteria andIdNotIn(List<${col.type.javaType}> values) {
		    	addCriterion("${col.name} not in", values, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// between
		  	public Criteria andIdBetween(${col.type.javaType} value1, ${col.type.javaType} value2) {
		    	addCriterion("${col.name} between", value1, value2, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// not between
		  	public Criteria andIdNotBetween(${col.type.javaType} value1, ${col.type.javaType} value2) {
		   		addCriterion("${col.name} not between", value1, value2, "${col.name}");
		    	return (Criteria) this;
		  	}
		  	</#if>
			<#elseif col.isPK="no">	
			// is null
		  	public Criteria and${col.methodName}IsNull() {
		    	addCriterion("${col.name} is null");
		    	return (Criteria) this;
		  	}
		
		  	// is not null
		  	public Criteria and${col.methodName}IsNotNull() {
		    	addCriterion("${col.name} is not null");
		    	return (Criteria) this;
		  	}
		
		  	// =
		  	public Criteria and${col.methodName}EqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} =", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// <>
		  	public Criteria and${col.methodName}NotEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} <>", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// >
		  	public Criteria and${col.methodName}GreaterThan(${col.type.javaType} value) {
		    	addCriterion("${col.name} >", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// >=
		  	public Criteria and${col.methodName}GreaterThanOrEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} >=", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// <
		  	public Criteria and${col.methodName}LessThan(${col.type.javaType} value) {
		    	addCriterion("${col.name} <", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// <=
		  	public Criteria and${col.methodName}LessThanOrEqualTo(${col.type.javaType} value) {
		    	addCriterion("${col.name} <=", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// like
		  	public Criteria and${col.methodName}Like(${col.type.javaType} value) {
		    	addCriterion("${col.name} like", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// not like
		  	public Criteria and${col.methodName}NotLike(${col.type.javaType} value) {
		    	addCriterion("${col.name} not like", value, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// in
		  	public Criteria and${col.methodName}In(List<${col.type.javaType}> values) {
		    	addCriterion("${col.name} in", values, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// not in
		  	public Criteria and${col.methodName}NotIn(List<${col.type.javaType}> values) {
		    	addCriterion("${col.name} not in", values, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// between
		  	public Criteria and${col.methodName}Between(${col.type.javaType} value1, ${col.type.javaType} value2) {
		    	addCriterion("${col.name} between", value1, value2, "${col.name}");
		    	return (Criteria) this;
		  	}
		
		  	// not between
		  	public Criteria and${col.methodName}NotBetween(${col.type.javaType} value1, ${col.type.javaType} value2) {
		   		addCriterion("${col.name} not between", value1, value2, "${col.name}");
		    	return (Criteria) this;
		  	}
	  	</#if>
	</#list>
    }
 
}
