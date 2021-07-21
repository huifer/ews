package com.github.huifer.ews.runner;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.persistence.ParamEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Rt implements ApplicationRunner {
	@Autowired
	private ParamEntityRepo paramEntityRepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<ParamEntity> byUrlId = paramEntityRepo.findByUrlId(1);
		System.out.println();
	}
}
