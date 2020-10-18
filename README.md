# ID
António Fernandes 92880
# Archetype definition
# GroupID definition
# ArtifactID definition
# Como criar projecto
mvn archetype:generate -DgroupID=MyWeatherRadar.Weather -DartifactId=Weather -Dversion=1.0-SNAPSHOT
# Como executar a app
~/Documents/IES/Lab1-2/Weather, usar:
mvn exec:java -Dexec.mainClass="weather.WeatherStarter"
# Executar a app com args
mvn exec:java -Dexec.mainClass="weather.WeatherStarter" -Dexec.args="1010500"
# Maven goal definition, Maven goals & sequência
