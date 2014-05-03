FROM tfennelly/java6_dev

# See https://github.com/tfennelly/dockerfiles/blob/master/java6_dev

MAINTAINER Tom Fennelly <tom.fennelly@gmail.com>

# clone the unedifact repo
RUN git clone https://github.com/smooks/unedifact.git /home/smooks-unedifact

WORKDIR /home/smooks-unedifact
