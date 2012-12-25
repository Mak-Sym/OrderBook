call mvn package
IF ERRORLEVEL 1 GOTO end

copy target\orderbook.jar .\orderbook.jar /Y

call java -jar orderbook.jar %1

:end