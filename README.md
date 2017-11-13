# LastCraft Core
Проект состоит из 4 частей:
* **CoreAPI** - API для взаимодействия
* **CoreCLI** - приложение для управления серверами для командной строки
* **CoreBukkit** - плагин для Spigot/Bukkit
* **CoreBungee** - плагин для BungeeCord
## CoreAPI
Содержит все необходимые общие для всех проектов элементы.
Отсюда и задается поведение пакетов, пришедших на один из серверов (__CLI, Bukkit, Proxy__).
### Хочу потрогать API
Не трогай, рано. Потрогай CLI.
## CoreCLI
Для запуска используйте
```bash
java -cp CoreCLI.jar;lib/* org.kvlt.core.CoreCLI
```
А проще всего запустить скрипт из директории Compiled
### Хочу подергать API (CoreCLI)
```java
// Онлайн-игрок
OnlinePlayer onlinePlayer = CoreServer().get().getOnlinePlayers().get("Steve");

// Любой игрок
ServerPlayer serverPlayer = PlayerDB.loadServerPlayer(name);

// Получить все игровые серверы
GameServer gameServer = CoreServer().get().getGameServers();

// Получить данных из секции конфига
String bukkitSectionValue = Config.getServer("key");

```
#### Что сделано:
* Отправка пакетов между серверами
* Соответственно их получение и обработка
* Базовые пакеты: подключение/отключение сервера/прокси/игрока, бродкаст сообщений, отправка motd
* Конфигурация главного сервера
* Контейнеры и адаптеры для хранения прокси, игровых серверов и игроков
* Базовые интерфейсы игроков
* Обработка команд с CoreCLI
* Базовая работа с БД MySQL
* Основа работы с сетью (нуждается в доработке)
* Обработка некоторых событий игроков и отправка соотв. пакетов
* Отправка личных сообщений между игроками
* Модели таблиц базы данных и маппинг данных MySQL --> POJO!
* Получение информации об игроке (Пример: /whois <name>)
#### TODO
* Бизнес-логика
* Новые пакеты
* Взаимодействие с игроками, регистрация, лицензия и тд и тп
* Очень много всего
#### Зависимости (Maven):
```xml
<!-- https://mvnrepository.com/artifact/io.netty/netty-all -->
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-all</artifactId>
    <version>4.1.16.Final</version>
</dependency>

<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>6.0.6</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.sql2o/sql2o -->
<dependency>
    <groupId>org.sql2o</groupId>
    <artifactId>sql2o</artifactId>
    <version>1.6.0-RC3</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.codehaus.groovy/groovy-all -->
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.4.11</version>
</dependency>
```
Используйте Maven только для загрузки зависимостей, для компиляции он не подходит из-за циклических зависимостей.
## CoreBukkit
Работает, гоняет пакеты, хорошо всё там. Трогать лишний раз не надо, только пакеты отсылать, да события обрабатывать.
Отрефакторить надо бы.
## CoreBungee
Что-то делает. Работает и ладно.