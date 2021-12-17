from Util import time_and_result


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        x, y = [x.split("=")[1] for x in inputs.readline().strip().split(": ")[1].split(", ")]
        x1, x2 = x.split("..")
        x_range = list(range(int(x1), int(x2) + 1))
        y1, y2 = y.split("..")
        y_range = list(range(int(y1), int(y2) + 1))

    highest_y = -99999
    set_x = x
    for x in range(300):
        for y in range(300):
            x_step = x
            y_step = y
            _x = 0
            _y = 0
            for step in range(300):
                _y += y_step
                _x += x_step
                y_step -= 1
                if x_step != 0:
                    x_step -= 1
                if _y in y_range and _x in x_range:
                    highest_y = max(y, highest_y)
                    set_x = x
                    break
    # get highest of path
    x_step = set_x
    y_step = highest_y
    _x = 0
    _y = 0
    max_y = 0
    for step in range(100):
        _y += y_step
        _x += x_step
        max_y = max(max_y, _y)
        y_step -= 1
        if x_step != 0:
            x_step -= 1
    return max_y


@time_and_result
def task2(file):
    with open(file, "r") as inputs:
        x, y = [x.split("=")[1] for x in inputs.readline().strip().split(": ")[1].split(", ")]
        x1, x2 = x.split("..")
        x_range = list(range(int(x1), int(x2) + 1))
        y1, y2 = y.split("..")
        y_range = list(range(int(y1), int(y2) + 1))

    pairs = 0
    for x in range(300):
        for y in range(-300, 300):
            x_step = x
            y_step = y
            _x = 0
            _y = 0
            for step in range(300):
                _y += y_step
                _x += x_step
                y_step -= 1
                if x_step != 0:
                    x_step -= 1
                if _y in y_range and _x in x_range:
                    pairs += 1
                    break
    return pairs


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task17_test.txt")
    task2("../data/task17_test.txt")
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task17.txt")
    task2("../data/task17.txt")
    print()


test()
run()
