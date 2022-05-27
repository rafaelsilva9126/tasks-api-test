package br.ce.wcaquino.tasks.apitest;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

	
	 
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void test() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}

	@Test
	public void deveAdicionarTarefaComSuccesso() {
		String date = LocalDate.now().toString();
		System.out.println(java.time.LocalDate.now());
		RestAssured.given()
			//.body("{\"task\": \"Teste via API\",\"dueDate\":" + date +"}")
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2022-06-25\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2010-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
	
}

