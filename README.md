# CGI Trial Task

## Setup Instructions

This project is built with Java 21 and uses Gradle as the build tool. 

### Prerequisites

- Java JDK 21

### Building the Project

Navigate to the project's root directory and run:

```
./gradlew build
```

### Running the Project

To start the application, execute:

```
./gradlew bootRun
```

## API Endpoints

### Movie Recommendations

**GET** `/movie/recommend/{userId}`

Retrieves recommended movies for a user based on their viewing history.

- **Path Variable:** `userId` - the ID of the user

### Screening Schedule

**GET** `/screening`

Fetches a paginated list of screenings based on filtering criteria.

- **Query Parameters:** Supports standard pageable parameters page, size, sort. Screening-specific: genre, ageRestriction, startTime, language.

### Seat Recommendations

**GET** `/seat/recommended?screening=x&tickets=y`

Suggests seats for a selected screening and the specified number of tickets.

- **Query Parameters:**
    - `screening` - ID of the screening
    - `tickets` - Number of tickets