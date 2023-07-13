package org.crud.reporte;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reporte {
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
		public static void insertarReporte(int id, String nombre, String fecha, String descripcion)throws IOException,SQLException {
			try {
				connectDataBaseOracle();
				String sql = "INSERT INTO REPORTE (ID, NOMBRE, FECHA, DESCRIPCION) VALUES (?,?,?,?)";
				PreparedStatement ps= connection.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setString(2, nombre);
				ps.setString(3, fecha);
				ps.setString(4, descripcion);
				ps.executeQuery();
				System.out.println("INSERTO EL REGISTRO: "+id+", "+nombre+", "+fecha+", "+descripcion);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception: "+e.getMessage());
			}
		}
		
		//actualizar
		public static void updateReporte(String nombre, int id)throws IOException, SQLException {
			try {
				connectDataBaseOracle();
				String sql= "update REPORTE SET NOMBRE = ? WHERE ID = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, nombre);
				ps.setInt(2, id);
				ps.executeQuery();
				System.out.println("ACTUALIZO EL REGIDTRO: "+id+", con el nombre: "+nombre);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("excepcion: "+e.getMessage());
			}
		}
		
		//borrar
		public static void deleteReporte(int id)throws IOException, SQLException {
			try {
				connectDataBaseOracle();
				String sql= "delete from reporte WHERE ID = ?";
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
		public static void selectByIReporte(int id)throws IOException, SQLException {
			try {
				connectDataBaseOracle();
				String sql= "select * from reporte WHERE ID = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rSet = ps.executeQuery();
				
				if(rSet.next()) {
					System.out.println("id:"+rSet.getInt("id")+", "+ "nombre:"+rSet.getString("nombre")+ ", fecha:"+rSet.getString("fecha")+ ", descripcion:"+rSet.getString("descripcion"));
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("excepcion: "+e.getMessage());
			}
		}
		
		//seleccionar varios
		public static void selectAllReporte()throws IOException, SQLException {
			try {
				connectDataBaseOracle();
				String sql= "select * from reporte";
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rSet = ps.executeQuery();
				
				while(rSet.next()) {
					System.out.println("id:"+rSet.getInt("id")+", "+ "nombre:"+rSet.getString("nombre")+ ", fecha:"+rSet.getString("fecha")+ ", descripcion:"+rSet.getString("descripcion"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("excepcion: "+e.getMessage());
			}
		}
		
		//INVOCAR AL PROCEDIMIENTO ALMCENADO LLAMADO PROC
		public static void invocateProcedureReporte(int id, String nombre, String fecha, String comentario)throws IOException, SQLException {
			try {
				connectDataBaseOracle();
				String sql= "CALL procreporte(?,?,?,?)";
				CallableStatement cs = connection.prepareCall(sql);//cambia la sentencia
				cs.setInt(1, id);
				cs.setString(2, nombre);
				cs.setString(3, fecha);
				cs.setString(4, comentario);
				cs.execute();
				System.out.println("EJECUTO CORRECTAMENTE EL PROCEDIMIENTO.");
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("excepcion: "+e.getMessage());
			}
		}
		
		public static void main(String[] args)throws IOException, SQLException {
			//connectDataBaseOracle();//prueba para ver la conexion, ya se usa en el insert, update o delete
			//insertarReporte(3, "reporte 3", "30/06/2023", "De envios");//checar en sql plus
			//updateReporte("Nuevo Reporte", 3);
			//deleteReporte(3);
			//selectByIReporte(2);
			//selectAllReporte();
			invocateProcedureReporte(3, "Por procedimienot", "28/06/2023", "prueba");
		}
}
