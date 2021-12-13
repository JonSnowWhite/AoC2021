from Util import time_and_result


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        ret_sum = 0
        lines = [line.strip() for line in inputs]
        for i in range(len(lines)):
            for j in range(len(lines[i])):
                val = lines[i][j]
                if i >= 1 and lines[i-1][j] <= val:
                    continue
                if i < len(lines)-1 and lines[i+1][j] <= val:
                    continue
                if j >= 1 and lines[i][j-1] <= val:
                    continue
                if j < len(lines[i])-1 and lines[i][j+1] <= val:
                    continue
                ret_sum += (int(val)+1)
        return ret_sum


@time_and_result
def task2(file):
    with open(file, "r") as inputs:
        lines = [line.strip() for line in inputs]
        basins = []
        unsearched = [(i, j) for i in range(len(lines)) for j in range(len(lines[0])) if lines[i][j] != "9"]
        unsearched_in_current_basin = []
        current_searching_node = None
        current_basin = -1
        # run until no basins are left
        while True:
            # basin finished
            if len(unsearched_in_current_basin) == 0:
                if len(unsearched) == 0:
                    break
                # append new basin and get to search one
                current_basin += 1
                basins.append([])
                unsearched_in_current_basin.append(unsearched.pop())
            else:
                # get new node to search
                current_searching_node = unsearched_in_current_basin.pop()
                # search neighbors
                i, j = current_searching_node[0], current_searching_node[1]
                if (i-1, j) in unsearched:
                    unsearched_in_current_basin.append((i-1, j))
                    unsearched.remove((i-1, j))
                if (i+1, j) in unsearched:
                    unsearched_in_current_basin.append((i+1, j))
                    unsearched.remove((i+1, j))
                if (i, j-1) in unsearched:
                    unsearched_in_current_basin.append((i, j-1))
                    unsearched.remove((i, j-1))
                if (i, j+1) in unsearched:
                    unsearched_in_current_basin.append((i, j+1))
                    unsearched.remove((i, j+1))
                # persist our current node
                basins[current_basin].append(current_searching_node)
        basins = sorted(basins, key=lambda x: len(x), reverse=True)
        return len(basins[0])*len(basins[1])*len(basins[2])


def test():
    task1("../data/task9_test.txt")
    task2("../data/task9_test.txt")


def run():
    task1("../data/task9.txt")
    task2("../data/task9.txt")


test()
run()
