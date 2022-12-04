from datetime import datetime


def time_and_result(f):
    def g(*args, **kwargs):
        start_time = datetime.now()
        ret = f(*args, **kwargs)
        stop_time = datetime.now()
        seconds = int((stop_time-start_time).total_seconds() * 1000000) / 1000
        print(f'Executing {f.__name__} took {seconds} milliseconds with result {ret}')
        return ret
    return g


def test_inputs(task: int) -> list[str]:
    return inputs(f'../data/task{task}_test.txt')


def task_inputs(task: int) -> list[str]:
    return inputs(f'../data/task{task}.txt')


def inputs(file: str) -> list[str]:
    """
    Generator that yields all \n-stripped text lines excluding for empty lines.
    Could probably be done in one line with map and filter
    """
    lines = []
    with open(file, "r") as measurements:
        for line in measurements:
            _line = line.strip()
            if _line == "":
                continue
            else:
                lines += [_line]
    return lines
