#!/bin/bash
#Credits http://pebblesinthesand.wordpress.com/2008/05/22/a-srcipt-for-running-processes-in-parallel-in-bash/

NUM=0
QUEUE=""
MAX_NPROC=2 # default
USAGE="A simple wrapper building edifact in parallel.
Usage: `basename $0` [-h] [-d] [-j nb_jobs] command arg_list
    -h      Shows this help
    -d      Deploy artifacts 
    -j nb_jobs  Set number of simultanious jobs [2]
 Examples:
    `basename $0` d96a d13b - build only d96a and d13b
    `basename $0` -j 3 -d - build and deploy all"
 
function queue {
    QUEUE="$QUEUE $1"
    NUM=$(($NUM+1))
}
 
function regeneratequeue {
    OLDREQUEUE=$QUEUE
    QUEUE=""
    NUM=0
    for PID in $OLDREQUEUE
    do
        if [ -d /proc/$PID  ] ; then
            QUEUE="$QUEUE $PID"
            NUM=$(($NUM+1))
        fi
    done
}
 
function checkqueue {
    OLDCHQUEUE=$QUEUE
    for PID in $OLDCHQUEUE
    do
        if [ ! -d /proc/$PID ] ; then
            regeneratequeue # at least one PID has finished
            break
        fi
    done
}
 
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

# parse command line
if [ $# -eq 0 ]; then #  must be at least one arg
    echo "$USAGE" >&2
    exit 1
fi

while getopts j:rh OPT; do # "j:" waits for an argument "h" doesnt
    case $OPT in
    h)  echo "$USAGE"
        exit 0 ;;
    j)  MAX_NPROC=$OPTARG ;;
    d)  echo "Deploying is turned ON!"
        DEPLOY="yes"
        shift ;;
    \?) # getopts issues an error message
        echo "$USAGE" >&2
        exit 1 ;;
    esac
done
 
# Main program
echo Using $MAX_NPROC parallel threads
shift `expr $OPTIND - 1` # shift input args, ignore processed args

if [ $# -eq 0 ]; 
then
    set ./parent ./d*
else
    set ./parent $*
fi
 
for directory;
do
 
    if [ -d "$directory" ]; then 
        eval "exec_mvn $directory &"
        # DEFINE COMMAND END
     
        PID=$!
        queue $PID
     
        while [ $NUM -ge $MAX_NPROC ]; do
            checkqueue
            sleep 0.4
        done
    fi    
done
wait # wait for all processes to finish before exit