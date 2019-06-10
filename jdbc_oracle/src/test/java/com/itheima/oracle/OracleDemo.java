package com.itheima.oracle;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;
import org.junit.Test;

import java.sql.*;

public class OracleDemo {

    @Test
    public void test1() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.153.10:1521:orcl", "itheima", "itheima");
        PreparedStatement statement = conn.prepareStatement("select * from emp where empno=?");
        statement.setObject(1, 7788);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String ename = rs.getString("ename");
            System.out.println(ename);
        }

        rs.close();
        statement.close();
        conn.close();
    }

    //java调用存储过程
    //CallableStatement     {?= call <procedure-name>[(<arg1>,<arg2>, ...)]} 存储函数
    //   {call <procedure-name>[(<arg1>,<arg2>, ...)]}                        存储过程
    @Test
    public void test2() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.153.10:1521:orcl", "itheima", "itheima");
        CallableStatement statement = conn.prepareCall("{call p_yearsal(?,?)}");
        statement.setObject(1, 7788);
        statement.registerOutParameter(2, OracleTypes.NUMBER);
        statement.execute();

        System.out.println(statement.getObject(2));


        statement.close();
        conn.close();
    }

    //java调用存储函数
    //CallableStatement     {?= call <procedure-name>[(<arg1>,<arg2>, ...)]} 存储函数
    //   {call <procedure-name>[(<arg1>,<arg2>, ...)]}                        存储过程
    @Test
    public void test3() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.153.10:1521:orcl", "itheima", "itheima");
        CallableStatement statement = conn.prepareCall("{?=call f_yearsal(?)}");
        statement.setObject(2, 7788);
        statement.registerOutParameter(1, OracleTypes.NUMBER);
        statement.execute();

        System.out.println(statement.getObject(1));


        statement.close();
        conn.close();
    }
}
