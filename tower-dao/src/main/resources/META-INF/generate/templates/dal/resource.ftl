package ${package}.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import com.tower.core.util.ObjectUtils;
import com.tower.service.openapi.aop.Auth;
import com.tower.service.openapi.exception.APIException;
import com.tower.service.openapi.exception.ErrorCodes;
import com.tower.service.openapi.resource.AbstractResource;
import com.tower.service.openapi.result.Result;

@Path("properties/{property_id}")
@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class ${className}Resource extends AbstractResource{
	
	/**
	 * ${methodDes}
	 *
	 * @return
	 */
	<#if urlMethod="get" || urlMethod="delete">
	@${urlMethod?upper_case}
	@Path("${methodName}")
	<#if isAuth="y">@Auth</#if>
	public Response ${methodName}(@PathParam("property_id") Integer proId,
			<#if inparslist??>
				<#list inparslist as inpar>
					@QueryParam("${inpar.name}") ${inpar.type?cap_first} ${inpar.name}<#if inpar_has_next>,</#if>
				</#list>
			</#if>) throws Exception {
			<#if inparslist??>
				<#list inparslist as inpar>
					<#if inpar.allowNull="y">
					if (ObjectUtils.isEmpty(${inpar.name})) {
						throw new APIException(ErrorCodes.INVALID_PARAMETER, "${inpar.desc}必须提供");
					}
					</#if>
				</#list>
			</#if>
			//TODO Auto-generated catch block
			Result<?> result = null;
			return Response.ok(result).build();
	}
	<#elseif urlMethod="post" || urlMethod="put">
	@${urlMethod?upper_case}
	@Path("${methodName}")
	<#if isAuth="y">@Auth</#if>	
	public Response add(<#if inparslist??><#list inparslist as inpar>@FormParam("${inpar.name}") ${inpar.type?cap_first} ${inpar.name}<#if inpar_has_next>,</#if></#list></#if>) throws APIException {
			<#if inparslist??>
				<#list inparslist as inpar>
					<#if inpar.allowNull="y">
			if (ObjectUtils.isEmpty(${inpar.name})) {
				throw new APIException(ErrorCodes.INVALID_PARAMETER, "${inpar.desc}必须提供");
			}
					</#if>
				</#list>
			</#if>
			Result<?> result = null;
			return Response.ok(result).build();
			
	}
	</#if>
	
}
