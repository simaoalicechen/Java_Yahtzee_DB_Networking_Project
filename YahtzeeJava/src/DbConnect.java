//package yahtzeegame;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {

	Connection connection = null;
	PreparedStatement ps = null;

	public DbConnect() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:java.db");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
			;
		}

		System.out.println("Database connected");
	}

	public static void main(String[] args) throws SQLException {
		DbConnect dbConnect = new DbConnect();
		System.out.println(dbConnect.GetUser());
	}

	public void InsertUser(String name, int points) throws SQLException {
		try {
			String sql = "insert into User(name,points) values (?,?)";
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);

			ps.setString(1, name);
			ps.setInt(2, points);

			ps.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("finally")
	public List<User> GetUser() {

		ArrayList<User> users = new ArrayList<>();
		try {
			ResultSet rs = null;
			String sql = "select * from User";
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();
			User b = new User();
			Class clazz = b.getClass();
			while (rs.next()) {
				Object t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {
					Object columnVal = rs.getObject(i + 1);

					String columnLabel = rsmd.getColumnLabel(i + 1);

					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnVal);
				}
				users.add((User) t);
			}
		} catch (IllegalAccessException | InstantiationException | SQLException illegalAccessException) {
			illegalAccessException.printStackTrace();
		} catch (NoSuchFieldException noSuchFieldException) {
			noSuchFieldException.printStackTrace();
		} finally {
			return users;
		}
	}

}