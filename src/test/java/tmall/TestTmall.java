package tmall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class TestTmall {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall?useUnicode=true&characterEncoding=utf8","root","123456");
			Statement s = c.createStatement();
			for (int i = 10; i < 1000; i++) {
				String sqlFormat = "insert into category values(null,'测试分类%d')";
				String sql = String.format(sqlFormat, i);
				s.execute(sql);
			}
			System.out.println("创建10条分类数据");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
