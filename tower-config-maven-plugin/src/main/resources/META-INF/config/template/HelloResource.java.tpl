package com.#{company}.service.#{artifactId}.resource;

import com.tower.service.resource.IResource;
import com.tower.service.web.impl.AbsResource;

public class HelloResource extends AbsResource implements IResource{
	
	public void hello(){
		System.out.println("hello");
	}
}

