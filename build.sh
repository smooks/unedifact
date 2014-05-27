#!/bin/bash

set -e

ROOT_DIR=$PWD

DEPLOY="no"
STRTAT=""

while getopts d:u:p:g:s: option
do
    case "${option}"
    in
        d) DEPLOY=${OPTARG};;
        u) USERNM=${OPTARG};;
        p) PASSWD=${OPTARG};;
        g) GPGPPH=${OPTARG};;
        s) STRTAT=${OPTARG};;
    esac
done

export CH_UN="$USERNM"
export CH_PW="$PASSWD"

if [ $DEPLOY = "yes" ]; then
    echo "Deploying is turned ON!"
fi

exec_mvn() {
    if [ -d $1 ]; then
        if [ "$STRTAT" != "" ] && [ "./$STRTAT" != "$1" ]; then
            echo "Skipping $1"
            return 0
        fi

        STRTAT=""

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

# Re-add directory list as a parameter
set ./d*

for directory;
do
    if [ -d "$directory" ]; then
        exec_mvn $directory
    fi
done

DEPLOY="no"