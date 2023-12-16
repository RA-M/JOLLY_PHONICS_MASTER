package com.jolly.phonics.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jolly.phonics.dto.Course;

@RestController
public class ClassController {

	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getCourses() {
		System.out.println("courses");
	    try {
	        // Your existing code to fetch courses
	        return ResponseEntity.ok(Arrays.asList(
	            new Course(1, "Course 1", "Description for Course 1", "course1.jpg"),
	            new Course(2, "Course 2", "Description for Course 2", "course2.jpg")
	        ));
	    } catch (Exception e) {
	        // Log the exception for debugging purposes
	        e.printStackTrace();
	        // Return an error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
}
