#!/bin/sh

set -e

DEPLOY="no"
if [ $1 = "-deploy" ]; then
    echo "Deploying is turned ON!"
    DEPLOY="yes"
fi


function exec_mvn {
    if [ -d $1 ]; then
        pushd $1

        if [ $DEPLOY = "yes" ]; then
            echo "Deploying $1"
            mvn clean deploy
        else
            echo "Installing $1"
            mvn clean install
        fi

        popd
    fi
}

function build_set {
    pushd $1

    exec_mvn mapping
    exec_mvn binding

    if [ $DEPLOY = "no" ]; then
        exec_mvn test
    fi

    popd
}


exec_mvn .

for directory in ./*
do
    if [ -d $directory ]; then
        build_set $directory
    fi
done

DEPLOY="no"
exec_mvn examples