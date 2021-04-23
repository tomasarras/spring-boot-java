package com.backend.service.impl;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.repository.LogRepository;
import com.backend.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService {
	@Autowired
	private LogRepository logRepository;
	
	@Override
	public void info(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
		log.info(message);
		logRepository.save(com.backend.entity.Log.builder()
				.date(new Timestamp(System.currentTimeMillis()))
				.message(message)
				.className(clazz.getName())
				.build());
	}

}
