#!/bin/bash

set -e

DEPLOY="no"
if [ "$1" = "-deploy" ]; then
    echo "Deploying is turned ON!"
    DEPLOY="yes"
    shift
fi

exec_mvn() {
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

exec_mvn ./parent

if [ $# -eq 0 ]; then
    set ./d*
fi

for directory;
do
    if [ -d "$directory" ]; then
        exec_mvn $directory
    fi
done

DEPLOY="no"