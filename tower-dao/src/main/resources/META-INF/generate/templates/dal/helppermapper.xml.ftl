<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
    PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"
    "http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">

<mapper namespace="${package}.dao.ibatis.mapper.${name}HelpperMapper">
	<resultMap id="BaseResultMap" type="${package}.dao.model.${name}">		
		
		<#list cols as col>
			<#if col.isPK="yes" && tab.pkFieldNum==1>
			<#if col.type.javaType="Integer" || col.type.javaType="Long" || col.type.javaType="Float" || col.type.javaType="Double" || col.type.javaType="java.math.BigInteger" || col.type.javaType="String">
			<id property="id" column="${col.name}" />
			</#if>
			<#else>
			<result property="${col.fieldName}" column="${col.name}" />
			</#if>
		</#list>
	</resultMap>
	
	<sql id="Base_Column_List">
		<#list colMaps as col>
			${col.name}<#if col_has_next>,</#if>
		</#list>
	</sql>
	
	<sql id="Helpper_Where_Clause">
    
	    <where>
	      <foreach collection="oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	          <trim prefix="(" prefixOverrides="and" suffix=")">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  AND ${r"${criterion.condition}"}
	                </when>
	                <when test="criterion.singleValue">
	                  AND ${r"${criterion.condition}"} ${r"#{criterion.value}"}
	                </when>
	                <when test="criterion.betweenValue">
	                  AND ${r"${criterion.condition}"} ${r"#{criterion.value}"} AND ${r"#{criterion.secondValue}"}
	                </when>
	                <when test="criterion.listValue">
	                  AND ${r"${criterion.condition}"}
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    ${r"#{listItem}"}
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	          </trim>
	        </if>
	      </foreach>
		</where>
 	</sql>
  	
  	<select id="queryByHelpper" parameterType="${package}.dao.model.${name}Helpper" resultMap="BaseResultMap"  fetchSize="100">
	    SELECT
	    	<if test="distinct">
	      		distinct
	    	</if>
	    	<include refid="Base_Column_List" />
	    FROM 
	    	${r"${TowerTabName}"}
	    	
	      	<include refid="Helpper_Where_Clause" />
	    	
	    	<if test="orderByClause != null">
	      		order by ${r"${orderByClause}"}
	    	</if>
  	</select>
  	
  	<select id="countByHelpper" parameterType="${package}.dao.model.${name}Helpper" resultType="java.lang.Integer">
    	SELECT 
    		count(*) 
    	FROM 
    		${r"${TowerTabName}"}
	     
	     <include refid="Helpper_Where_Clause" />
	     
  	</select>
  
  	<delete id="deleteByHelpper" parameterType="${package}.dao.model.${name}Helpper">
	    DELETE 
	    FROM 
	    	${r"${TowerTabName}"}
		<include refid="Helpper_Where_Clause" />
  	</delete>
  
	<sql id="Update_By_Helpper_Where_Clause">
	    <where>
	      <foreach collection="helpper.oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	          <trim prefix="(" prefixOverrides="and" suffix=")">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  AND ${r"${criterion.condition}"}
	                </when>
	                <when test="criterion.singleValue">
	                  AND ${r"${criterion.condition}"} ${r"#{criterion.value}"}
	                </when>
	                <when test="criterion.betweenValue">
	                  AND ${r"${criterion.condition}"} ${r"#{criterion.value}"} AND ${r"#{criterion.secondValue}"}
	                </when>
	                <when test="criterion.listValue">
	                  AND ${r"${criterion.condition}"}
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    ${r"#{listItem}"}
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	          </trim>
	        </if>
	      </foreach>
	    </where>
  	</sql>
  	<update id="updateByHelpper" parameterType="java.util.Map">
  		UPDATE 
  		${r"${TowerTabName}"}
		<set>
			<#if tab.pkFieldNum==1>
			<#list colMaps as col>
			<#if tab.pkFieldNum==1 && col.isPK="yes" &&  (col.type.javaType="Integer" || col.type.javaType="Long" || col.type.javaType="Float" || col.type.javaType="Double" || col.type.javaType="java.math.BigInteger" || col.type.javaType="String")>
			<if test="record.id !=  null">
				${col.name}=${r"#{record.id"}${r"}"},
			</if>
			<#else>
			<if test="${r"record."}${col.fieldName} !=  null">
				${col.name}=${r"#{record."}${col.fieldName}${r"}"},
			</if>
			</#if>			
			</#list>
			<#else>			
			<#list colMaps as col>
			<#if col.isPK="yes">
			<if test="${r"record."}${col.fieldName} !=  null">
				${col.name}=${r"#{record."}${col.fieldName}${r"}"},
			</if>
			</#if>
			</#list>
			</#if>			
		</set>
		<if test="helpper != null">
			<include refid="Update_By_Helpper_Where_Clause" />
    	</if>
  	</update>
</mapper>
