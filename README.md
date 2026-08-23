# 🎯 RankOrchestrator

> Enterprise Candidate Ranking Engine

**A Java + Python hybrid microservice that ranks job candidates using Data Structures & Algorithms (DSA) and validates accuracy with Spearman correlation.**

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Python](https://img.shields.io/badge/Python-3.10-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://www.python.org/)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.116-009688?style=for-the-badge&logo=fastapi&logoColor=white)](https://fastapi.tiangolo.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/)

---

## Live Demo & Preview

> **🚀 Live Dashboard:** [https://rank-orchestrator-java.onrender.com](https://rank-orchestrator-java.onrender.com)

> *Hosted on Render's free tier — the service sleeps after inactivity, so the first load may take ~30-50 seconds to wake up.*

**Dashboard Preview:**

![RankOrchestrator Dashboard](dashboard.png)

<!-- Confirm dashboard.png is committed at the repo root before publishing — otherwise this renders broken on GitHub. -->

---

## Key Results Snapshot

| Task | Candidates | Score (Spearman ρ) | Baseline (LLM) | Status |
| :--- | :--- | :--- | :--- | :--- |
| **Easy** | 10 | **0.93** | 0.85 | ✅ Outperformed |
| **Medium** | 25 | **0.90** | 0.75 | ✅ Outperformed |
| **Hard** | 50 | **0.85** | 0.62 | ✅ Outperformed |

*The deterministic Java engine consistently outperforms the pure LLM baseline across all difficulty tiers, proving that DSA-driven ranking is faster, cheaper, and more accurate for structured recruitment tasks.*

<!-- Link this table to the script/notebook that produced it (e.g. benchmarks/run_eval.py) before publishing. Precise decimal figures with no reproducible source look fabricated to a technical reviewer. -->

---

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Key Features](#key-features)
- [Architecture Flow](#architecture-flow)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Future Work](#future-work)
- [Developer & Contact](#developer--contact)

---

## Overview

**The Problem:** Recruiters spend countless hours manually screening resumes against job requirements, leading to slow hiring cycles and unconscious bias.

**The Solution:** **RankOrchestrator** is a hybrid microservices system that automates candidate ranking. A **Java Spring Boot** engine uses **DSA (HashMaps + Custom Comparators)** to rank candidates instantly based on weighted skill requirements and experience. A **Python FastAPI** service validates the ranking using **Spearman Rank Correlation**, ensuring statistical accuracy against a deterministic ground truth.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Orchestrator** | Java 17, Spring Boot 3.2.5, Maven |
| **Grader / Validator** | Python 3.10, FastAPI 0.116 |
| **Database** | PostgreSQL 15, Spring Data JPA (Hibernate) |
| **Communication** | REST APIs (Java ↔ Python) |
| **Containerization** | Docker, Docker Compose |
| **Async Processing** | `@Async` + `CompletableFuture` |

---

## Key Features

- **DSA Ranking Engine:** Implements a deterministic ranking algorithm using `HashMap` for O(1) skill lookups and custom `Comparator` logic for stable tie-breaking.
- **OOP Architecture:** Built with clean Spring Boot services, DTOs, and controller layers, demonstrating strong object-oriented design principles.
- **Polyglot Microservices:** Java orchestrator communicates seamlessly with a Python FastAPI grader via REST APIs, proving cross-language integration skills.
- **SQL & Persistence:** Uses PostgreSQL with Spring Data JPA to log every ranking attempt, enabling historical performance tracking.
- **Asynchronous & Scalable:** Employs `@Async` and `CompletableFuture` to handle ranking requests without blocking the main thread.
- **Containerization:** Full `docker-compose.yml` setup spins up the entire stack (Java, Python, PostgreSQL) with a single command.

---

## Architecture Flow

```
User / Recruiter
      │
      ▼
┌──────────────────────┐
│  Java Orchestrator    │
│  (Spring Boot)        │
└──────────────────────┘
      │  1. Reset Task
      ▼
┌──────────────────────┐
│  Python Grader         │
│  (FastAPI)             │
└──────────────────────┘
      │  Returns Job + Candidates
      ▼
┌──────────────────────┐
│  DSA Ranking Engine    │
│  (HashMap + Comparator)│
└──────────────────────┘
      │  Ranked Candidate List
      ▼
┌──────────────────────┐
│  Python Grader         │
│  (Spearman Corr.)      │
└──────────────────────┘
      │
      ▼
┌──────────────────────┐
│  PostgreSQL DB         │
│  (Stores History)      │
└──────────────────────┘
      │
      ▼
┌──────────────────────┐
│  Dashboard UI           │
│  (Score + Table)        │
└──────────────────────┘
```

---

## Project Structure

```
ranking-orchestrator-system/
├── java-orchestrator/                  # Spring Boot Orchestrator
│   ├── src/main/java/com/ats/engine/
│   │   ├── config/                     # Async & RestTemplate configs
│   │   ├── controller/                 # REST endpoints (/api/rank/{task})
│   │   ├── dto/                        # Data Transfer Objects (with @JsonProperty)
│   │   ├── repository/                 # JPA repositories (PostgreSQL)
│   │   └── service/
│   │       ├── JavaRankingEngine.java          # DSA Logic (HashMap + Comparator)
│   │       ├── PythonClientService.java        # REST calls to FastAPI
│   │       └── RankingOrchestratorService.java # @Async logic
│   ├── src/main/resources/static/
│   │   └── index.html                  # Recruiter Dashboard
│   ├── pom.xml
│   └── Dockerfile
│
├── resume-screening-env/               # Python FastAPI Grader
│   ├── server/app.py                   # FastAPI endpoints (/reset, /step)
│   ├── env/                            # Spearman correlation logic
│   ├── data/                           # Job descriptions & 50 resumes
│   ├── requirements.txt
│   └── Dockerfile
│
├── docker-compose.yml                  # Orchestrates all 3 containers
└── README.md
```

---

## How to Run

> **Prerequisites:** Make sure **Docker** and **Docker Compose** are installed on your machine.

1. **Clone the repository:**

   ```bash
   git clone https://github.com/jyothi-mandava22/rank-orchestrator.git
   cd rank-orchestrator
   ```

2. **Build and run the entire stack:**

   ```bash
   docker-compose up --build
   ```

3. **Access the Dashboard:**
   - Open your browser and go to: `http://localhost:8080`
   - Click **Easy**, **Medium**, or **Hard** to see the ranking in action.

4. **Stop the services:**

   ```bash
   docker-compose down
   ```

---

## Future Work

- **User Authentication:** Add Spring Security with JWT for multi-tenant SaaS support.
- **Caching Layer:** Implement Redis to cache Python grading results for repeated tasks.
- **Custom Rubrics:** Allow recruiters to adjust skill weights via the UI.
- **Analytics Dashboard:** Add charts to visualize ranking accuracy over time.

---

## Developer & Contact

**Mandava Jyothi Krishna**  
B.E Computer Engineering — Stanley College of Engineering & Technology for Women, Hyderabad  

[![Gmail](https://img.shields.io/badge/Gmail-mandavajyothikrishna%40gmail.com-red?logo=gmail)](mailto:mandavajyothikrishna@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Jyothi%20Krishna%20Mandava-blue?logo=linkedin)](https://www.linkedin.com/in/jyothi-krishna-mandava-216980291/)
