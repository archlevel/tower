<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE taglib PUBLIC "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN" "http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd">

<taglib>
	<tlibversion>1.0</tlibversion>
	<jspversion>1.1</jspversion>
	<shortname>htmlpager</shortname>
	<uri>http://localhost/utils/paging/jsptag</uri>
	<info>a tag lib used in jsp for paged display of volume data</info>
	<tag>
		<name>Pager</name>
		<tagclass>com.tower.service.web.util.PagerTag</tagclass>
		<bodycontent>JSP</bodycontent>
		<attribute>
			<name>onclick</name>
			<required>true</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
</taglib>
