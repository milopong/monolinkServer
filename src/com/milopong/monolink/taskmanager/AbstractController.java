package com.milopong.monolink.taskmanager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;


@Resource(name="abstractController")
public class AbstractController {


	@Autowired
	TaskExecutorExample taskexecutorExample;
	
	@PostConstruct
	public void postCon() {
		System.out.println("PostConstruct");
		taskexecutorExample.printMessages();
	}

	@PreDestroy
	public void preDes() {
		System.out.println("PreDestroy");
	}

}