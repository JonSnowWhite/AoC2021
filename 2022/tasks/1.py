import math
from Util import time_and_result


@time_and_result
def task1():
    """
    Don't save everything in a list, just keep track of the largest until now, saves memory
    """
    with open("../data/task1.txt", "r") as measurements:
        current_batch = 0
        max_batch = 0
        for line in measurements:
            _line = line.strip()
            if _line == "":
                # final case
                max_batch = max(max_batch, current_batch)
                current_batch = 0
            else:
                current_batch += int(_line)
    return max_batch


@time_and_result
def task2():
    """
    Sigh...save in list now, slower but less code
    """
    with open("../data/task1.txt", "r") as measurements:
        current_batch = 0
        batches = []
        for line in measurements:
            _line = line.strip()
            if _line == "":
                # final case
                batches += [current_batch]
                current_batch = 0
            else:
                current_batch += int(_line)
    return sum(sorted(batches, reverse=True)[:3])


task1()
task2()
