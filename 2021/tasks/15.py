import math

from Util import time_and_result


@time_and_result
def task1(file):
    risk_levels = []
    with open(file, "r") as inputs:
        for line in inputs:
            if line != "\n":
                risk_levels.append(line.strip())
    optimal_risks = []
    x_max = len(risk_levels)
    y_max = len(risk_levels[0])
    for i in range(x_max):
        optimal_risks.append(y_max * [0])
    # start of dynamic programming
    optimal_risks[0][0] = 0
    for x in range(x_max):
        for y in range(y_max):
            if x == 0 and y == 0:
                continue
            # yadda yadda for python indices
            a = math.inf
            b = math.inf
            if x != 0:
                a = optimal_risks[x-1][y]
            if y != 0:
                b = optimal_risks[x][y-1]
            optimal_risks[x][y] = min(a, b) + int(risk_levels[x][y])

    changed = True
    counter = -1
    while changed:
        counter += 1
        changed = False
        new_optimal_risks = []
        for i in range(x_max):
            new_optimal_risks.append(y_max * [0])
        for x in range(x_max):
            for y in range(y_max):
                _min = optimal_risks[x][y]
                if x != 0 and optimal_risks[x-1][y] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x-1][y] + int(risk_levels[x][y])
                if y != 0 and optimal_risks[x][y-1] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x][y-1] + int(risk_levels[x][y])
                if x != x_max-1 and optimal_risks[x+1][y] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x+1][y] + int(risk_levels[x][y])
                if y != y_max-1 and optimal_risks[x][y+1] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x][y+1] + int(risk_levels[x][y])
                new_optimal_risks[x][y] = _min
        optimal_risks = new_optimal_risks
    print(f"Changed a total of {counter} times!")
    return optimal_risks[-1][-1]


@time_and_result
def task2(file):
    risk_levels = []
    with open(file, "r") as inputs:
        lines = [line for line in inputs]
        for j in range(5):
            for line in lines:
                if line != "\n":
                    l = []
                    for i in range(5):
                        for num in line.strip("\n"):
                            new_num = (int(num) + i + j)
                            if new_num >= 10:
                                new_num -= 9
                            l.append(new_num)
                    risk_levels.append(l)
    optimal_risks = []
    x_max = len(risk_levels)
    y_max = len(risk_levels[0])
    for i in range(x_max):
        optimal_risks.append(y_max * [0])
    # start of dynamic programming
    optimal_risks[0][0] = 0
    for x in range(x_max):
        for y in range(y_max):
            if x == 0 and y == 0:
                continue
            # yadda yadda for python indices
            a = math.inf
            b = math.inf
            if x != 0:
                a = optimal_risks[x-1][y]
            if y != 0:
                b = optimal_risks[x][y-1]
            optimal_risks[x][y] = min(a, b) + int(risk_levels[x][y])

    changed = True
    counter = -1
    while changed:
        counter += 1
        changed = False
        new_optimal_risks = []
        for i in range(x_max):
            new_optimal_risks.append(y_max * [0])
        for x in range(x_max):
            for y in range(y_max):
                _min = optimal_risks[x][y]
                if x != 0 and optimal_risks[x-1][y] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x-1][y] + int(risk_levels[x][y])
                if y != 0 and optimal_risks[x][y-1] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x][y-1] + int(risk_levels[x][y])
                if x != x_max-1 and optimal_risks[x+1][y] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x+1][y] + int(risk_levels[x][y])
                if y != y_max-1 and optimal_risks[x][y+1] + int(risk_levels[x][y]) < _min:
                    changed = True
                    _min = optimal_risks[x][y+1] + int(risk_levels[x][y])
                new_optimal_risks[x][y] = _min
        optimal_risks = new_optimal_risks
    print(f"Changed a total of {counter} times!")
    return optimal_risks[-1][-1]


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task15_test.txt")
    task2("../data/task15_test.txt")
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task15.txt")
    task2("../data/task15.txt")
    print()


test()
run()
