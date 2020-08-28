package com.briup.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.briup.bean.Environment;
import com.briup.bean.FileNameEnums;
import com.briup.util.ConnectionUtil;
import com.briup.util.FileBackupUtil;

/**
 * 存储数据
 * 
 * @author ghost
 *
 */
public class DataStorageImpl implements IDataStorage {
	private static final Logger logger = Logger.getLogger(DataStorageImpl.class);

	/**
	 * 实现数据的存储 
	 * 1.使用jdbc的方案存储数据
	 * 2.使用批处理以提高sql语句的执行效率 
	 * 3.PrepareStatement 可以防止代码注入的问题同时支持动态参数 
	 * 4.调用ConnectionUtils 从连接池中获取连接
	 * 5.存储失败时，将事务回滚并备份数据，等待下一次存储时把备份文件加载进来一起存储
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void dataStorage(Collection<Environment> collection) throws Exception {
		// 读取上一次的备份数据
		Object data = FileBackupUtil.recover(FileNameEnums.SERVER_ACCEPT_DATA_PATH.getPath(), true);
		// 如果备份数据不为空,则把上一次存储失败的数据,追加到数据集合中
		if (data != null) {
			collection.addAll((Collection<? extends Environment>) data);
		}

		// 获取连接
		Connection connection = (new ConnectionUtil()).getConnections(false);
		try {
			// 用来记录天数
			String day1 = "0";
			// 用来记录缓存池中的数据条数
			int count = 0;

			PreparedStatement ps = null;

			// 向表中添加数据
			for (Environment environment : collection) {
				// 获取采集的日期的天数
				String day = environment.getGatheDate().toString().substring(8);

				// 确保日期相同时只有一个ps对象
				if (!day1.equals(day)) {
					if (ps != null) {
						ps.executeBatch();
						ps.clearBatch();
						ps.close();
					}
					day1 = day;
					// 将在不同日期采集到的数据添加到不同的表中
					String sql = "insert into tbl_data_" + day + " values(common_seq.nextval,?,?,?,?,?,?,?,?,?)";
					ps = connection.prepareStatement(sql);
				}

				ps.setString(1, environment.getSrcId());
				ps.setString(2, environment.getDevId());
				ps.setLong(3, environment.getRegionId());
				ps.setString(4, environment.getName());
				ps.setLong(5, environment.getCount());
				ps.setInt(6, environment.getStates());
				ps.setDouble(7, environment.getData());
				ps.setInt(8, environment.getRewiceState());
				ps.setDate(9, environment.getGatheDate());

				ps.addBatch();
				count++;

				// 当缓存池中的数据达到100条时,刷新缓存
				if (count % 100 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}

			// 当循环结束后,如果缓存池中还有未刷新的数据,刷新缓存
			if (ps != null) {
				ps.clearBatch();
				ps.clearBatch();
				ps.close();
			}

			connection.commit();
			// 关闭连接
			connection.close();
			logger.info("数据存储成功");
		} catch (Exception e) {
			e.printStackTrace();
			// 如果发生异常,将事务回滚，备份数据,等下次一起入库
			connection.rollback();
			FileBackupUtil.store(FileNameEnums.SERVER_ACCEPT_DATA_PATH.getPath(), collection);
		}

	}

}
