# Rhythm Blast [![Build Status](https://travis-ci.org/java-park-mail-ru/Glitchless-09-2017.svg?branch=dev)](https://travis-ci.org/java-park-mail-ru/Glitchless-09-2017) [![codecov](https://codecov.io/gh/java-park-mail-ru/Glitchless-09-2017/branch/master/graph/badge.svg)](https://codecov.io/gh/java-park-mail-ru/Glitchless-09-2017)

Арканоид на стероидах <s>и на котлине</s>.

🎮 Игра: https://glitchless.ru (если отвалилось, то: https://glitchless.surge.sh)

💈 Презентация: https://promo.glitchless.ru

🚀 Выступление: https://youtu.be/vwdLFMBApPI?t=1h37m32s (1:37:32)


## Технологии

- Kotlin, Java
- Spring
- Docker
- Ansible, Docker registry, Travis CI

🛠 Гитхаб с фронтендом: https://github.com/glitchless/RhythmBlastFrontend (или [тп репа](https://github.com/frontend-park-mail-ru/2017_2_glitchless), звездочки ставьте там, хотя, а, лучше везде)

🎻 Гитхаб с девопсом: https://github.com/reo7sp/glitchless-serv


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


## Команда _Glitchless_

- [Михаил Волынов](https://github.com/StealthTech)
- [Никита Куликов](https://github.com/LionZXY)
- [Олег Морозенков](https://github.com/reo7sp)
- [Юрий Голубев](https://github.com/Ansile)
