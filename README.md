# Инструкция по запуску лабораторных работ

## Лабораторная работа №1

```
cd "H:\3 курс\Java\Java-Lab-1"
```

```
mkdir bin
```

```
"C:\Program Files\Java\jdk1.8.0_211\bin\javac.exe" -sourcepath ./src -d bin -encoding utf8 src/com/example/Main.java
```

```
java -classpath ./bin com.example.Main
```

```
mkdir doc
```

```
"C:\Program Files\Java\jdk1.8.0_211\bin\javadoc.exe" -d doc -charset utf-8  -sourcepath src -author -subpackages com.example
```

