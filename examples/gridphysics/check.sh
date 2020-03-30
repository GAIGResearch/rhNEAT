#!/bin/bash

for f in *.txt;
do
 
 if [[ $f != *"_lvl"* ]]
 then
  #echo "It's there! $f";
  git diff $f >> allDiffs.txt
 fi


done
