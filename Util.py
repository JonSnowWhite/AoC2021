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

