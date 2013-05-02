import matplotlib.pyplot as plt
import numpy as np


def autolabel(ax, rects):
    # attach some text labels
    for rect in rects:
        height = rect.get_height()
        ax.text(rect.get_x()+rect.get_width()/2., 1.05*height, '%d'%int(height),
                ha='center', va='bottom')

def makeBars(ax, data, offset, width, color, increment):
	return ax.bar(np.arange(len(data))*offset + increment, data, bottom=0, width=width, align='edge', color=color)

def makeplot(allData, title, y_label):
	colours = [(.5, .75, 0.5, 1), (.75, .75, 0.5, 1), (.5, .75, 0.75, 1), (.75, .5, 0.75, 1)]
	names = ['SRT', 'Round Robin',  'FCFS', 'Lottery Scheduling']
	test_names = ['Test1', 'Test2', 'Test3', 'Blocked', 'Non Blocked', 'No Priority']

	num_algorithms = len(names)
	num_tests= len(test_names)

	fig = plt.figure(figsize=(13,5))
	ax = fig.add_subplot(111)
	width = 0.8

	ax.set_xlim((-width/2, len(allData)))
	ax.set_ylim(ymin=0, ymax=max(allData) + max(allData)*0.2)

	allRects = []
	for i in range(num_algorithms):
		row_data = allData[i::num_algorithms]
		rects = makeBars(ax, row_data, num_algorithms, width, colours[i], width*i)
		allRects.append(rects)
		autolabel(ax, rects)

	ticks = [i*(5*width) + (width*2) for i in range(num_tests)]
	ax.set_xticks(ticks)

	xtickNames = ax.set_xticklabels(test_names)
	plt.setp(xtickNames, rotation=0, fontsize=10)

	ax.legend(allRects, names, bbox_to_anchor=(1.05, 1), loc=2, borderaxespad=0.)

	ax.set_xlabel('Test File')
	ax.set_ylabel(y_label)
	ax.set_title(title)
	fig.subplots_adjust(left=0.1, right=0.75) 
	plt.show()


#Duration test data
test1_dur = [44,60,48,55.3716]
test2_dur = [44,59,54,52.7515]
test3_dur = [91,133,134,122.587]
blocked_dur = [55,59,52,58.8708]
no_blocked_dur = [57,91,69,86.48]
no_prior_dur = [44,60,48,57.3909]

allDurData = test1_dur+test2_dur+test3_dur+blocked_dur+no_blocked_dur+no_prior_dur

#CPU Time test data
test1_cpu = [77,69,75,72.0944]
test2_cpu = [79,69,69,70.7122]
test3_cpu = [182,182,191,182.4386]
blocked_cpu = [101,74,88,85.2827]
no_blocked_cpu = [110,110,110,110.0]
no_prior_cpu = [77,69,75,69.3216]

allCPUData = test1_cpu+test2_cpu+test3_cpu+blocked_cpu+no_blocked_cpu+no_prior_cpu

#Conext Swtiches test data
test1_ctext = [14,68,10,48.519]
test2_ctext = [20,64,15,46.4977]
test3_ctext = [43,172,134,140.783]
blocked_ctext = [12,55,10,40.9669]
no_blocked_ctext = [8,105,69,75.6119]
no_prior_ctext = [14,68,10,51.6611]

allCtextData = test1_ctext+test2_ctext+test3_ctext+blocked_ctext+no_blocked_ctext+no_prior_ctext

#Idle time test data
test1_idle = [8,0,6,3.0944]
test2_idle = [10,0,0,1.7122]
test3_idle = [0,0,9,0.4386]
blocked_idle = [32,5,19,16.2827]
no_blocked_idle = [0,0,0,0]
no_prior_idle = [8,0,6,0.3216]

allIdleData = test1_idle+test2_idle+test3_idle+blocked_idle+no_blocked_idle+no_prior_idle

#First Job Finish Duration
test1_fst = [11,48,13,36.6029]
test2_fst = [15,48,24,34.9245]
test3_fst = [15,92,42,59.2018]
blocked_fst = [11,41,13,33.7594]
no_blocked_fst = [11,55,26,53.5286]
no_prior_fst = [11,48,13,42.3337]

allFstData = test1_fst+test2_fst+test3_fst+blocked_fst+no_blocked_fst+no_prior_fst

#First Job Finish Time (CPU Cycles)
test1_fst_finish = [15,52,13,37.3673]
test2_fst_finish = [15,52,28,35.0173]
test3_fst_finish = [15,92,45,59.2033]
blocked_fst_finish = [15,45,13,34.4534]
no_blocked_fst_finish = [15,59,26,56.9598]
no_prior_fst_finish = [15,52,13,44.2713]

allFstFinishData = test1_fst_finish+test2_fst_finish+test3_fst_finish+blocked_fst_finish+no_blocked_fst_finish+no_prior_fst_finish

makeplot(allDurData, "Mean Duration", "CPU Cycles")
makeplot(allCPUData, "CPU Time", "CPU Cycles")
makeplot(allCtextData, "Context Switches", "No. Context Switches")
makeplot(allIdleData, "Idle Time", "CPU Cycles")
makeplot(allFstData, "First Job Duration", "CPU Cycles")
makeplot(allFstFinishData, "First Job Finish Time", "CPU Cycles");

def makePercentages(lst1, lst2):
	percent = []
	for i in range(len(lst1)):
		percent.append(float(lst1[i])/float(lst2[i]) *100)	

	return percent

test1_idleper = makePercentages(test1_idle, test1_cpu)
test2_idleper = makePercentages(test2_idle, test2_cpu)
test3_idleper = makePercentages(test3_idle, test3_cpu)
blocked_idleper = makePercentages(blocked_idle, blocked_cpu)
no_blocked_idleper = makePercentages(no_blocked_idle, no_blocked_cpu)
no_prior_idleper = makePercentages(no_prior_idle, no_prior_cpu)

allIdleperData = test1_idleper+test2_idleper+test3_idleper+blocked_idleper+no_blocked_idleper+no_prior_idleper

makeplot(allIdleperData, "Percent Idle Time", "% Idle")

