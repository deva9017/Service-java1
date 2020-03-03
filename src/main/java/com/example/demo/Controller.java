package com.example.demo;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@RequestMapping("hello")
public List<link> getlink()
{
	List<link> aliens= new ArrayList();
			link al=new link();
	al.setId(10);
	al.setName("hsk");
	aliens.add(al);
	return aliens;
}
}

