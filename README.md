


```

    # requires mvn3
    mvn clean compile assembly:single -Dmaven.test.skip=true

    # to run
    java -jar target/simple-service-1-jar-with-dependencies.jar
```




jar assembled using
http://maven.apache.org/components/plugins/maven-assembly-plugin/