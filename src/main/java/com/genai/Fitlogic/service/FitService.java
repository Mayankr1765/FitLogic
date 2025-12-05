package com.genai.Fitlogic.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentByWordSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FitService {

    private ChatModel model;

    private EmbeddingStore<TextSegment> embeddingStore;

    private OllamaEmbeddingModel ollamaEmbeddingModel;

    private final DocumentSplitter splitter = new DocumentByWordSplitter(200,100);




    public FitService(){
        this.model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("mistral")
                .temperature(0.5)
                .build();

        this.embeddingStore = new InMemoryEmbeddingStore<>();
        this.ollamaEmbeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("mistral")
                .build();
    }

    public String getAnswer(String query) {
        String finalPrompt = """
            You are a certified fitness coach & nutrition specialist.
            Keep answers beginner-friendly and practical.

            User Question: %s
            """.formatted(query);
        try {
            String response = this.model.chat(finalPrompt);

            System.out.println("Response is " + response);

            return response;
        }
        catch (Exception e){
            System.out.println("exception is " + e);
            return new String("Exception" + e);
        }
    }

    public String getAnswerRAG(String query){
        try {
            Embedding queryEmbeeding = ollamaEmbeddingModel.embed(query).content();
            EmbeddingSearchRequest embeddingSearchRequest = new EmbeddingSearchRequest(queryEmbeeding, 5, 0.0, null);

            List<EmbeddingMatch<TextSegment>> matches =
                    (List<EmbeddingMatch<TextSegment>>) embeddingStore.search(embeddingSearchRequest);

            StringBuilder contextBuilder = new StringBuilder();
            for (EmbeddingMatch<TextSegment> match : matches) {
                contextBuilder.append(match.embedded().text()).append("\n");
            }


            String context = contextBuilder.toString();

            // Step 4: RAG Prompt
            String finalPrompt = """
            You are a certified fitness coach & nutrition specialist.
            You have access to user's blood report in the context.

            CONTEXT FROM BLOOD REPORT:
            %s

            USER QUESTION:
            %s

            Answer only from the report.
            If information does not exist, reply "Not found in report".
            Give fitness and diet recommendation based on found report values.
            """.formatted(context, query);

            // Step 5: Call LLM
            return model.chat(finalPrompt);


        }
        catch (Exception e ){
            System.out.println("Error occured");
        }
        return "";
    }

    public void ingestReport(MultipartFile multipartFile){
        try {
            PDDocument document = PDDocument.load(multipartFile.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            List<TextSegment> segments = splitter.split(Document.from(text));

            for (TextSegment segment : segments) {
                Embedding embedding = ollamaEmbeddingModel.embed(segment).content();
                embeddingStore.add(embedding, segment);
            }

            System.out.println("PDF Ingest Completed: chunks = " + segments.size());
        }
        catch (Exception e) {
            System.out.println("Ingest Error: " + e);
        }

    }
}
