package com.bm.coursecatalogservice.controller

import arrow.core.Either
import com.bm.commonlib.dto.BaseResponse
import com.bm.commonlib.dto.CustomErrorMessages
import com.bm.coursecatalogservice.dto.CourseDTO
import com.bm.coursecatalogservice.entity.Course
import com.bm.coursecatalogservice.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/courses")
class CourseController @Autowired constructor(val courseService: CourseService) {

    @PostMapping
    fun saveCourse(@RequestBody courseDTO: CourseDTO): BaseResponse<CourseDTO, String> =
        when(val result = courseService.saveCourse(courseDTO)){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }

    @GetMapping("/{id}")
    fun getCourseById(@PathVariable("id") id: Int): BaseResponse<CourseDTO, String> =
        when(val result = courseService.getCourseById(id)){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }


    @GetMapping("/name/{name}")
    fun getCourseByName(@PathVariable("name") name: String): BaseResponse<CourseDTO, String> =
        when(val result = courseService.getCourseByName(name)){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }

    @GetMapping("/category/{category}")
    fun getAllCoursesInCategory(@PathVariable("category") category: String): BaseResponse<List<CourseDTO>, String> =
        when(val result = courseService.getAllCoursesInCategory(category)){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }

    @GetMapping
    fun getAllCourses(): BaseResponse<List<CourseDTO>, String> =
        when(val result = courseService.getAllCourses()){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }

    @PutMapping("/{id}")
    fun editCourse(@PathVariable("id") courseId: Int, @RequestBody parameters: Map<String, Any>): BaseResponse<CourseDTO, String> =
        when(val result = courseService.editCourse(courseId, parameters)){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): BaseResponse<String, String> =
        when(val result = courseService.deleteCourse(id)){
            is Either.Left -> BaseResponse(status = false, error = result.a)
            is Either.Right -> BaseResponse(response = result.b)
        }
}