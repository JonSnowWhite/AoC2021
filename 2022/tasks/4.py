from Util import time_and_result
from Util import test_inputs
from Util import task_inputs

TASK = 4


def elves_from_line(line: str) -> list[list[int]]:
    return list(map(lambda x: [int(y) for y in x.split("-")], line.split(",")))


def contains_other(elves):
    return elves[0][1] <= elves[1][1] and elves[0][0] >= elves[1][0] or elves[1][1] <= elves[0][1] and elves[1][0] >= elves[0][0]


def overlap(elves):
    return elves[1][0] <= elves[0][1] <= elves[1][1] or elves[1][0] <= elves[0][0] <= elves[1][1] or elves[0][0] <= elves[1][1] <= elves[0][1] or elves[0][0] <= elves[1][0] <= elves[0][1]


@time_and_result
def task1(inputs):
    """
    Split line/rucksack into two parts, for each letter in first half check if existent in second half
    """
    return len(list(filter(lambda x: contains_other(elves_from_line(x)), inputs)))


@time_and_result
def task2(inputs):
    """
    basically the same with different test
    """
    return len(list(filter(lambda x: overlap(elves_from_line(x)), inputs)))


def test():
    print("- - - - - TESTS - - - - -")
    task1(test_inputs(TASK))
    task2(test_inputs(TASK))
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1(task_inputs(TASK))
    task2(task_inputs(TASK))
    print()


test()
run()
