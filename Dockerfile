FROM nubomedia/apps-baseimage:v1

MAINTAINER Nubomedia

ADD nubomedia-magic-mirror/ /tmp/nubomedia-magic-mirror
RUN cd /tmp/nubomedia-magic-mirror && mvn compile

ENTRYPOINT cd /tmp/nubomedia-magic-mirror && mvn exec:java

