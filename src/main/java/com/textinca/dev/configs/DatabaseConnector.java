package com.textinca.dev.configs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnector {
	
	protected String userName;
	protected String password;
    protected String url;
    private Connection mySqlConnection;
    protected DSLContext queryFactory;
    protected Settings settings; 
    
    protected static final String TESTDB_USER = "root";
    protected static final String TESTDB_PASS = "textinadmin";
    protected static final String TESTDB_URL = "jdbc:mysql://textin-devtest.cn6t8xcmyxcw.us-east-1.rds.amazonaws.com:3306/devtest?useSSL=false&serverTimezone=America/Costa_Rica";
    
    public DatabaseConnector()
    {
    	setDefaultConfigurationValues()
    	.initializeConnection();
    }
    
    public DatabaseConnector setUserName(String userName)
    {
    	this.userName = userName;
    	return this;
    }
    
    public DatabaseConnector setPassword(String password)
    {
    	this.password = password;
    	return this;
    }
    
    public DatabaseConnector setUrl(String url)
    {
    	this.url = url;
    	return this;
    }
    
    public DSLContext getFactory()
    {
    	return this.queryFactory;
    }
    
    public DatabaseConnector setDefaultConfigurationValues()
    {
    	this.userName = TESTDB_USER;
    	this.password = TESTDB_PASS;
    	this.url = TESTDB_URL;
    	return this;
    }
    
    public void initializeConnection()
    {
        try 
        {
        	this.mySqlConnection = DriverManager.getConnection(url, userName, password);
        	this.settings = new Settings().withStatementType(StatementType.STATIC_STATEMENT);
        	this.queryFactory = DSL.using(this.mySqlConnection, SQLDialect.MYSQL, this.settings);
        } 
        catch (Exception error) 
        {
            error.printStackTrace();
            System.out.println("An error has occured while you try establish the connection with " 
            					+ url + "in \n"
            					+ "ConnectionHandler class from dbhandler package"
            );
        }
    }
    
    @PreDestroy
    public void closeConnection()
    {
    	try 
    	{
			this.mySqlConnection.close();
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    
    public CallableStatement prepareCall(String sqlStatement)
    {
    	CallableStatement statement;
    	try {
    		statement = this.mySqlConnection
    						.prepareCall(sqlStatement);
		} 
    	catch (SQLException e) 
    	{
    		statement = null;
			e.printStackTrace();
		}
    	return statement;
    }
}
