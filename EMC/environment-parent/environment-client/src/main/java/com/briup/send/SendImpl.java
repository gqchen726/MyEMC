package com.briup.send;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

import com.briup.bean.Environment;
import com.briup.bean.FileNameEnums;
import com.briup.util.FileBackupUtil;

/**
 * 发送数据
 * 
 * @author ghost
 *
 */
public class SendImpl implements ISend {
	/**
	 * 向服务端发送collection数据(对象) 
	 *  Socket tcp/ip 
	 *    通过对象输出流发送数据 
	 *      当发生异常时备份数据
	 * @param collection 采集好的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void send(Collection<Environment> collection) {
		try {
			// 创建客户端对象
			Socket socket = new Socket("127.0.0.1" , 8666);
			// 发送前把上次发送失败产生的备份数据加载进来
			Object data = FileBackupUtil.recover(FileNameEnums.CLIENT_SEND_DATA_PATH.getPath(), true);
			if (data != null) {
				collection.addAll((Collection<? extends Environment>) data);
			}
			// 构建对象输出流
			ObjectOutputStream oos = 
					new ObjectOutputStream(
							new BufferedOutputStream(
									socket.getOutputStream()));
			// 将数据集合用对象输出流序列化输出
			oos.writeObject(collection);
			oos.flush();
			// 释放资源
			oos.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
			// 如果发送数据失败,将数据备份,待下次发送加载进来
			try {
				FileBackupUtil.store(FileNameEnums.CLIENT_SEND_DATA_PATH.getPath(), collection);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
