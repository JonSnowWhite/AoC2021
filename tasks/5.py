from Util import time_and_result


@time_and_result
def task1():
    grid = []
    for i in range(1000):
        grid.append(1000 * [0])
    with open("../data/task5.txt", "r") as measurements:
        for line in measurements:
            a, b = [[int(y) for y in x.split(",")] for x in line.replace(" ", "").strip().split("->")]
            if a[0] == b[0]:
                start = min(a[1], b[1])
                end = max(a[1], b[1])
                for i in range(start, end+1):
                    grid[i][a[0]] += 1
            if a[1] == b[1]:
                start = min(a[0], b[0])
                end = max(a[0], b[0])
                for i in range(start, end+1):
                    grid[a[1]][i] += 1

    ret = 0
    for i in range(1000):
        for j in range(1000):
            if grid[i][j] > 1:
                ret += 1
    return ret


@time_and_result
def task2():
    grid = []
    for i in range(1000):
        grid.append(1000 * [0])
    with open("../data/task5.txt", "r") as measurements:
        for line in measurements:
            a, b = [[int(y) for y in x.split(",")] for x in line.replace(" ", "").strip().split("->")]
            if a[0] == b[0]:
                start = min(a[1], b[1])
                end = max(a[1], b[1])
                for i in range(start, end+1):
                    grid[i][a[0]] += 1
            if a[1] == b[1]:
                start = min(a[0], b[0])
                end = max(a[0], b[0])
                for i in range(start, end+1):
                    grid[a[1]][i] += 1
            if a[0] - b[0] == a[1] - b[1] or a[0] - b[0] == b[1] - a[1]:
                if b[0] > a[0]:
                    if b[1] > a[1]:
                        for i in range(b[0]-a[0]+1):
                            grid[a[1]+i][a[0]+i] += 1
                    if b[1] < a[1]:
                        for i in range(b[0]-a[0]+1):
                            grid[a[1]-i][a[0]+i] += 1
                if b[0] < a[0]:
                    if b[1] > a[1]:
                        for i in range(a[0]-b[0]+1):
                            grid[a[1]+i][a[0]-i] += 1
                    if b[1] < a[1]:
                        for i in range(a[0]-b[0]+1):
                            grid[a[1]-i][a[0]-i] += 1

    ret = 0
    for line in grid:
        print(line)
    for i in range(1000):
        for j in range(1000):
            if grid[i][j] > 1:
                ret += 1
    return ret


task2()
