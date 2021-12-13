from Util import time_and_result


def fishies(days: int):
    with open("../data/task6.txt", "r") as inputs:
        _fishes = [int(i) for i in [x.strip() for x in inputs if x != ""][0].split(",")]
    fishes = 9 * [0]
    for fish in _fishes:
        fishes[fish] += 1
    for _ in range(days):
        new_fishes = 9 * [0]
        new_fishes[8] += fishes[0]
        new_fishes[6] += fishes[0]
        for i in range(1, len(fishes)):
            new_fishes[i-1] += fishes[i]
        fishes = new_fishes
    return sum([fishes[i] for i in range(9)])


@time_and_result
def task1():
    return fishies(80)


@time_and_result
def task2():
    return fishies(256)


task2()
