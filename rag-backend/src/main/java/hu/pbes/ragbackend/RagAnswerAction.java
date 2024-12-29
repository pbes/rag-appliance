package hu.pbes.ragbackend;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RagAnswerAction {

    private final RagProperties ragProperties;

    public String answer(String message) {
        List<Document> documents = loadDocuments(ragProperties.getDocumentPath());

        ChatLanguageModel chatModel = OllamaChatModel.builder()
                .baseUrl(ragProperties.getOllamaUrl())
                .modelName(ragProperties.getOllamaModel())
                .build();

        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();

        return assistant.chat(message);
    }

    private List<Document> loadDocuments(String documentPath) {
        if (Files.isDirectory(Path.of(documentPath))) {
            return FileSystemDocumentLoader.loadDocuments(documentPath);
        } else {
            return List.of(FileSystemDocumentLoader.loadDocument(documentPath));
        }
    }

}
