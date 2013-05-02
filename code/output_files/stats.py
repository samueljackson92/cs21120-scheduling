#!/usr/bin/env python

##############################################
# stats.py
# Run some data analysis on the results of the 
# scheduling algorithms
# Author: 	Samuel Jackson (slj11@aber.ac.uk)
# Date:		21/4/13
##############################################

from argparse import ArgumentParser
import matplotlib.pyplot as plt
from matplotlib import ticker
import numpy as np
import re

#Parse command line options for filename
parser = ArgumentParser(description="Statistical analysis tool.")
parser.add_argument('-f', '--file', type=str, nargs=1, required=True, help='Input CSV file.')
args = parser.parse_args()
fname = args.file[0]

#load data from CSV file.
try:
	dat = np.loadtxt(fname, delimiter=",", comments='#')
except Exception, e:
	print "Exception: ", e
	exit(-1)


numTests = len(dat)
meandur = 0
meanCPUTime = 0
meanContextSwitches = 0
meanIdleTime = 0

flatdat = [item for sublist in dat for item in sublist]

fstJobDuration = flatdat[::6]
fstJobFinish = flatdat[1::6]
duration = flatdat[2::6]
cputime = flatdat[3::6]
contextSwitches = flatdat[4::6]
idleTime = flatdat[5::6]

meanFstFinish = sum(fstJobFinish) / numTests
meanFstTime = sum(fstJobDuration) / numTests
meandur = sum(duration) / numTests
meanCPUTime = sum(cputime) / numTests
meanIdleTime = sum(idleTime) / numTests
meanContextSwitches = sum(contextSwitches) / numTests

def histo(data, title, subtitle):
	hist, bins = np.histogram(data, bins=(max(data)+1 - min(data)), range=(min(data), max(data)+1))
	width = 0.7*(bins[1]-bins[0])
	center = (bins[:-1]+bins[1:])/2

	fig = plt.figure()
	ax = fig.add_subplot(111)
	ax.bar(center, hist, align = 'center', width = width)

	ax.set_xlabel(title)
	ax.set_ylabel('Frequency')
	ax.set_title(subtitle)

	ax.set_xticks([i+0.5 for i in bins])
	ax.set_xticklabels([int(i) for i in bins])

	plt.show()


print "Average First Job Finish Time: ", meanFstFinish

print "Average First Job Duration Time: ", meanFstTime

print "Mean Total Duration: ", meandur
#histo(duration, 'Total Duration', 'Mean Total Duration')


print "Mean CPU Time: ", meanCPUTime
#histo(cputime, 'CPU Time', 'Total CPU Time')

print "Mean Context Switches: ", meanContextSwitches
#histo(contextSwitches,'Context Switches', 'Total Context Switches')

print "Mean Idle Time: ", meanIdleTime
#histo(idleTime,'Idle Time','Total Idle Time')
