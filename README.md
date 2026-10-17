# FitZone - Gym Tracker REST API

A Spring Boot REST API for managing gym **members** and their **workout logs**, stored in MongoDB.

**Tech stack:** Java 25, Spring Boot 4.1.1, Spring Data MongoDB, Bean Validation, Maven, MongoDB, Postman.

## Project structure

```
src/main/java/com/fitzone/tracker
├── FitZoneApplication.java
├── controller   MemberController, WorkoutController, HealthController
├── service      MemberService, WorkoutService
├── repository   MemberRepository, WorkoutRepository
├── entity       Member, Workout, MembershipPlan (enum)
├── dto          MemberSummary
└── exception    ResourceNotFoundException, DuplicateResourceException, GlobalExceptionHandler
```

## How to run

1. Start MongoDB (default `mongodb://localhost:27017`).
2. Open the folder in IntelliJ as a Maven project and let it load.
3. Run `FitZoneApplication`. The API starts on **http://localhost:8082**.
4. Open MongoDB Compass and connect to `localhost:27017`. Database `fitzonedb` is created on the first insert.
5. Optional: load demo data with `load("sample-data.js")` in mongosh.
6. In Postman, import `FitZone.postman_collection.json`. Run **Create Member** first, then **Log Workout**. The ids are saved into collection variables automatically.

To change the port or database, edit `src/main/resources/application.properties`.

## Collections

**members**: `id`, `name`, `email` (unique), `age` (14-90), `plan` (BASIC / STANDARD / PREMIUM), `joinDate`, `active`

**workouts**: `id`, `memberId` (reference to a member), `exerciseType`, `durationMinutes` (>= 1), `caloriesBurned` (>= 0), `workoutDate`, `notes`

## Endpoints

| Method | URL | Description |
|---|---|---|
| GET | /health | Check that the API is running |
| POST | /api/members | Create member (201) |
| GET | /api/members | List all members |
| GET | /api/members/{id} | Get one member (404 if missing) |
| PUT | /api/members/{id} | Update member |
| DELETE | /api/members/{id} | Delete member and their workouts (204) |
| GET | /api/members/search?name=xyz | Search by name, case-insensitive |
| GET | /api/members/plan/{plan} | Filter by plan |
| GET | /api/members/{id}/summary | Total workouts, minutes, calories, average |
| POST | /api/workouts | Log workout (member must exist) |
| GET | /api/workouts | List all workouts |
| GET | /api/workouts/{id} | Get one workout |
| PUT | /api/workouts/{id} | Update workout |
| DELETE | /api/workouts/{id} | Delete workout |
| GET | /api/workouts/member/{memberId} | All workouts of a member |
| GET | /api/workouts/type/{exerciseType} | Filter by exercise type |

## Error responses

| Situation | Status |
|---|---|
| Validation fails (blank name, bad email, age out of range) | 400 with per-field messages |
| Invalid JSON or unknown plan name | 400 |
| Member or workout id not found | 404 |
| Duplicate email | 409 |

## Viva notes (short answers)

- **Why layered architecture?** The controller handles HTTP, the service holds business rules, and the repository talks to the database. Each layer can change independently.
- **Why constructor injection instead of `@Autowired` fields?** It makes dependencies explicit and easier to test.
- **How are members and workouts related?** MongoDB has no joins, so a workout stores `memberId`, and the service checks that the member exists before saving.
- **What does `@Valid` do?** It triggers the validation annotations on the entity. Failures are turned into a clean 400 response by `GlobalExceptionHandler`.
- **What does `MongoRepository` give us?** Ready-made CRUD methods, plus derived queries like `findByNameContainingIgnoreCase` from the method name alone.
- **Why does deleting a member also delete workouts?** To avoid orphan workout records pointing to a missing member.
