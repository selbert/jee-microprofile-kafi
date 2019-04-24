FROM openliberty/open-liberty:kernel-java11-openj9

COPY server.xml /config/

COPY target/shop.war /config/dropins/