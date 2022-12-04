from Util import time_and_result
from Util import test_inputs
from Util import task_inputs
import string

TASK = 3


def score(letter: str):
    return string.ascii_letters.index(letter) + 1


@time_and_result
def task1(inputs):
    """
    Split line/rucksack into two parts, for each letter in first half check if existent in second half
    """
    total = 0
    for line in inputs:
        for a in set(line[:len(line)//2]):
            if a in line[len(line)//2:]:
                total += score(a)
    return total


@time_and_result
def task2(inputs):
    """
    Group elves by triples(?) and then check for each letter in first whether it exists in the other two.
    Could probably be done by set() of each elf and computing union.
    """
    total = 0
    groups = list(zip([x for x in inputs if inputs.index(x) % 3 == 0], [x for x in inputs if inputs.index(x) % 3 == 1], [x for x in inputs if inputs.index(x) % 3 == 2]))
    for group in groups:
        for letter in set(group[0]):
            if letter in group[1] and letter in group[2]:
                total += score(letter)
    return total


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
