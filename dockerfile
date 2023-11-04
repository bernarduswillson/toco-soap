FROM mysql/mysql-server:8.0.23
COPY db/toco_soap.sql /docker-entrypoint-initdb.d/

