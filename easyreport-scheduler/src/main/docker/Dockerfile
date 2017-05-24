FROM azul/zulu-openjdk:8
MAINTAINER dengzhiwei <xianrendzw@easytoolsoft.com>, https://github.com/xianrendzw/EasyReport.git

VOLUME /tmp

# Update Ubuntu
RUN \
  bash -c 'apt-get -qq update && apt-get -y upgrade && apt-get -y autoclean && apt-get -y autoremove' && \
  bash -c 'DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget tar'

ENV USER_NAME easyreport
ENV APP_HOME /opt/poc-api/$USER_NAME

RUN \
  useradd -ms /bin/bash $USER_NAME && \
  mkdir -p $APP_HOME

ADD easyreport.jar ${APP_HOME}/easyreport.jar
RUN \
  chown $USER_NAME $APP_HOME/easyreport.jar && \
  bash -c 'touch ${APP_HOME}/easyreport.jar'

ENV JAVA_TOOL_OPTIONS "-Xms128M -Xmx128M -Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"

USER $USER_NAME
WORKDIR $APP_HOME
ENTRYPOINT ["java", "-jar", "easyreport.jar"]

# Run as:
# docker run -idt -p 8701:8701 -e appPort=8701 easytoolsoft/easyreport:latest
