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
lot  = [55.3471, 72.1901, 48.4087, 3.1901]

duration = [rr[0],sjn[0],fcfs[0], lot[0]]
cputime = [rr[1], sjn[1], fcfs[1], lot[1]]
context = [rr[2], sjn[2], fcfs[2], lot[2]]
idle = [rr[3], sjn[3], fcfs[3], lot[3]]

names = ['Round Robin', 'SJN', 'FCFS', 'Lottery Scheduling']

data = duration
groups = [i for i in xrange(len(data))]

fig = plt.figure()
ax = fig.add_subplot(111)
width = 0.7


rects1 = ax.bar(np.arange(len(data)) * 3, data,bottom=0, width=width, align='center', color=(.5, .75, 0.5, 1))
data = cputime
rects2 = ax.bar(np.arange(len(data)) * 3+width, data,bottom=0, width=width, align='center', color=(.75, .5, 0.75, 1))
data = idle
rects3 = ax.bar(np.arange(len(data)) * 3+width*2, data,bottom=0, width=width, align='center', color=(.5, .75, 0.75, 1))

ax.set_xlabel('Scheduling Algorithm')
ax.set_ylabel('Time / Units')
ax.set_title('Average times')

ind = np.arange(len(names))
ticks = [-0.35+0.7*2-0.35, -0.35+(0.7*6)-0.1, -0.35+(0.7*11)-0.6, -0.35+(0.7*15)-0.4]
ax.set_xticks(ticks)
xtickNames = ax.set_xticklabels(names)
plt.setp(xtickNames, rotation=0, fontsize=10)


ax.legend( (rects1[0], rects2[0], rects3[0]), ('Mean Duration', 'CPU Time', 'Idle Time'), 
	bbox_to_anchor=(1.05, 1), loc=2, borderaxespad=0.)

ax.set_ylim(ymin=0, ymax=100)
plt.subplots_adjust(bottom=0.15, right=.68) 

def autolabel(rects):
    # attach some text labels
    for rect in rects:
        height = rect.get_height()
        ax.text(rect.get_x()+rect.get_width()/2., 1.05*height, '%d'%int(height),
                ha='center', va='bottom')

autolabel(rects1)
autolabel(rects2)
autolabel(rects3)




plt.show()

fig = plt.figure()
ax = fig.add_subplot(111)
data = context
rect = ax.bar(np.arange(len(data)), data,bottom=0, width=width, align='center', color=(.5, .75, 0.75, 1))
ax.set_xticks(groups)
xtickNames = ax.set_xticklabels(names)
plt.setp(xtickNames, rotation=0, fontsize=10)

ax.set_xlabel('Scheduling Algorithm')
ax.set_ylabel('No. Context Switches')
ax.set_title('A Comparison of Context Switches')
plt.show()