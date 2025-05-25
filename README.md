# Mangalib Backend

Mangalib Backend — это RESTful API для управления мангой и главами. Он предоставляет функциональность для создания, обновления, удаления и получения информации о манге и связанных с ней главах.

## 🛠️ Стек технологий

- **Язык:** Kotlin
- **Фреймворк:** Spring Boot, Spring Web, Spring Data JPA, Jakarta Validation, Hibernate
- **База данных:** PostgreSQL, H2 (для разработки)

---

## 📖 REST API

### Manga API

| HTTP Метод | Путь                  | Описание                               |
|------------|-----------------------|----------------------------------------|
| `GET`      | `/api/manga`          | Получить список всех манг.             |
| `POST`     | `/api/manga`          | Создать новую мангу.                   |
| `GET`      | `/api/manga/{id}`     | Получить информацию о манге по её ID.  |
| `PUT`      | `/api/manga/{id}`     | Обновить мангу по её ID.               |
| `DELETE`   | `/api/manga/{id}`     | Удалить мангу по её ID.                |
| `DELETE`   | `/api/manga?title=...`| Удалить мангу по её названию.          |

### Chapter API

| HTTP Метод | Путь                                | Описание                               |
|------------|-------------------------------------|----------------------------------------|
| `GET`      | `/api/manga/{mangaId}/chapters`     | Получить список всех глав данной манги.|
| `POST`     | `/api/manga/{mangaId}/chapters`     | Добавить новую главу в указанную мангу.|
| `GET`      | `/api/manga/{mangaId}/chapters/{id}`| Получить информацию о главе по ID.     |
| `PUT`      | `/api/manga/{mangaId}/chapters/{id}`| Обновить данные главы.                 |
| `DELETE`   | `/api/manga/{mangaId}/chapters/{id}`| Удалить главу по ID.                   |
