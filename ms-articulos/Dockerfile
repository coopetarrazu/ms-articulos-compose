ARG MS_NAME=ms-articulos
ARG COMMONS=commons-seguridad
ARG MS_VERSION=0.0.1-SNAPSHOT

FROM eclipse-temurin:21-jdk-alpine AS builder
ARG MS_NAME
ARG COMMONS

RUN apk add --no-cache dos2unix tree

WORKDIR /app

# Copiar pom raíz y estructura mínima
COPY ./pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw

# Copiar solo los módulos necesarios
COPY ./$COMMONS ./$COMMONS
COPY ./$MS_NAME ./$MS_NAME

# Preparar mvnw
RUN dos2unix mvnw && chmod +x mvnw

# ⬅️ Paso clave: instalar el pom padre sin compilar módulos
RUN ./mvnw clean install -N -DskipTests

# Compilar e instalar commons
WORKDIR /app/$COMMONS
RUN ../mvnw clean install -DskipTests

# Compilar e instalar commons events
RUN ../mvnw clean install -DskipTests

# Compilar microservicio
WORKDIR /app/$MS_NAME
RUN ../mvnw clean package -DskipTests

# Imagen final
FROM eclipse-temurin:21-jdk-alpine
ARG MS_NAME
ARG MS_VERSION

WORKDIR /app

COPY --from=builder /app/$MS_NAME/target/$MS_NAME-$MS_VERSION.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

