
# AdsPlatform

Бэкенд-часть платформы для перепродажи вещей с авторизацией и CRUD-операциями

## 🚀 Возможности
- Регистрация и авторизация пользователей с ролями (USER/ADMIN)
- Создание, редактирование и удаление объявлений
- Комментирование объявлений
- Загрузка и отображение изображений объявлений и аватарок
- Разграничение прав доступа (пользователи могут редактировать только свои объявления)

## 🛠 Технологии
- **Языки:** < Java >
- **Фреймворки:** <Spring Boot/Security/Data JPA>
- **Базы данных:** < PostgreSQL >
- **Инструменты:** <Liquibase/Swagger/Lombok/MapStruct>

## 📦 Установка
1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/Alla-java/ADS-online.git
   ```

2. Установить зависимости:
   Убедитесь, что установлены:
   - JDK 17+ 
   - Apache Maven 3.6+
   - PostgreSQL 12+
     
   Все зависимости указаны в pom.xml и автоматически устанавливаются при:
   ```bash
   mvn clean install
   ```

3. Базы данных
   
   Приложение работает с PostgreSQL:
   
### Настройка БД
- **Управление**: Liquibase
- **Миграции**: `/src/main/resources/db/changelog/`
  > 💡 Все изменения БД должны проводиться через миграции Liquibase!
- **Конфигурация**:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/adsDb
spring.datasource.username=user_db_name
spring.datasource.password=user_db_password
spring.jpa.hibernate.ddl-auto=validate
```

## 🚀 Запуск приложения

Для запуска приложения:
```bash
mvn spring-boot:run
```

Для запуска фронтенда (требуется Docker):
```bash
docker run -p 3000:3000 --rm ghcr.io/dmitry-bizin/front-react-avito:v1.21
```

## 📚 Документация API

После запуска доступны:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`

## 🧪 Тестирование

Проект содержит:
- Unit-тесты сервисов и мапперов
- Интеграционные тесты контроллеров
- Тесты безопасности

Для запуска:
```bash
mvn test
```

## 📂 Структура проекта
```
src/
├── main/
│   ├── java/
│   │   ├── config/       # Конфигурации Spring
│   │   ├── controller/   # REST контроллеры
│   │   ├── dto/          # Data Transfer Objects
│   │   ├── model/        # Сущности БД
│   │   ├── repository/   # Репозитории Spring Data
│   │   ├── service/      # Бизнес-логика
│   │   └── mapper/       # Мапперы DTO <-> Entity
│   └── resources/
│       ├── db/changelog/ # Миграции Liquibase
│       └── application.properties
└── test/                 # Тесты
```

## 👥 Команда разработчиков
- **Кирилл Яковлев** – Backend Developer (Spring Boot, ORM, Maven, PostgreeSQL)
- **Алла Зацепина** – Backend Developer (Spring Boot, ORM, Maven, PostgreeSQL)
- **Игорь Канашник** – Backend Developer (Spring Boot, ORM, Maven, PostgreeSQL)
- **Дмитрий Гаврилин** – Backend Developer (Spring Boot, ORM, Maven, PostgreeSQL)
