<?xml version="1.0" encoding="ISO-8859-1" ?>
<!-- ~ Licensed to the Apache Software Foundation (ASF) under one ~ or more 
	contributor license agreements. See the NOTICE file ~ distributed with this 
	work for additional information ~ regarding copyright ownership. The ASF 
	licenses this file ~ to you under the Apache License, Version 2.0 (the ~ 
	"License"); you may not use this file except in compliance ~ with the License. 
	You may obtain a copy of the License at ~ ~ http://www.apache.org/licenses/LICENSE-2.0 
	~ ~ Unless required by applicable law or agreed to in writing, ~ software 
	distributed under the License is distributed on an ~ "AS IS" BASIS, WITHOUT 
	WARRANTIES OR CONDITIONS OF ANY ~ KIND, either express or implied. See the 
	License for the ~ specific language governing permissions and limitations 
	~ under the License. -->
<!DOCTYPE taglib PUBLIC "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN"
  "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd">

<taglib>
	<tlib-version>1.1.2</tlib-version>

	<jsp-version>1.2</jsp-version>

	<short-name>Apache Shiro</short-name>

	<uri>http://shiro.apache.org/tags</uri>

	<description>Apache Shiro JSP Tag Library.</description>
	<tag>
		<name>hasPermission</name>
		<tag-class>com.tower.service.web.auth.SecurityTag</tag-class>
		<body-content>JSP</body-content>
		<description>Displays body content only if the current Subject (user)
			'has' (implies) the specified permission (i.e the user has the
			specified ability).
		</description>
		<attribute>
			<name>name</name>
			<required>true</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
</taglib>
