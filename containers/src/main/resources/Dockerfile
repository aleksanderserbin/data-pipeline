FROM centos:7

RUN mkdir -p /opt/aserbin/datapipeline
RUN yum update -y && \
    yum install -y java-1.8.0-openjdk
COPY deps/api.jar /opt/aserbin/datapipeline/


CMD ["java", "-jar", "/opt/aserbin/datapipeline/api.jar"]