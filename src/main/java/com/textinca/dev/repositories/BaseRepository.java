package com.textinca.dev.repositories;

import com.textinca.dev.configs.DatabaseConnector;

import java.util.List;

public interface BaseRepository<M, K> {
	
	public static final String CAMPAIGN_MESSAGE = "CampaignMessage";
	public static final String COMPANIES = "Companies";
	public static final String MESSAGE_EVENT_LOG = "MessageEventLog";
	
	public DatabaseConnector dbConnector = new DatabaseConnector();

	public abstract int insert(M model);
	public abstract int update(M model);
	public abstract List<M> selectAll();
	public abstract int delete(M model);
	public abstract M findByKey(K primaryKey);

}