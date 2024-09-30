FROM 978222968597.dkr.ecr.ap-southeast-1.amazonaws.com/openjdk:11

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
