import matplotlib.pyplot as plt
import numpy as np


def autolabel(ax, rects):
    # attach some text labels
    for rect in rects:
        height = rect.get_height()
        ax.text(rect.get_x()+rect.get_width()/2., 1.05*height, '%d'%int(height),
                ha='center', va='bottom')


def makeplot(allData, test1, test2, test3, blocked, no_blocked, no_prior, title, y_label):
	names = ['SJN', 'Round Robin',  'FCFS', 'Lottery Scheduling']
	num_tests = (len(allData)/len(names))

	fig = plt.figure(figsize=(13,5))
	ax = fig.add_subplot(111)
	width = 0.8

	ax.set_xlim((-width/2, len(allData)))
	#ax.set_ylim(ymin=0, ymax=max(allData)+20)
	ax.set_ylim(ymin=0, ymax=50)

	rects1 = ax.bar(np.arange(len(test1)) * num_tests, test1, bottom=0, width=width, align='edge', color=(.5, .75, 0.5, 1))
	rects2 = ax.bar(np.arange(len(test2)) * num_tests+width, test2, bottom=0, width=width, align='edge', color=(.75, .75, 0.5, 1))
	rects3 = ax.bar(np.arange(len(test3)) * num_tests+width*2, test3, bottom=0, width=width, align='edge', color=(.5, .75, 0.75, 1))
	rects4 = ax.bar(np.arange(len(blocked)) * num_tests+width*3, blocked, bottom=0, width=width, align='edge', color=(.75, .5, 0.75, 1))
	rects5 = ax.bar(np.arange(len(no_blocked)) * num_tests+width*4, no_blocked, bottom=0, width=width, align='edge', color=(.5, .5, 0.5, 1))
	rects6 = ax.bar(np.arange(len(no_prior)) * num_tests+width*5, no_prior, bottom=0, width=width, align='edge', color=(.5, .25, 0.5, 1))

	ax.set_xticks([(i*num_tests)+width*(num_tests/2) for i in range(len(names))])

	xtickNames = ax.set_xticklabels(names)
	plt.setp(xtickNames, rotation=0, fontsize=10)

	autolabel(ax, rects1)
	autolabel(ax, rects2)
	autolabel(ax, rects3)
	autolabel(ax, rects4)
	autolabel(ax, rects5)
	autolabel(ax, rects6)

	ax.legend( (rects1[0], rects2[0], rects3[0], rects4[0], rects5[0], rects6[0]), 
		('Test1', 'Test2', 'Test3', 'Blocked', 'Not Blocked', 'Same Priority'), 
		bbox_to_anchor=(1.05, 1), loc=2, borderaxespad=0.)

	ax.set_xlabel('Scheduling Algorithm')
	ax.set_ylabel(y_label)
	ax.set_title(title)
	fig.subplots_adjust(left=0.1, right=0.75) 
	plt.show()


#Duration test data
test1_dur = [44,60,48,55.355]
test2_dur = [44,59,54,52.7214]
test3_dur = [91,133,134,122.6082]
blocked_dur = [55,59,52,58.8188]
no_blocked_dur = [57,91,69,86.5511]
no_prior_dur = [44,60,48,57.3252]

allDurData = test1_dur+test2_dur+test3_dur+blocked_dur+no_blocked_dur+no_prior_dur

#CPU Time test data
test1_cpu = [77,69,75,72.1559]
test2_cpu = [79,69,69,70.7239]
test3_cpu = [182,182,191, 182.4428]
blocked_cpu = [101,74,88,85.1865]
no_blocked_cpu = [110,110,110,110.0]
no_prior_cpu = [77,69,75,69.3116]

allCPUData = test1_cpu+test2_cpu+test3_cpu+blocked_cpu+no_blocked_cpu+no_prior_cpu

#Conext Swtiches test data
test1_ctext = [14,68,10,48.452]
test2_ctext = [20,64,15,46.5125]
test3_ctext = [43,172,134,140.8237]
blocked_ctext = [12,55,10,40.9425]
no_blocked_ctext = [8,105,69,75.5918]
no_prior_ctext = [14,68,10,51.508]

allCtextData = test1_ctext+test2_ctext+test3_ctext+blocked_ctext+no_blocked_ctext+no_prior_ctext

#Idle time test data
test1_idle = [8,0,6,3.1559]
test2_idle = [10,0,0,1.7239]
test3_idle = [0,0,9,0.4428]
blocked_idle = [32,5,19,16.1865]
no_blocked_idle = [0,0,0,0]
no_prior_idle = [8,0,6,0.3116]

allIdleData = test1_idle+test2_idle+test3_idle+blocked_idle+no_blocked_idle+no_prior_idle

test1_fst = [11,48,13,37.3631]
test2_fst = [15,48,24,35.0107]
test3_fst = [15,92,42,59.3119]
blocked_fst = [11,41,13,34.351]
no_blocked_fst = [11,55,26,57.2704]
no_prior_fst = [11,48,13,44.201]

allFstData = test1_fst+test2_fst+test3_fst+blocked_fst+no_blocked_fst+no_prior_fst

# makeplot(allDurData, test1_dur, test2_dur, test3_dur, blocked_dur, no_blocked_dur, no_prior_dur, "Mean Duration", "Mean Duration Time / Units")
# makeplot(allCPUData, test1_cpu,test2_cpu,test3_cpu,blocked_cpu,no_blocked_cpu,no_prior_cpu, "CPU Time", "CPU Time / Units")
# makeplot(allCtextData, test1_ctext,test2_ctext,test3_ctext,blocked_ctext,no_blocked_ctext,no_prior_ctext, "Context Switches", "No. Context Switches")
# makeplot(allIdleData, test1_idle,test2_idle,test3_idle,blocked_idle,no_blocked_idle,no_prior_idle, "Idle Time", "Idle Time / Units")
# makeplot(allFstData, test1_fst,test2_fst,test3_fst,blocked_fst,no_blocked_fst,no_prior_fst, "First Job Finish Time", "Finish Time / Units")

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

makeplot(allFstData,test1_idleper,test2_idleper,test3_idleper,blocked_idleper,no_blocked_idleper,no_prior_idleper, "Percent Idle Time", "% Idle")

