package jinhetech.oracle.generation.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * 单例模式,整个系统只存在一个ConnectionFactory对象
 * 单例模式的好处,所有的静态成员变量外部无法更改,只能在唯一的类实例创建时初始化静态成员变量,
 * 并且外部只能得到唯一的实例来获取数据库连接,而这个实例可以保证每次拿到的都是同一配置下的数据库连接。
 * 这样我们就能保证整个系统只有一个"工厂"在为我们创建数据库连接,只要我们去这个"工厂"申请即可。
 */
public class ConnectionFactory {
	
	/** 数据源，static */
	private static DataSource DS = null;
	
	//静态引用,在类初始化加载时会被执行一次,由于是private的,所以该属性在外部无法被修改,因此也就是保持类加载时执行的那一次的状态。
	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	private static Logger log = Logger.getLogger(ConnectionFactory.class);
	//private的构造方法,外部无法创建该类的对象。
	private ConnectionFactory(){
		//初始化dbcp的DataDource数据源
		try{
			ConnectionFactory.initDbcpDataSource();
			System.out.println("dbcp数据源DataSource初始化成功!");
			System.out.println("ConnectionFactory的单实例被创建......");
		}catch(Exception e){
			System.out.println("dbcp数据源DataSource初始化失败!");
		}
	}
	//返回实例地址,由于这个地址是唯一的,因此完成了单例的设计模式和思路。
	public static ConnectionFactory getInstance(){
		return connectionFactory;
	}

	/**
	 * 从数据源获得一个连接
	 * 
	 * @throws Exception
	 */
	public Connection getConn() throws Exception {
		Connection con = null;
		if (null == DS) {
			initDbcpDataSource();
		}
		if (DS != null) {
			try {
				con = DS.getConnection();
			} catch (Exception e) {
				e.printStackTrace(System.err);
				log.error("无法连接数据库，请检查数据库是否启动！错误原因：" + e.getMessage());
				throw new Exception("无法连接数据库，请检查数据库是否启动！错误原因：" + e.getMessage());
			}
			try {
				// 默认关闭事务
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}
		return con;
	}

	/**
	 * 指定所有参数连接数据源
	 * 
	 * @param connectURI
	 *            数据库
	 * @param username
	 *            用户名
	 * @param pswd
	 *            密码
	 * @param driverClass
	 *            数据库连接驱动名
	 * @param initialSize
	 *            初始连接池连接个数
	 * @param maxActive 
	 *            最大激活连接数
	 * @param maxIdle
	 *            最大闲置连接数
	 * @param maxWait
	 *            获得连接的最大等待毫秒数
	 * @return void
	 */
	public static void initDbcpDataSource() throws Exception{
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(Constants.DBCP_DRIVERCLASS);
		ds.setUsername(Constants.DBCP_USERNAME);
		ds.setPassword(Constants.DBCP_PASSWORD);
		ds.setUrl(Constants.DBCP_URL);
		ds.setInitialSize(Constants.DBCP_INITIALSIZE); 
		ds.setMaxActive(Constants.DBCP_MAXACTIVE);
		ds.setMaxIdle(Constants.DBCP_MAXIDLE);
		ds.setMaxWait(Constants.DBCP_MAXWAIT);
		DS = ds;
	}
	
	/**
	 * 关闭数据库资源
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public void close(ResultSet rs,Statement st,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(st!=null){
				st.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭数据库资源
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public void close(ResultSet rs,PreparedStatement ps,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public DataSource getDS() {
		return DS;
	}
}
