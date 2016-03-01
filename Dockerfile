FROM nubomedia/apps-baseimage:v1

MAINTAINER Nubomedia

ADD nubomedia-magic-mirror .

ENTRYPOINT cd nubomedia-magic-mirror && mvn compile exec:java
