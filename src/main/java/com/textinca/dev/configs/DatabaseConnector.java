package com.textinca.dev.configs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
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
    protected static final String TESTDB_PASS = "password";
    protected static final String TESTDB_URL = "jdbc:mysql://domain/dbname?useSSL=false&serverTimezone=America/Costa_Rica";

    @PostConstruct
    public void doConnection()
    {	
    	setDefaultConfigurationValues()
    	.initializeConnection();
    	System.out.println("-Initialize MySQL Connection-");
    }
    
    @PreDestroy
    public void closeConnection()
    {
    	try 
    	{
			this.mySqlConnection.close();
	    	System.out.println("-Close MySQL Connection-");
		} 
    	catch (SQLException error) 
    	{
    		System.out.println("Error while trying closing MySQL Connection: " + error);
		}
    }
    
    public DSLContext getFactory()
    {
    	return this.queryFactory;
    }
    
    private DatabaseConnector setDefaultConfigurationValues()
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
    
    public CallableStatement prepareCall(String sqlStatement)
    {
    	CallableStatement statement;
    	try {
    		statement = this.mySqlConnection
    						.prepareCall(sqlStatement);
		} 
    	catch (SQLException error) 
    	{
    		statement = null;
            System.out.println("An error has occured while you try preparing statement to "+ url + "Error :" + error);
		}
    	return statement;
    }
}
