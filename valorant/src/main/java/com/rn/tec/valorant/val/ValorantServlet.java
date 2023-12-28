package com.rn.tec.valorant.val;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/characters")
public class ValorantServlet extends HttpServlet {

	private static final String VALORANTAPI_URL = "https://valorant-api.com/v1/agents";
	private RestTemplate clienteHttp;

	@Override
	public void init() throws ServletException {
		this.clienteHttp = new RestTemplate();
		log.info("Cliente Http Inicializado");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		log.info("doGet() inicializado");

		try {
			APIValorantResponse respostaAPI = this.clienteHttp.getForObject(VALORANTAPI_URL, APIValorantResponse.class);

			if (respostaAPI == null) {
				log.error("Chamada a API não retornou dados");
				req.setAttribute("erro",
						"Ocorreu um erro ao realizar a chamada a API usando GET, API não retornou dados");
			} else {

				System.out.println(respostaAPI.getData().get(1).getDisplayName());

				log.info("Chamada a API realizada co sucesso");
				req.setAttribute("characters", respostaAPI);
			}

			req.getRequestDispatcher("characters.jsp").forward(req, resp);

		} catch (Exception e) {
			 log.error("Ocorreu um erro ao realizar a chamada a API:" + VALORANTAPI_URL, e);

	            req.setAttribute("erro",
	                    "ocorreu um erro ao realizar a chamada a API através do método GET: " + e.getMessage().replace("\"",
	                            "'"));
		}
		
		
		log.info("doGet() finalizado");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("doPost() inicializado");

		String respostaServlet = null;

		try {

			ResponseEntity<String> respostaAPI = this.clienteHttp.getForEntity(VALORANTAPI_URL, String.class);

			if (respostaAPI.hasBody()) {

				respostaServlet = respostaAPI.getBody();
				log.info("Chamada a API realizada com sucesso");
				resp.setStatus(HttpStatus.OK.value());

			} else {
				log.error("Chamada a API realizada com inconsistência código HTTP:" + respostaAPI.getStatusCodeValue());

				resp.setStatus(respostaAPI.getStatusCodeValue());

				respostaServlet = "{" + " \"erro\": " + respostaAPI.getStatusCodeValue() + ","
						+ " \"mensagem\": \"A API não retornou dados\"" + "}";
			}

		} catch (Exception e) {
			log.error("Ocorreu um erro ao realizar a chamada a API:" + VALORANTAPI_URL, e);

			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

			respostaServlet = "{" + " \"erro\": " + HttpStatus.INTERNAL_SERVER_ERROR.value() + "," + " \"mensagem\": \""
					+ e.getMessage().replace("\"", "'") + "\"" + "}";
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		respostaServlet = respostaServlet.replace("’", "'");
		resp.getOutputStream().print(respostaServlet);

		log.info("doPost() finalizado");
	}

}
