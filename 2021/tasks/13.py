from Util import time_and_result


def dots_in_paper(paper: list) -> int:
    _sum = 0
    for i in range(len(paper)):
        for j in paper[i]:
            if j == 1:
                _sum += 1
    return _sum


def print_paper(paper: list) -> None:
    for i in range(len(paper)):
        for j in paper[i]:
            if j == 0:
                print(" ", end="")
            else:
                print("#", end="")
        print("")
    print("\n")


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        # input parsing
        dots = []
        axes = []
        for line in inputs:
            if line != "\n" and not line.startswith("fold"):
                dots.append(line.strip().split(","))
            elif line.startswith("fold"):
                axes.append(line.strip().split(" ")[2].split("="))
        max_x = max(int(dot[0]) for dot in dots)
        max_y = max(int(dot[1]) for dot in dots)
        paper = []
        for i in range(max_y+1):
            paper.append((max_x + 1) * [0])
        for dot in dots:
            paper[int(dot[1])][int(dot[0])] = 1
        # print_paper(paper)
        # only interpret first split
        for split in axes[:1]:
            axis = split[0]
            coordinate = int(split[1])
            if axis == "x":
                for line in paper:
                    for i in range(max_x - coordinate + 1):
                        line[coordinate - i] |= line[coordinate + i]
                    # cut/fold paper
                    paper[paper.index(line)] = line[:coordinate]
            if axis == "y":
                for c in range(len(paper[0])):
                    for i in range(max_y - coordinate + 1):
                        paper[coordinate - i][c] |= paper[coordinate + i][c]
                # cut fold paper
                paper = paper[:coordinate]
            # print_paper(paper)
        return dots_in_paper(paper)


@time_and_result
def task2(file):
    with open(file, "r") as inputs:
        # input parsing
        dots = []
        axes = []
        for line in inputs:
            if line != "\n" and not line.startswith("fold"):
                dots.append(line.strip().split(","))
            elif line.startswith("fold"):
                axes.append(line.strip().split(" ")[2].split("="))
        max_x = max(int(dot[0]) for dot in dots)
        max_y = max(int(dot[1]) for dot in dots)
        paper = []
        for i in range(max_y+1):
            paper.append((max_x + 1) * [0])
        for dot in dots:
            paper[int(dot[1])][int(dot[0])] = 1
        # print_paper(paper)
        # only interpret first split
        for split in axes:
            axis = split[0]
            coordinate = int(split[1])
            if axis == "x":
                print(f"splitting at {axis}={coordinate} with max {len(paper[0])}")
                for line in paper:
                    for i in range(max_x - coordinate + 1):
                        try:
                            line[coordinate - i] |= line[coordinate + i]
                        except:
                            pass
                    # cut/fold paper
                    paper[paper.index(line)] = line[:coordinate]
            if axis == "y":
                print(f"splitting at {axis}={coordinate} with max {len(paper)}")
                for c in range(len(paper[0])):
                    for i in range(max_y - coordinate + 1):
                        try:
                            paper[coordinate - i][c] |= paper[coordinate + i][c]
                        except:
                            pass
                # cut fold paper
                paper = paper[:coordinate]
        print_paper(paper)
        return dots_in_paper(paper)

#  # #### ###  #### #  #  ##  #  # ####
#  # #    #  #    # # #  #  # #  #    #
#  # ###  #  #   #  ##   #  # #  #   #
#  # #    ###   #   # #  #### #  #  #
#  # #    # #  #    # #  #  # #  # #
 ##  #    #  # #### #  # #  #  ##  ####


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task13_test.txt")
    task2("../data/task13_test.txt")
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task13.txt")
    task2("../data/task13.txt")
    print()


test()
run()
