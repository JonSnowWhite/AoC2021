from Util import time_and_result


@time_and_result
def task1(file):
    scores = {')': 3, ']': 57, '}': 1197, '>': 25137}
    counter_parts = {'{': '}', '(': ')', '[': ']', '<': '>'}
    with open(file, "r") as inputs:
        _sum = 0
        lines = [line.strip() for line in inputs]
        for line in lines:
            stack = []
            for bracket in line:
                if bracket in '([{<':
                    stack.append(bracket)
                elif bracket in ')]}>' and counter_parts[stack.pop()] != bracket:
                    _sum += scores[bracket]
                    break
    return _sum


def process_line(line: str) -> list:
    counter_parts = {'{': '}', '(': ')', '[': ']', '<': '>'}
    stack = []
    for bracket in line:
        if bracket in '([{<':
            stack.append(bracket)
        elif bracket in ')]}>' and counter_parts[stack.pop()] != bracket:
            return []
    return stack


@time_and_result
def task2(file):
    scores = {'(': 1, '[': 2, '{': 3, '<': 4}
    with open(file, "r") as inputs:
        line_scores = []
        lines = [line.strip() for line in inputs]
        for line in lines:
            remaining_brackets = process_line(line)
            if len(remaining_brackets) > 0:
                score = 0
                for bracket in remaining_brackets[::-1]:
                    score *= 5
                    score += scores[bracket]
                line_scores.append(score)
    return sorted(line_scores)[len(line_scores)//2]


def test():
    task1("../data/task10_test.txt")
    task2("../data/task10_test.txt")


def run():
    task1("../data/task10.txt")
    task2("../data/task10.txt")


test()
run()
