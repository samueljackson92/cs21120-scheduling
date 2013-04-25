#!/usr/bin/env python

##############################################
# simple_stats.py
# Run some data analysis on the results of the 
# scheduling algorithms
# Author: 	Samuel Jackson (slj11@aber.ac.uk)
# Date:		23/4/13
##############################################

from argparse import ArgumentParser
import matplotlib.pyplot as plt
from matplotlib import ticker
import numpy as np

#Parse command line options for filename
# parser = ArgumentParser(description="Statistical analysis tool.")
# parser.add_argument('-f', '--file', type=str, nargs=1, required=True, help='Input CSV file.')
# args = parser.parse_args()
# fname = args.file[0]

# #load data from CSV file.
# try:
# 	dat = np.loadtxt(fname, delimiter=",")
# except Exception, e:
# 	print "Exception: ", e
# 	exit(-1)

# if(len(dat) > 0):
# 	dat = dat[0]
# else:
# 	exit(-1)

rr 	 = [60,69,68,0]
sjn  = [44,81,12,12]
fcfs = [48,75,10,6]

duration = [rr[0],sjn[0],fcfs[0]]
cputime = [rr[1], sjn[1], fcfs[1]]
names = ['Round Robin', 'Shortest Job Next', 'First Come, First Served']

data = duration
groups = [i for i in xrange(len(data)*2)]

fig = plt.figure()
ax = fig.add_subplot(111)
width = 0.7


rects1 = ax.bar(np.arange(3) * 2, data,bottom=0, width=width, align='center', color=(.5, .75, 0.5, 1))
data = cputime
rects2 = ax.bar(np.arange(3) * 2+width, data,bottom=0, width=width, align='center', color=(.75, .5, 0.75, 1))

ax.set_xlabel('xtitle')
ax.set_ylabel('ytitle')
ax.set_title('title')

ind = np.arange(len(names))
ticks = [-0.35+0.7, -0.35+(0.7*4)-0.1, -0.35+(0.7*7)-0.2]
ax.set_xticks(ticks)
xtickNames = ax.set_xticklabels(names)
plt.setp(xtickNames, rotation=0, fontsize=10)

ax.legend( (rects1[0], rects2[0]), ('Duration', 'CPU Time') )

ax.set_ylim(ymin=0, ymax=max(data)+10) 

plt.show()