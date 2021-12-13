from Util import time_and_result


class Board:

    def __init__(self, numbers, size=5):
        self.numbers = numbers
        self.ticked = []
        self.size = size
        for i in range(size):
            _ticked = size * [0]
            self.ticked.append(_ticked)

    def update(self, number):
        for i in range(self.size):
            for j in range(self.size):
                if self.numbers[i][j] == number:
                    self.ticked[i][j] = 1

    def has_bingo(self):
        for i in range(self.size):
            # lines
            if sum(self.ticked[i]) == self.size:
                return True
            # columns
            if sum([self.ticked[j][i] for j in range(self.size)]) == self.size:
                return True
        return False

    def get_sum_of_unticked(self):
        ret = 0
        for i in range(self.size):
            for j in range(self.size):
                if self.ticked[i][j] == 0:
                    ret += self.numbers[i][j]
        return ret

    def __repr__(self):
        return self.__str__()

    def __str__(self):
        return "\n".join([str(a) for a in self.numbers])


def parse_input(file):
    numbers = []
    boards = []
    _board = []
    with open(file, "r") as measurements:
        lines = [line.strip("\n") for line in measurements]
        for line in lines:
            # empty line
            if line.strip(" ") == "":
                continue
            # numbers line
            if len(line) > 40:
                numbers = [int(num) for num in line.split(",")]
            # board line
            else:
                board_line = [int(num) for num in line.split(" ") if num != ""]
                if len(board_line) != 5:
                    raise Exception("Board line parsing failed")
                _board.append(board_line)
                if len(_board) == 5:
                    boards.append(Board(_board, 5))
                    _board = []
    return numbers, boards


@time_and_result
def task1():
    numbers, boards = parse_input(file="../data/task4.txt")
    for number in numbers:
        for board in boards:
            board.update(number)
            if board.has_bingo():
                return board.get_sum_of_unticked()*number


@time_and_result
def task2():
    numbers, boards = parse_input(file="../data/task4.txt")
    for number in numbers:
        _boards = boards[:]
        print("boards left: ", len(boards))
        for board in _boards:
            board.update(number)
            if board.has_bingo():
                if len(_boards) == 1:
                    return board.get_sum_of_unticked()*number
                else:
                    boards.remove(board)


task1()
task2()
