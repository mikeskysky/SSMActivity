package com.mike.test;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MyTest 
{
	public static void main(String[] args) 
	{
		MultipartFile[] mulfile=new MockMultipartFile[1];
		String content="<h1>1212test</h1>";
		mulfile[0]=new MockMultipartFile("mytest.html", "mytest.html",null,content.getBytes());
		for (MultipartFile file : mulfile) {
			System.out.println(file.getOriginalFilename());
		}
	}
}
