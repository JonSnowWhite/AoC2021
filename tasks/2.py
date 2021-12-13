from Util import time_and_result


@time_and_result
def task1():
    with open("../data/task2.txt", "r") as measurements:
        lines = [i.strip(" ") for i in measurements if i != ""]
        horizontal = 0
        depth = 0
        for line in lines:
            text, number = line.split(" ")
            number = int(number)
            if text == "forward":
                horizontal += number
            elif text == "down":
                depth += number
            elif text == "up":
                depth -= number
            else:
                print(f"Could not parse line {line}")
        return horizontal*depth


@time_and_result
def task2():
    with open("../data/task2.txt", "r") as measurements:
        lines = [i.strip(" ") for i in measurements if i != ""]
        horizontal = 0
        depth = 0
        aim = 0
        for line in lines:
            text, number = line.split(" ")
            number = int(number)
            if text == "forward":
                horizontal += number
                depth += (aim*number)
            elif text == "down":
                aim += number
            elif text == "up":
                aim -= number
            else:
                print(f"Could not parse line {line}")
        return horizontal*depth


task1()
task2()
