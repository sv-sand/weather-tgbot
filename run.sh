#!/bin/bash

nohup mvn exec:java -Dexec.mainClass="ru.sanddev.weathertgbot.App" > logs/nohup.out &