package med.volli.api.controller;

import med.volli.api.domain.consulta.AgendaConsultas;
import med.volli.api.domain.consulta.DadosAgendamentoConsulta;
import med.volli.api.domain.consulta.DadosDetalhamentoConsulta;
import med.volli.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosConsultaJson;


    @MockBean
    private AgendaConsultas agendaConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informações estão inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        // Given | Arrange

        // When | Act
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        // Then | Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão válidas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        // Given | Arrange
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosAgendamentoConsulta =  new DadosAgendamentoConsulta(2l, 5l, data, especialidade);
        var requestBody = dadosAgendamentoJson
                .write(dadosAgendamentoConsulta)
                .getJson();

        var responseBody = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

        when(agendaConsultas.agendar(any()))
                .thenReturn(responseBody);

        // When | Act
        var response = mvc
                .perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody )
                )
                .andReturn().getResponse();

        var jsonEsperado = dadosConsultaJson.write(responseBody).getJson();


        // Then | Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertArrayEquals(jsonEsperado.getBytes(), response.getContentAsByteArray());
    }
}