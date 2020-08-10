package com.huabo.gkb.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;

public interface BaseService<T,V extends Serializable> {
	//查询多条数据
	public PageInfo<T> SelectMapper(T record,int pageNumber,int pageSize);
	//主键查询�?�?
	public T SelectByPrimaryKey(V key);
	//查询�?�?
	public PageInfo<T> selectAll(int pageNumber,int pageSize);
	public List<T> selectAll();
	
	//保存�?个实体，null的属性也会保存，不会使用数据库默认�??
	
	public int insert(T record);
	//说明：保存一个实体，null的属性不会保存，会使用数据库默认�?
	public int insertSelective(T record);
	//说明：根据主键更新实体全部字段，null值会被更�?
	public int updateByPrimaryKey(T record);
	//说明：根据主键更新属性不为null的�??
	int updateByPrimaryKeySelective(T record);
	//说明：根据实体属性作为条件进行删除，查询条件使用等号
	int delete(T record);
	//说明：根据主键字段进行删除，方法参数必须包含完整的主键属�?
	int deleteByPrimaryKey(Object key);
	//说明：根据Example条件进行查询
	PageInfo<T> selectByExample(Object example,int pageNumber,int pageSize);
	//不分页查询
	List<T> selectByExample(Object example);
	//说明：根据Example条件更新实体record包含的全部属性，null值会被更�?
	int updateByExample(@Param("record") T record, @Param("example") Object example);
	//说明：根据Example条件更新实体record包含的不是null的属性�??
	int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);
	//说明：根据Example条件删除数据
	int deleteByExample(Object example);
	/*说明：根据主键字符串进行查询，类中只有存在一个带有@Id注解的字�?
	List<T> selectByIds(String ids);
	//说明：根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段，格式为（�?1”，�?2”）
	int deleteByIds(String ids);*/
	//暂时不要用，准备是调用sqlID和参数列表，执行查询，返回结�?
	PageInfo<T> selectBySql(Class cls,String sqlId,Map args);
	
}
