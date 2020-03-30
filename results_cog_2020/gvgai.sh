#!/bin/sh
#$ -pe smp 1 #Request 10 CPU cores
#$ -l h_rt=80:00:00 # Request 7h 20m 10s of walltime
#$ -l h_vmem=2G # Request 2GB RAM for each core requested
#$ -M diego.perez@qmul.ac.uk # Sends notifications email to this address
#$ -m bea # Emails are sent on begin, end and abortion
#$ -t 1-20  # Index of the job array ${SGE_TASK_ID}
#$ -tc 20  # Number of jobs allowed to run concurrently

module load java/9.0.1-oracle

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    exit -1
fi

cd /data/scratch/$USER/$1/ # Move to the scratch directory
#$ -cwd

# Jobs
p0=($2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2 $2)
p1=(0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19)
p2=(5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5)
p3=(20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20)

nb=${SGE_TASK_ID}-1

#java -jar rhneat.jar ${p1[$nb]} ${p2[$nb]} ${p3[$nb]} ${p4[$nb]} >> rhneat_${p1[$nb]}_${p2[$nb]}_${p3[$nb]}_${p4[$nb]}.txt 2> rhneat_${p1[$nb]}_${p2[$nb]}_${p3[$nb]}_${p4[$nb]}.err
java -jar GVGAI.jar ${p0[$nb]} ${p1[$nb]} ${p2[$nb]} ${p3[$nb]} >> rhneat_${p1[$nb]}_${p2[$nb]}_${p3[$nb]}.txt 2> rhneat_${p1[$nb]}_${p2[$nb]}_${p3[$nb]}.err
