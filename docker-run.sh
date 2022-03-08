mvn clean install -DskipTests -Pprod && docker build -t muriloalvesdev/serviceplayer . && docker-compose up
