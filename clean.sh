#!/bin/sh

set -e

function exec_mvn {
    if [ -d $1 ]; then
        pushd $1
        mvn clean
        popd
    fi
}

function build_set {
    pushd $1

    exec_mvn mapping
    exec_mvn binding
    exec_mvn test

    popd
}


exec_mvn .

for directory in ./*
do
    if [ -d $directory ]; then
        build_set $directory
    fi
done

exec_mvn examples