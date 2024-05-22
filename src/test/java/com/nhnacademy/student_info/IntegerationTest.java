package com.nhnacademy.student_info;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    void wrongURLReturnErrorPageTestWithCookie() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.COOKIE, "SESSION=1234"); // 인터셉트에 전역으로 로그인체크가 걸쳐있어서 어쩔 수 없이 테스트용으로 쿠키설정함

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/modify/5", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().toString()).contains("Error");
        assertThat(response.getBody().toString()).contains("Validation Error");
        assertThat(response.getBody().toString()).contains("NoResourceFoundException");

        System.out.println(response);
    }


    @Test
    void getLoginShouldReturnViewContainInputId() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/login",
                String.class)).contains("ID");
        ResponseEntity<String> res = this.restTemplate.getForEntity("http://localhost:" + port + "/login",
                String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().toString()).contains("ID");
        System.out.println(res);
    }



    @Test
    void wrongModifyURLAndStudentNotFoundReturnErrorPageTestWithCookie() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.COOKIE, "SESSION=5"); // 인터셉트에 전역으로 로그인체크가 걸쳐있어서 어쩔 수 없이 테스트용으로 쿠키설정함

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/student/modify/5", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().toString()).contains("Error");
        assertThat(response.getBody().toString()).contains("Validation Error");
        assertThat(response.getBody().toString()).contains("StudentNotFoundException");

        System.out.println(response);
    }


    @Test
    void wrongViewURLAndStudentNotFoundReturnErrorPageTestWithCookie() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.COOKIE, "SESSION=5"); // 인터셉트에 전역으로 로그인체크가 걸쳐있어서 어쩔 수 없이 테스트용으로 쿠키설정함

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/student/view/5", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().toString()).contains("Error");
        assertThat(response.getBody().toString()).contains("Validation Error");
        assertThat(response.getBody().toString()).contains("StudentNotFoundException");

        System.out.println(response);
    }

}
