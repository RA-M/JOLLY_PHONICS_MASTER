package com.jolly.phonics.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jolly.phonics.dto.Course;

@RestController
public class ClassController {

	@GetMapping(path = "/courses")
	public ResponseEntity<List<Course>> getCourses() {
		System.out.println("courses");
	    try {
	        // Your existing code to fetch courses
	    	List<Course> courseList = new ArrayList<Course>();
	    	courseList.add(new Course(1, "Course 1", "Description for Course 1", "https://www.speldsa.org.au/image/cache/catalog/information/Website%20Images/Jolly%20Phonics%20Logo%202020%20-%20orange%20background-1181x709.jpg"));
	    	courseList.add(new Course(2, "Course 2", "Description for Course 2", "https://banner2.cleanpng.com/20180525/gsq/kisspng-english-grammar-comparison-verb-5b08544527ee93.3051470715272725171636.jpg"));
	    	courseList.add(new Course(3, "Course 3", "Description for Course 3", "https://cdn.vectorstock.com/i/1000x1000/60/81/math-font-with-symbol-and-formula-icon-vector-39626081.webp"));
	    	
	        return ResponseEntity.ok(courseList);
	    } catch (Exception e) {
	        // Log the exception for debugging purposes
	        e.printStackTrace();
	        // Return an error response
	        return null;
	        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
}
