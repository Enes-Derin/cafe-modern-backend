# ===========================
# 1) BUILD STAGE
# ===========================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# pom.xml ve kaynak kodları kopyala
COPY pom.xml .
COPY src ./src

# Jar oluştur
RUN mvn clean package -DskipTests


# ===========================
# 2) RUN STAGE
# ===========================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Build aşamasında oluşturulan jar dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

# Upload klasörü oluştur
RUN mkdir -p /app/uploads

# Upload klasörünü dışarı aç
VOLUME ["/app/uploads"]

# Uygulama 8080 portunda çalışacak
EXPOSE 8080

# Çalıştırma komutu
ENTRYPOINT ["java", "-jar", "app.jar"]
