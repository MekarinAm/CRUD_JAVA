package org.crud.sregion;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * CRUD:CREATE, READ, UPDATE AND DELETE
 * */
public class S_Region {
	//CONEXION CON LA BASE DE DATOS
	@SuppressWarnings("unused")
	private static Connection connection = null;
	private static String driver = "oracle.jdbc.driver.OracleDriver";//sale del paquete de jdbc--oracle.jdbc.driver--OracleDriver.class(copy qualified name)
	private static String url = "jdbc:oracle:thin:@localhost:1521:ORCL";//sale de la perspectiva de database en las propiedades de la conexion de Oracle11g
	
	public static void connectDataBaseOracle()throws IOException, SQLException {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Cargo driver corectamente: ojdbc6.jar");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: "+e.getMessage());
		}
		
		try {
			connection=DriverManager.getConnection(url,"System","vw501577");
			System.out.println("CONEXION EXITOSA: ORACLE11G");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION: "+e.getMessage());
		}
	}
	
	//INSERTAR
	public static void insertarS_Region(int id, String name)throws IOException,SQLException {
		try {
			connectDataBaseOracle();
			String sql = "INSERT INTO S_REGION (ID, NAME) VALUES (?,?)";
			PreparedStatement ps= connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.executeQuery();
			System.out.println("INSERTO EL REGISTRO: "+id+", "+name);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
	//actualizar
	public static void updateS_Region(String name, int id)throws IOException, SQLException {
		try {
			connectDataBaseOracle();
			String sql= "update S_REGION SET NAME = ? WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, id);
			ps.executeQuery();
			System.out.println("ACTUALIZO EL REGIDTRO: "+id+", "+name);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("excepcion: "+e.getMessage());
		}
	}
	
	//borrar
	public static void deleteS_Region(int id)throws IOException, SQLException {
		try {
			connectDataBaseOracle();
			String sql= "delete from S_REGION WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();
			System.out.println("ELIMINO EL REGISTRO: "+id);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("excepcion: "+e.getMessage());
		}
	}
	
	//selecionar por id(uno)
	public static void selectByIdS_Region(int id)throws IOException, SQLException {
		try {
			connectDataBaseOracle();
			String sql= "select * from S_REGION WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rSet = ps.executeQuery();
			
			if(rSet.next()) {
				System.out.println("id:"+rSet.getInt("id")+", "+ "nombre:"+rSet.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("excepcion: "+e.getMessage());
		}
	}
	
	//seleccionar varios
	public static void selectAllS_Region()throws IOException, SQLException {
		try {
			connectDataBaseOracle();
			String sql= "select * from S_REGION";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rSet = ps.executeQuery();
			
			while(rSet.next()) {
				System.out.println(rSet.getInt("id")+ ", "+rSet.getString("name"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("excepcion: "+e.getMessage());
		}
	}
	
	//INVOCAR AL PROCEDIMIENTO ALMCENADO LLAMADO PROC
	public static void invocateProcedureS_Region(int id, String name)throws IOException, SQLException {
		try {
			connectDataBaseOracle();
			String sql= "CALL proc(?,?)";
			CallableStatement cs = connection.prepareCall(sql);//cambia la sentencia
			cs.setInt(1, id);
			cs.setString(2, name);
			cs.execute();
			System.out.println("EJECUTO CORRECTAMENTE EL PROCEDIMIENTO.");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("excepcion: "+e.getMessage());
		}
	}
	
	public static void main(String[] args)throws IOException, SQLException {
		//connectDataBaseOracle();--prueba para ver la conexion, ya se usa en el insert, update o delete
		//insertarS_Region(4, "Puebla");//checar en sql plus
		//updateS_Region("Oaxaca",4);
		//deleteS_Region(4);
		//selectByIdS_Region(3);
		//selectAllS_Region();
		invocateProcedureS_Region(4, "PUEBLA");
	}
	
	
	
	
}
