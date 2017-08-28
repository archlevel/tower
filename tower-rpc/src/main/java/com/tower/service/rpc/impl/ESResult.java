package com.tower.service.rpc.impl;

import io.searchbox.core.SearchResult;

import com.google.gson.Gson;
import com.tower.service.rpc.IResult;

public class ESResult extends SearchResult implements IResult {

	public ESResult(Gson gson) {
		super(gson);
	}

	public ESResult(ESResult searchResult) {
		super(searchResult);
	}

}
