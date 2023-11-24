FROM adoptopenjdk:11-jre-hotspot
COPY wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

COPY build/libs/sentryc-0.0.1-SNAPSHOT.jar app.jar
CMD ["/usr/wait-for-it.sh", "elasticsearch", "9200", "--", "java", "-jar", "app.jar"]

# Optional: EXPOSE instruction (document ports)
EXPOSE 8080