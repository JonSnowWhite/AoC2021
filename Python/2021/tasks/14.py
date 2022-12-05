import math

from Util import time_and_result


def add_or_inc(pair: tuple, into: dict, times=1) -> None:
    try:
        into[pair] += 1
    except:
        into[pair] = 1
    into[pair] += (times - 1)


def task(file, rounds):
    with open(file, "r") as inputs:
        letters = []
        replacements = {}
        pairs = {}

        # parse formula
        formula = inputs.readline().strip()
        first = formula[0]
        last = formula[-1]
        for i in range(len(formula) - 1):
            add_or_inc((formula[i], formula[i+1]), pairs)
        # parse replacements
        for line in inputs:
            if line != "\n":
                first, replacement = line.strip().replace(" ", "").split("->")
                first, second = first[0], first[1]
                replacements[(first, second)] = replacement
                if replacement not in letters:
                    letters += [replacement]
        # replace
        while rounds > 0:
            new_pairs = {}
            for pair in pairs.keys():
                occurences = pairs[pair]
                # try to replace tuple with something
                try:
                    replacement = replacements[pair]
                    add_or_inc((pair[0], replacement), new_pairs, occurences)
                    add_or_inc((replacement, pair[1]), new_pairs, occurences)
                # just reinsert the pair
                except:
                    add_or_inc(pair, new_pairs, occurences)
            pairs = new_pairs
            rounds -= 1
    # count occurrences
    min_occ = math.inf
    max_occ = 0
    for letter in letters:
        count = 0
        for pair in pairs.keys():
            count += pairs[pair] * pair.count(letter)
        # kinda buggy, sometimes it works with += 1, sometimes without
        if letter == last:
            count += 0
        if letter == first:
            count += 0
        count = count // 2
        min_occ = min(count, min_occ)
        max_occ = max(count, max_occ)
    return max_occ - min_occ


@time_and_result
def task1(file, rounds):
    return task(file, rounds)


@time_and_result
def task2(file, rounds):
    return task(file, rounds)


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task14_test.txt", 10)
    task2("../data/task14_test.txt", 40)
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task14.txt", 10)
    task2("../data/task14.txt", 40)
    print()


test()
run()
