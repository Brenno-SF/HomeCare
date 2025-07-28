Readme temporário.
# 🏥 HomeCare API - Sistema de Gestão de Atendimentos Domiciliares

Este projeto é uma API RESTful desenvolvida com **Java Spring Boot** para gestão de pacientes, profissionais da saúde e agendamentos no contexto de **homecare** (atendimento domiciliar).

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA + Hibernate
- MySQL
- Lombok
- Spring DevTools

---

## 🗂️ Estrutura do Banco de Dados

O sistema é modelado de forma normalizada, com foco em flexibilidade e integridade dos dados. As principais tabelas incluem:

### 👤 Usuários (`user_tb`)
Tabela central que representa todos os usuários do sistema (pacientes e profissionais).  
Campos: `name`, `email`, `password`, `birth_date`, `gender`, `type_user` (enum: `PATIENT`, `PROFESSIONAL`), entre outros.

### 🧑‍⚕️ Profissionais (`professional_tb`)
Estende os dados do usuário para profissionais da saúde.  
Relacionamentos:
- `credentials_tb`: credenciais como COREN, CRM, etc.
- `availability_professional_tb`: horários disponíveis para atendimento.

### 🧑‍🦼 Pacientes (`patient_tb`)
Estende os dados do usuário para pacientes.  
Relacionamentos:
- `history_tb`: histórico médico
- `assessment_tb`: avaliações dos profissionais

### 📆 Agendamento (`appointment_tb`)
Relaciona pacientes e profissionais para marcar consultas/atendimentos.  
Campos: `date`, `start_time`, `end_time`, `status`, `obs`.

### 🗓️ Disponibilidade (`availability_professional_tb`)
Horários semanais que o profissional está disponível para agendamentos.  
Campos: `week_day`, `start_time`, `end_time`.

### 🧾 Credenciais (`credentials_tb`)
Representa registros profissionais (COREN, CRM, etc.)  
Campos: `number`, `uf`, `validation_date`, `type`.

### 📞 Contatos & Endereços
- `emails_tb`: múltiplos e-mails por usuário
- `phones_tb`: múltiplos telefones
- `address_tb`: endereços completos

---

## 🔐 Segurança

### Autenticação e Autorização
A API utiliza **JWT (JSON Web Token)** com **Spring Security**:
- Login via `/auth/login`
- Geração de token JWT
- Proteção de rotas por perfil de usuário (`ROLE_PATIENT`, `ROLE_PROFESSIONAL`, `ROLE_ADMIN`)

---

## 🧰 Como rodar localmente

### Pré-requisitos

- Java 17+
- Maven
- MySQL
