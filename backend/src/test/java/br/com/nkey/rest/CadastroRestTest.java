package br.com.nkey.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;

import br.com.nkey.dto.CadastroDTO;
import br.com.nkey.dto.UsuarioLogin;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@MongoDbIntegrationTest
public class CadastroRestTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private HttpHeaders headerToken;
	
	@BeforeEach
	public void setup() throws Exception {
		String token = login("admin@gmail.com", "123mudar");
		headerToken = new HttpHeaders();
		headerToken.set(HttpHeaders.AUTHORIZATION, token);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveSalvarCadastroComSucesso() throws Exception {
		ResultActions request = mockMvc.perform(post("/cadastro")
				.headers(headerToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(serializarJson(new CadastroDTO("jose")))
		);
		request.andExpect(status().isCreated());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveRecuperarPorIdComSucesso() throws Exception {
		mockMvc.perform(get("/cadastro/55f3ed00b1375a48e618300a")
				.headers(headerToken))
        		.andExpect(status().isOk());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveFalharRecuperarPorIdInexistente() throws Exception {
		mockMvc.perform(get("/cadastro/55f3ed00b1375a48e6183xxx")
				.headers(headerToken))
        		.andExpect(status().isNotFound());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveRecuperarTodosCadastros() throws Exception {
		mockMvc.perform(get("/cadastro")
				.headers(headerToken))
        		.andExpect(status().isOk());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveRemoverCadastroPorIdComSucesso() throws Exception {
		mockMvc.perform(delete("/cadastro/55f3ed00b1375a48e618300a")
				.headers(headerToken))
        		.andExpect(status().isNoContent());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveFalharAoTentarRemoverCadastroPorIdInexistente() throws Exception {
		mockMvc.perform(delete("/cadastro/55f3ed00b1375a48e6183xxx")
				.headers(headerToken))
        		.andExpect(status().isNotFound());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveAlterarCadastroComSucesso() throws Exception {
		ResultActions request = mockMvc.perform(put("/cadastro/55f3ed00b1375a48e618300a")
				.headers(headerToken)
				.content(serializarJson(new CadastroDTO("pedro")))
				.contentType(MediaType.APPLICATION_JSON)
        );
		request.andExpect(status().isNoContent());
	}
	
	private String login(String email, String senha) throws Exception {
		UsuarioLogin usuarioLogin = new UsuarioLogin(email, senha);
        return mockMvc.perform(
                post("/login")
                .content(serializarJson(usuarioLogin))
                .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse()
				.getHeader(HttpHeaders.AUTHORIZATION);
    }
    
    private String serializarJson(Object obj) throws IOException {
        return new ObjectMapper().writeValueAsString(obj);
    }
    
}
