#!/usr/bin/python3

from sys import argv
import pandas as pd
import matplotlib.pyplot as plt

if len(argv) != 2:
    raise Exception('Pass exactly one argument - log file name')

df = pd.read_csv(argv[1])

df.plot()
plt.show()
