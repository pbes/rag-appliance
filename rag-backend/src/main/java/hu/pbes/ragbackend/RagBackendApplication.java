package hu.pbes.ragbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableConfigurationProperties({
		RagProperties.class
})
public class RagBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagBackendApplication.class, args);
	}

}

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class RagBackendController {

	private final RagAnswerAction ragAnswerAction;

	@PostMapping("/chat")
	public String chat(@RequestBody Message message) {
		return ragAnswerAction.answer(message.message());
	}
}

// TODO singleton RagAnswweringAction & chat model, reconnect if Ollama server is not available, retry, circuit breaker
// TODO Flyway, JPA, PostgreSQL
// TODO DB hozzáadása (PostgreSQL), táblák létrehozása
// TODO user & thread tárolás a frontend oldalról
// TODO frontend küldje át a history-t a backend oldalra
// TODO kérdés - válasz párokat tárolni, reinforcement learning
// TODO HKK dokumentumokat hozzáadni
// TODO dockerben futtatni az egész solution-t

record Message (String message) {}
