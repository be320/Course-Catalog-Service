package com.bm.coursecatalogservice.repository.impl

import com.bm.coursecatalogservice.entity.Course
import com.bm.coursecatalogservice.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class CourseRepositoryImpl @Autowired constructor(val jdbcTemplate: JdbcTemplate) : CourseRepository {

    override fun saveCourse(course: Course): Course {
        val courseId = SimpleJdbcInsert(jdbcTemplate)
            .withTableName("Courses")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(
                mapOf(
                    "name" to course.name,
                    "category" to course.category
                )
            ).toInt()
        return getCourseById(courseId)!!
    }


    override fun getCourseById(id: Int): Course? {
        return jdbcTemplate.query({ connection ->
            val ps = connection.prepareStatement("SELECT * FROM bm.Courses WHERE id = ?")
            ps.setInt(1, id)
            ps
        }){ rs: ResultSet, _: Int ->
            fillCourse(rs)
        }.firstOrNull()
    }

    override fun getCourseByName(name: String): Course? {
        return jdbcTemplate.query({ connection ->
            val ps = connection.prepareStatement("SELECT * FROM bm.Courses WHERE name = ?")
            ps.setString(1, name)
            ps
        }){ rs: ResultSet, _: Int ->
            fillCourse(rs)
        }.firstOrNull()
    }

    override fun getAllCoursesInCategory(category: String): List<Course> {
        return jdbcTemplate.query({ connection ->
            val ps = connection.prepareStatement("SELECT * FROM bm.Courses WHERE category = ?")
            ps.setString(1, category)
            ps
        }){ rs: ResultSet, _: Int ->
            fillCourse(rs)
        }
    }

    override fun getAllCourses(): List<Course> {
        return jdbcTemplate.query({ connection ->
            val ps = connection.prepareStatement("SELECT * FROM bm.Courses")
            ps
        }){ rs: ResultSet, _: Int ->
            fillCourse(rs)
        }
    }

    override fun editCourse(courseId: Int, parameters: Map<String, Any>) :Course? {
        val numberOfRowsAffected = jdbcTemplate.update(""" 
            UPDATE bm.Courses 
            SET ${parameters.keys.joinToString(" = ?, ", postfix = " = ?")}
            WHERE id = ?
        """.trimIndent(), *parameters.values.toTypedArray(), courseId)
        return if (numberOfRowsAffected == 1) getCourseById(courseId)!! else null
    }

    override fun deleteCourse(id: Int): String? {
        val numberOfRowsAffected = jdbcTemplate.update("DELETE FROM bm.Courses WHERE id = ?", id)
        return if (numberOfRowsAffected == 1) "Deleted Successfully" else null
    }

    private fun fillCourse(rs: ResultSet): Course =
        Course(rs.getInt("id"), rs.getString("name"), rs.getString("category"))
}