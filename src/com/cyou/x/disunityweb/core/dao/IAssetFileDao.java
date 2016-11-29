package com.cyou.x.disunityweb.core.dao;

import com.cyou.x.disunityweb.core.domain.XAssetFile;


public interface IAssetFileDao {
	
	public void add(XAssetFile asset);
	public XAssetFile get(int id);
	public void update(XAssetFile asset);
	public void delete(XAssetFile asset);
	
}
