# only backtest from report result
import numpy as np
import os
import pathlib
import time
import xml.etree.ElementTree as ET
import matplotlib.pyplot as plt
import pandas as pd

from pyparsing import line
program_starttime = time.time()

filename = "result_wr_50_2_4_1000_10000.txt"
ProgramPath = pathlib.Path(__file__).parent.resolve()

# df = pd.DataFrame()
# winrate = []
# lossnum = []
# tradenum = []
# testnum = [] 
# rate = []


filepath = os.path.join(ProgramPath, filename)
l = len(filename)
title = filename[7: l-4]
titcut = title.split("_") # wr_54_4_26_1000_10000
# winrate.append(titcut[1])
# lossnum.append(titcut[2])
# tradenum.append(titcut[3])
# testnum.append(titcut[4]+"_"+titcut[5])

array = []
with open(filepath) as f:
    lines = f.readlines()
    for this_line in lines:
        array.append(float(this_line))

mymean = '%.2f' %np.mean(array)
mymedian = '%.2f' %np.median(array)
# rate.append(mymean)
title = title + "_mean_" + mymean + "_median_" + mymedian
fig, ax = plt.subplots(1, 1)
ax.hist(array, bins=np.arange(min(array), max(array) + 0.5, 0.5))
ax.set_title(title)

plt.savefig(os.path.join(ProgramPath, title+".jpg"))

# df['winrate'] = winrate
# df['loss'] = lossnum
# df['trades'] = tradenum
# df['tests'] = testnum
# df['rate'] = rate 
# df.to_excel(os.path.join(ProgramPath,"report.xlsx") , header= True , index= False)
