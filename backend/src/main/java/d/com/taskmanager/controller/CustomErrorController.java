package d.com.taskmanager.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping(value = "/error")
	public String handleError(HttpServletRequest request, HttpServletResponse response) {
		// ステータスコード取得
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			// ステータスコードが404のとき
			if (statusCode == HttpServletResponse.SC_NOT_FOUND) {
				response.setStatus(HttpServletResponse.SC_OK);
				return "/index.html";
			}
		}

		return "error";
	}
}
