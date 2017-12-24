# Rhythm Blast [![Build Status](https://travis-ci.org/java-park-mail-ru/Glitchless-09-2017.svg?branch=dev)](https://travis-ci.org/java-park-mail-ru/Glitchless-09-2017) [![codecov](https://codecov.io/gh/java-park-mail-ru/Glitchless-09-2017/branch/master/graph/badge.svg)](https://codecov.io/gh/java-park-mail-ru/Glitchless-09-2017)

Круговой арканоид <s>на котлине</s>, которого мы заслуживаем.

Игра доступна по адресу: https://glitchless.ru

Бекенд: https://glitchless.ru/api

Ссылка на legacy-репозиторий в Технопарке: https://github.com/java-park-mail-ru/Glitchless-09-2017/tree/dev


## Технологии

- Kotlin, Java
- Spring
- Docker
- Ansible, Docker registry, Travis CI

Ссылка на актуальный репозиторий с Frontend: https://github.com/glitchless/RhythmBlastFrontend


## Инструкции по запуску

```sh
docker build -t backend .

docker run \
    --name db \
    -d \
    -e POSTGRES_USER=backend -e POSTGRES_PASSWORD=qwerty -e POSTGRES_DB=glitchlessdb \
    postgres

docker run \
    --name backend \
    -d \
    -p 8081:80 \
    -e DATABASE_USER=backend -e DATABASE_PASSWORD=qwerty \
    -e DATABASE_URL=jdbc:postgresql://db/glitchlessdb \
    --link db \
    backend 

# будет доступен на http://localhost:8081
```

## Инструкция по запуску тестов

```sh
docker-compose -f docker-compose-test.yml up --abort-on-container-exit
```

## Команда Glitchless

- [Михаил Волынов](https://github.com/StealthTech)
- [Никита Куликов](https://github.com/LionZXY)
- [Олег Морозенков](https://github.com/reo7sp)
- [Юрий Голубев](https://github.com/Ansile)
