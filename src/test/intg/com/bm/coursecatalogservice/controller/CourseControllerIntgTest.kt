package com.bm.coursecatalogservice.controller

import arrow.core.Either
import com.bm.commonlib.dto.BaseResponse
import com.bm.coursecatalogservice.dto.CourseDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@AutoConfigureWebTestClient
class CourseControllerIntgTest @Autowired constructor(private val webTestClient: WebTestClient) {

    @Test
    fun saveCourse(){
        val courseDTO = CourseDTO(null, "Algebra", "Engineering")
        val responseBody : BaseResponse<CourseDTO, Any> = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
//            .expectBody(BaseResponse<CourseDTO, Any>::class.java)
//            .returnResult()
//            .responseBody

        assertThat(responseBody!!.response).isInstanceOf(CourseDTO::class.java)
    }
}