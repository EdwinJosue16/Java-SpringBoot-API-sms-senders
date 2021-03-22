package com.textinca.dev.utilities;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ResponseBuilder <T>
{	
	public T content;
	public HttpStatus code;
	
	public ResponseEntity<T> buildResponse()
	{
		return ResponseEntity.status(this.code).body(this.content);
	}
	
	public ResponseEntity<List<T>> buildListContentResponse(List<T> list)
	{
		ResponseEntity< List<T> > response;
		if(!list.isEmpty())
		{
			response = ResponseEntity.ok(list);
		}
		else 
		{
			response = ResponseEntity.noContent().build();
		}
		return response;
	}
}