package com.briup.store;

import java.util.Collection;

import com.briup.bean.Environment;
/**
 * 对数据进行存储
 * 
 * @author ghost
 *
 */
public interface IDataStorage {
	/**
	 * 对服务端接收到的数据入库
	 * 1.注册驱动
	 * 2.获取连接
	 * 3.创建PrepareStatement对象
	 * 4.创建表
	 * 5.遍历数据集合将数据存储到数据库中
	 * 
	 * @param collection 要存储的数据
	 */
	void dataStorage(Collection<Environment> collection) throws Exception ;
}
