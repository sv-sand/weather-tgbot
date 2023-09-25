#!/bin/bash

mvn clean
git pull
mvn compile

nohup mvn exec:java -Dexec.mainClass="ru.sanddev.weathertgbot.App"