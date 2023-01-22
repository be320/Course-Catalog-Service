package com.bm.coursecatalogservice.service

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.bm.commonlib.dto.CustomErrorMessages
import com.bm.coursecatalogservice.dto.CourseDTO
import com.bm.coursecatalogservice.entity.Course
import com.bm.coursecatalogservice.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseService @Autowired constructor(val courseRepository: CourseRepository) {

    fun saveCourse(courseDTO: CourseDTO): Either<String, CourseDTO> {
        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category)
        }
        return try {
            val savedCourse = courseRepository.saveCourse(courseEntity)
            Right(
                savedCourse.let {
                    CourseDTO(it.id, it.name, it.category)
                })
        } catch (ex: Exception) {
            println("Error happened in saveCourse : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }

    fun getCourseById(id: Int): Either<String, CourseDTO> {
        return try {
            val fetchedCourse =
                courseRepository.getCourseById(id) ?: return Left(CustomErrorMessages.NOT_FOUND_ERROR_MSG.en)
            Right(
                fetchedCourse.let {
                    CourseDTO(it.id, it.name, it.category)
                })
        } catch (ex: Exception) {
            println("Error happened in getCourseById : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }

    fun getCourseByName(name: String): Either<String, CourseDTO> {
        return try {
            val fetchedCourse =
                courseRepository.getCourseByName(name) ?: return Left(CustomErrorMessages.NOT_FOUND_ERROR_MSG.en)
            Right(
                fetchedCourse.let {
                    CourseDTO(it.id, it.name, it.category)
                })
        } catch (ex: Exception) {
            println("Error happened in getCourseByName : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }

    fun getAllCoursesInCategory(category: String): Either<String, List<CourseDTO>> {
        return try {
            val fetchedCourses =
                courseRepository.getAllCoursesInCategory(category)
            val dtoList = mutableListOf<CourseDTO>()
            fetchedCourses.forEach {
                dtoList.add(it.let { CourseDTO(it.id, it.name, it.category) })
            }
            Right(dtoList)
        } catch (ex: Exception) {
            println("Error happened in getAllCoursesInCategory : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }

    fun getAllCourses(): Either<String, List<CourseDTO>> {
        return try {
            val fetchedCourses =
                courseRepository.getAllCourses()
            val dtoList = mutableListOf<CourseDTO>()
            fetchedCourses.forEach {
                dtoList.add(it.let { CourseDTO(it.id, it.name, it.category) })
            }
            Right(dtoList)
        } catch (ex: Exception) {
            println("Error happened in getAllCourses : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }

    fun editCourse(courseId: Int, parameters: Map<String, Any>): Either<String, CourseDTO> {
        return try {
            val editedCourse =
                courseRepository.editCourse(courseId, parameters) ?: return Left(CustomErrorMessages.NOT_FOUND_ERROR_MSG.en)
            Right(
                editedCourse.let {
                    CourseDTO(it.id, it.name, it.category)
                })
        } catch (ex: Exception) {
            println("Error happened in editCourse : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }

    fun deleteCourse(id: Int): Either<String, String> {
        return try {
            val courseDeleted = courseRepository.deleteCourse(id) ?: return Left(CustomErrorMessages.NOT_FOUND_ERROR_MSG.en)
            Right(courseDeleted)
        } catch (ex: Exception) {
            println("Error happened in deleteCourse : ${ex.message}")
            Left(CustomErrorMessages.DATABASE_ERROR_MSG.en)
        }
    }
}