package com.briup.gather;

import java.io.RandomAccessFile;
import java.util.Collection;

import org.junit.Test;

import com.briup.bean.Environment;

public class IGatherTest {
	@Test
	public void t1() {

		IGather gather = new GatherImpl();
		Collection<Environment> collection = gather.gather();
		System.out.println(collection.size());
		collection.forEach(System.out::println);
	}


	@Test
	public void t2() {
		String ss = "100|101|2|1280|1|3|032d01|1|1527044690937\r\n" + "100|101|2|1280|1|3|032d01|1|1527044692947";
		String[] split = ss.split("\r\n");
		for (String string : split) {
			System.out.println(string);
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void t3() {
		try {

			String file = "src/main/resources/radwtmp";
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			long flag = 3099927;
			randomAccessFile.seek(flag);
			byte[] b = new byte[1024];
			while (true) {
				String line = randomAccessFile.readLine();
				if (line == null) {
					randomAccessFile.close();
					break;
				}
				System.out.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
