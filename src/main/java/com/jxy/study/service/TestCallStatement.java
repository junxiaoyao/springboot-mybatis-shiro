package com.jxy.study.service;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.Calendar;

/**
 * @description
 * @author: jxy
 * @create: 2019-09-16 11:21
 */
public class TestCallStatement {

    public final static String URL =
        "jdbc:mysql://118.24.188.79:3306/springbootshiro?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";

    public static void main(String[] args) throws Exception{
        new TestCallStatement().testCall();
    }
    public void testCall() throws Exception{
//        MySqlStatementParser parser = new MySqlStatementParser("");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection(URL,"root","123456");
        //创建Oracle存储过程的对象，调用存储过程
       // CallableStatement c=connection.prepareCall("{call addUser2('651651','cas','?')}");
        CallableStatement c=connection.prepareCall("{call addUser2(?,?,?)}");
//        //一次给存储过程传递参数
        c.setString(1,"999");
        c.setString(2,"javaCall");
        c.setInt(3,Types.INTEGER);
      //  c.setInt(0, Types.INTEGER);
        c.execute();
       int count= c.getInt(3);
    }
}
