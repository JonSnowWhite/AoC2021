from Util import time_and_result


@time_and_result
def task1(file, rounds):
    neighbors = [(i, j) for i in (0, 1, -1) for j in (0, 1, -1) if (i, j) != (0, 0)]
    flashes = 0
    with open(file, "r") as inputs:
        fishes = [[int(letter) for letter in line.strip()] for line in inputs]
        for r in range(rounds):
            flashed = []
            to_flash = []
            # increase every number by 1
            for i in range((len(fishes))):
                for j in range(len(fishes[i])):
                    fishes[i][j] += 1
                    if fishes[i][j] == 10:
                        to_flash.append((i, j))
            # Spyshka
            while len(to_flash) > 0:
                # change fish status to flashed
                flash_fish = to_flash.pop()
                flashed.append(flash_fish)
                # reset state to 0
                fishes[flash_fish[0]][flash_fish[1]] = 0
                flashes += 1
                # check if we can and have to increase neighbors
                for offset in neighbors:
                    try:
                        i, j = flash_fish[0] + offset[0], flash_fish[1] + offset[1]
                        if (i, j) not in flashed and i > -1 and j > -1:
                            fishes[i][j] += 1
                            # only add once
                            if fishes[i][j] == 10:
                                to_flash.append((i, j))
                    except IndexError:
                        # no fish left out of bounds
                        pass
    return flashes


@time_and_result
def task2(file):
    neighbors = [(i, j) for i in (0, 1, -1) for j in (0, 1, -1) if (i, j) != (0, 0)]
    rounds = 0
    with open(file, "r") as inputs:
        fishes = [[int(letter) for letter in line.strip()] for line in inputs]
        while True:
            flashed = []
            to_flash = []
            # increase every number by 1
            for i in range((len(fishes))):
                for j in range(len(fishes[i])):
                    fishes[i][j] += 1
                    if fishes[i][j] == 10:
                        to_flash.append((i, j))
            # Spyshka
            while len(to_flash) > 0:
                # change fish status to flashed
                flash_fish = to_flash.pop()
                flashed.append(flash_fish)
                # reset state to 0
                fishes[flash_fish[0]][flash_fish[1]] = 0
                # check if we can and have to increase neighbors
                for offset in neighbors:
                    try:
                        i, j = flash_fish[0] + offset[0], flash_fish[1] + offset[1]
                        if (i, j) not in flashed and i > -1 and j > -1:
                            fishes[i][j] += 1
                            # only add once
                            if fishes[i][j] == 10:
                                to_flash.append((i, j))
                    except IndexError:
                        # no fish left out of bounds
                        pass
            rounds += 1
            if len(flashed) == len(fishes) * len(fishes[0]):
                return rounds


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task11_test.txt", 100)
    task2("../data/task11_test.txt")
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task11.txt", 100)
    task2("../data/task11.txt")
    print()


test()
run()
