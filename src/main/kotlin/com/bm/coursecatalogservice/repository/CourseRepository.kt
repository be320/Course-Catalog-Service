package com.bm.coursecatalogservice.repository

import com.bm.coursecatalogservice.entity.Course

interface CourseRepository {

    fun saveCourse(course: Course): Course

    fun getCourseById(id: Int): Course?

    fun getCourseByName(name: String): Course?

    fun getAllCoursesInCategory(category: String): List<Course>

    fun getAllCourses(): List<Course>

    fun editCourse(courseId: Int, parameters: Map<String, Any>): Course?

    fun deleteCourse(id: Int): String?

}