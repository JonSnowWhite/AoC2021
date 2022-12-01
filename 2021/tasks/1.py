import math
from Util import time_and_result


@time_and_result
def task1():
    with open("../data/task1.txt", "r") as measurements:
        increases = 0
        last_val = math.inf
        for line in measurements:
            _line = line.strip(" ")
            if _line == "":
                continue
            elif int(_line) > last_val:
                increases += 1
            last_val = int(_line)
        return increases


@time_and_result
def task2():
    with open("../data/task1.txt", "r") as measurements:
        lines = [int(i.strip(" ")) for i in measurements if i != ""]
        windows = [sum(lines[i:i+3]) for i in range(len(lines)-2)]
        increases = 0
        last_val = math.inf
        for window in windows:
            if window > last_val:
                increases += 1
            last_val = window
    return increases


task1()
task2()
