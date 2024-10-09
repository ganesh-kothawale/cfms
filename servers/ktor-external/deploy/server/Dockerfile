FROM openjdk:11

MAINTAINER mani-k

RUN apt-get update && apt-get install -y \
      vim \
      lsof \
      curl \
      wget \
      htop

# Set timezone
ENV TZ=Asia/Kolkata APP_HOME=/home/app
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
      echo $TZ > /etc/timezone && \
      dpkg-reconfigure --frontend noninteractive tzdata

WORKDIR /home/app

# Set up logs directory
RUN mkdir -p logs
VOLUME ["/home/app/logs"]

ADD . ./

ENTRYPOINT ["bash", "run.sh"]
