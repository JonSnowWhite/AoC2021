from Util import time_and_result


@time_and_result
def task1():
    with open("../data/task3.txt", "r") as measurements:
        lines = [i.strip(" ").strip("\n") for i in measurements if i != ""]
        delta_rate_list = 12 * [0]
        for line in lines:
            num = int(line, 2)
            for i in range(len(line)):
                if (num >> i) & 1 == 1:
                    delta_rate_list[i] += 1
                else:
                    delta_rate_list[i] -= 1
        delta_rate = 0
        for i in range(len(delta_rate_list)):
            if delta_rate_list[i] > 0:
                delta_rate += 2**i
        return delta_rate * (delta_rate ^ 0xFFF)


def get_max(l, j):
    counter1 = 0
    counter0 = 0
    for i in l:
        if i[j] == "1":
            counter1 += 1
        else:
            counter0 += 1
    if counter1 >= counter0:
        return 1
    else:
        return 0


def get_min(l, j):
    counter1 = 0
    counter0 = 0
    for i in l:
        if i[j] == "1":
            counter1 += 1
        else:
            counter0 += 1
    if counter1 == 0:
        return 0
    elif counter0 == 0:
        return 1
    elif counter0 <= counter1:
        return 0
    else:
        return 1


@time_and_result
def task2():
    with open("../data/task3.txt", "r") as measurements:
        lines = [i.strip(" ").strip("\n") for i in measurements if i != ""]
        r1 = 0
        r2 = 0
        oxygen = lines[:]
        scrubber = lines[:]
        for i in range(12):
            max = get_max(oxygen, i)
            min = get_min(scrubber, i)

            oxygen = [x for x in oxygen if x[i] == str(max)]
            if len(oxygen) == 1:
                r1 = int(oxygen[0], 2)
            scrubber = [x for x in scrubber if x[i] == str(min)]
            if len(scrubber) == 1:
                r2 = int(scrubber[0], 2)
    return r1*r2



task1()
task2()
