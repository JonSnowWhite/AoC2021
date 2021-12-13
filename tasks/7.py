from Util import time_and_result


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        positions = [int(i) for i in [x.strip() for x in inputs if x != ""][0].split(",")]
    fuels = []
    print(f"min {min(positions)} max {max(positions)}")
    for i in range(min(positions), max(positions)+1):
        fuel = 0
        for position in positions:
            fuel += abs(position-i)
        fuels += [fuel]
    return min(fuels)


@time_and_result
def task2(file, precompute):
    with open(file, "r") as inputs:
        positions = [int(i) for i in [x.strip() for x in inputs if x != ""][0].split(",")]
    if precompute:
        sums = []
        _tmp = 0
        for i in range(max(positions)+1):
            _tmp += i
            sums.append(_tmp)
    fuels = []
    for i in range(min(positions), max(positions)+1):
        fuel = 0
        for position in positions:
            if precompute:
                fuel += sums[abs(position-i)]
            else:
                distance = abs(position-i)
                fuel += int((distance**2+distance)/2)
        fuels += [fuel]
    return min(fuels)


task2("../data/task7_test.txt", True)
task2("../data/task7.txt", True)
