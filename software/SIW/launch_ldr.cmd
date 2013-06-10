set JAVA_HOME=C:\jdk1.6.0_45
:set JAVA_HOME=C:\jdk1.5.0_22
set ANT_HOME=C:\apache-ant-1.8.0
set MAVEN_HOME=C:\apache-maven-3.0.5
set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%MAVEN_HOME%\bin

:ant clean

:ant

ant -Dtarget.env=dev run -propertyfile build.properties

pause