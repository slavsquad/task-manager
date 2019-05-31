Start-process -FilePath "java.exe" -ArgumentList "-Dserver.port=7070 -Dserver.host=127.0.0.1 -jar C:\Users\s.stepanenko\IdeaProjects\task-manager\tm-server\release\bin\tm-server-1.0-SNAPSHOT.jar"
Wait-Event -Timeout 10
Start-process -FilePath "java.exe" -ArgumentList "-Dserver.port=8080 -Dserver.host=127.0.0.1 -jar C:\Users\s.stepanenko\IdeaProjects\task-manager\tm-server\release\bin\tm-server-1.0-SNAPSHOT.jar"
Wait-Event -Timeout 10
Start-process -FilePath "java.exe" -ArgumentList "-Dserver.port=9090 -Dserver.host=127.0.0.1 -jar C:\Users\s.stepanenko\IdeaProjects\task-manager\tm-server\release\bin\tm-server-1.0-SNAPSHOT.jar"
