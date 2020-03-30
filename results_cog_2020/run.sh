#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters (2): ./run.sh <Config> "
    exit -1
fi

mkdir -p /data/scratch/$USER/$1 # Create a new directory for our job

cp -R $HOME/rhneat/GVGAI.jar /data/scratch/$USER/$1
cp -R $HOME/rhneat/examples /data/scratch/$USER/$1
# mkdir -p /data/scratch/$USER/$1/gamelogs/ # Create a new directory for our job

qsub gvgai.sh $1 $2
