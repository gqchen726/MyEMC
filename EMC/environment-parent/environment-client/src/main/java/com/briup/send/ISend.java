package com.briup.send;

import java.util.Collection;

import com.briup.bean.Environment;

/**
 * 发送数据
 * @author ghost
 *
 */
public interface ISend {
	/**
	 * 将解析好的数据发送给服务端
	 * 1.创建客户端连接
	 * 2.用对象输出流把接收到的数据序列化到服务端
	 * @param collection 客户端发送的数据集合
	 */
	void send(Collection<Environment> collection);
}
