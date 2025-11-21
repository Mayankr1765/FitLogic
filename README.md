🏋️‍♂️ FitLogic – AI-Powered Fitness Query Bot
FitLogic is a lightweight Spring Boot + LangChain4j + Ollama project designed to answer fitness-related questions using a local LLM.
Ask anything about workouts, nutrition, weight gain/loss, supplements, macros, recovery, etc.

🚀 Tech Stack
Component Description: Java 17+, Primary language BootREST API framework LangChain4jLLM orchestration libraryO llama (Local LLM)Local model server (no cloud dependency)Mistral model LLM used to generate answers

📌 Features
✔ Ask any fitness-related question
✔ Runs 100% locally – no API cost
✔ Prompt-engineered for fitness & nutrition domain
✔ Very small & clean codebase (easy to learn from)
Example questions you can ask:
/fitness/ask?query=best workout split for muscle gain
/fitness/ask?query=diet plan to gain weight for vegetarian
/fitness/ask?query=how many calories should I eat to bulk


🛠️ Project Setup
1️⃣ Install & Run Ollama
Download: https://ollama.com/download
Pull the model:
ollama pull mistral

Start the server (if not already running):
ollama serve

2️⃣ Clone Repository
git clone https://github.com/<your-username>/FitLogic.git
cd FitLogic

3️⃣ Run Spring Boot
Using IDE or:
mvn spring-boot:run


🔥 API Usage
Endpoint
GET http://localhost:8080/fitness/ask?query=<your-question>

Example
GET http://localhost:8080/fitness/ask?query=what is the best protein rich vegetarian diet

Example Response
"Include paneer, tofu, eggs (if allowed), lentils, chickpeas, curd, oats, quinoa and peanut butter. Aim for 1.6–2.2 g protein/kg body weight..."


📂 Project Structure
FitLogic
 ├─ controller
 │   └─ FitnessQuery.java
 ├─ service
 │   └─ FitService.java
 ├─ FitLogicApplication.java
 └─ README.md


🧠 Core LLM Prompt
The bot is guided with domain-specific instructions:
Please answer the query as a fitness expert. Provide short, practical & beginner-friendly guidance.


🧪 Future Enhancements


Web Chat UI using React / Angular


Personalized calorie & macro calculator


Workout routine generator based on user profile


Chat memory + history

RAG after report upload



⭐ Support
If FitLogic helped you or inspired learning,
please ⭐ star the repository — it motivates future improvements!

🧍 Author
Mayank Raj
💡 Passionate about Fitness + AI + Spring Boot + LangChain4j

Let me know if you want:
🔹 project banner / logo
🔹 MIT license file
🔹 Architecture diagram
Happy coding & lifting! 💪🔥
