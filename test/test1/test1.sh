#!/bin/sh

export f=`pwd`

cd ../..

echo 'LADINO
LADINO' | java -Djava.security.auth.login.config=classes/jaas.config -classpath lib/AXgen.jar:lib/aopalliance.jar:lib/axgen-example-entities-abstract-src.jar:lib/axgen-example-entities-abstract.jar:lib/cadsr-api.jar:lib/cglib-full-2.0.1.jar:lib/commons-collections-2.0.jar:lib/commons-collections-2.1.jar:lib/commons-dbcp-1.1.jar:lib/commons-lang-1.0-mod.jar:lib/commons-logging.jar:lib/commons-pool-1.1.jar:lib/dom4j-1.4.jar:lib/ehcache-0.7.jar:lib/freemarker-2.3pre16.jar:lib/hibernate2.jar:lib/jakarta-oro-2.0.7.jar:lib/jaxen-1.0-FCS-full.jar:lib/jdom-1.0b8.jar:lib/jmi.jar:lib/jmiutils.jar:lib/jta.jar:lib/log4j-1.2.8.jar:lib/logkit-1.0.jar:lib/cabio-mdr.jar:lib/mdrapi.jar:lib/mof.jar:lib/nbmdr.jar:lib/odmg-3.0.jar:lib/ojdbc14.jar:lib/openide-util.jar:lib/saxpath-1.0-FCS.jar:lib/spring-core.jar:lib/spring-orm.jar:lib/spring.jar:lib/uml-1.3.jar:lib/velocity.jar:lib/xercesImpl-2.4.0.jar:lib/xml-apis-2.0.2.jar:classes/ gov.nih.nci.ncicb.cadsr.loader.UMLLoader ./test/test1/data Gene-Test-1 1 

cd $f/sql

sqlplus sbrext/jjuser@cbdev @suite.sql

diff test.out golden.out
