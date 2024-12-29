# RAG solution

## Install dependencies

```bash
cd rag-frontend
# if not present: python3 -m venv virtualenv
source ./virtualenv/bin/activate

pip install -r requirements.txt
```

## Run the application

### Frontend

```bash
cd rag-frontend
cp .env.example .env

# update .env with the correct backend URL

chainlit run app.py -w
```

### Backend

```bash
cd rag-backend
mvn spring-boot:run
```
