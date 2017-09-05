package com.insigma.mvc.serviceimp.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.mvc.dao.log.LogMapper;
import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.log.LogService;

/**
 *
 * @author wengsh
 *
 */

@Service
public class LogServiceImpl implements LogService {

	
	@Resource
	private LogMapper logMapper;
	

	@Override
	@Transactional
	public String saveLogInfo(SLog slog){
		logMapper.saveLogInfo(slog);
		return slog.getLogid();
	}

	
}