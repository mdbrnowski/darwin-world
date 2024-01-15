import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("log.csv")

df.plot()
plt.show()
