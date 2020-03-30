__author__ = 'dperez'

import sys, glob, os


if __name__ == '__main__':
    file = sys.argv[1]
    char0 = sys.argv[2]
    char1 = sys.argv[3]

    with open(file) as f:
        levelFile = f.readlines()

        for line in levelFile:
            lineSub = line.replace(char0, char1)
            print lineSub,


