#!/bin/bash

for f in $1_lvl*.txt;
do
 echo "Processing $f"
 # do something on $f
 python change.py $f $2 $3 > aux_$f

 mv aux_$f $f
 #cat $f

done
