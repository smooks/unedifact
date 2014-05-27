#!/bin/bash

set -e

ROOT_DIR=$PWD

while getopts d:u:p:g: option
do
    case "${option}"
    in
        d) DEPLOY=${OPTARG};;
        u) USERNM=${OPTARG};;
        p) PASSWD=${OPTARG};;
        g) GPGPPH=${OPTARG};;
    esac
done

export CH_UN="$USERNM"
export CH_PW="$PASSWD"

if [ $DEPLOY = "yes" ]; then
    echo "Deploying is turned ON!"
fi

exec_mvn() {
    if [ -d $1 ]; then
        pushd $1

        if [ $DEPLOY = "yes" ]; then
            echo "Deploying $1"
            mvn clean deploy -Pdeploy -Dgpg.passphrase="$GPGPPH" -Dmaven.test.skip=true --settings $ROOT_DIR/settings_codehaus.xml
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