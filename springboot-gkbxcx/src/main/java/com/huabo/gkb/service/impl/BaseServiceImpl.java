package com.huabo.gkb.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huabo.gkb.service.BaseService;

import tk.mybatis.mapper.common.Mapper;

public class BaseServiceImpl<T, V extends Serializable> implements  BaseService<T,V> {
	
	@Autowired  
	private Mapper<T> mapper;
	@Autowired
	private SqlSession sqlSessionTemplate;

	@Override
	public PageInfo<T> SelectMapper(T record,int pageNumber,int pageSize) {
		// TODO Auto-generated method stub
		PageInfo<T> pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> mapper.select(record));
		return pageInfo;
	}

	@Override
	public T SelectByPrimaryKey(V key) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(key);
	}
	
	@Override
	public PageInfo<T> selectAll(int pageNumber,int pageSize) {
		// TODO Auto-generated method stub
		PageInfo<T> pageInfo = PageHelper.startPage(pageNumber,pageSize).doSelectPageInfo(() -> mapper.select(null));
		return pageInfo;
	}

	@Override
	public int insert(T record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(T record) {
		// TODO Auto-generated method stub
		return mapper.delete(record);
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public PageInfo<T> selectByExample(Object example,int pageNumber,int pageSize) {
		// TODO Auto-generated method stub
		PageInfo<T> pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> mapper.selectByExample(example));
		return pageInfo;
	}

	@Override
	public int updateByExample(T record, Object example) {
		// TODO Auto-generated method stub
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByExampleSelective(T record, Object example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int deleteByExample(Object example) {
		// TODO Auto-generated method stub
		return mapper.deleteByExample(example);
	}


	@Override
	public PageInfo<T> selectBySql(Class cls, String sqlId, Map args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectByExample(Object example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public List<T> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}


}
