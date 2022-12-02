from Util import time_and_result


translate_dict = {'X': 'A', 'Y': 'B', 'Z': 'C'}
score_dict = {'A': 1, 'B': 2, 'C': 3}
# 'B' paper beats 'A' scissors so 'AB' means win
win_dict = {'AA': 3, 'BB': 3, 'CC': 3, 'AB': 6, 'AC': 0, 'BA': 0, 'BC': 6, 'CA': 6, 'CB': 0}
# holds what wins you the round
# e.g. Paper beats rock
get_winning_dict = {'A': 'B', 'B': 'C', 'C': 'A'}
# holds what looses you the round
get_loose_dict = {'A': 'C', 'B': 'A', 'C': 'B'}


@time_and_result
def task1():
    """
    simply save everything in precomputed dictionaries, we COULD have used indexes of a list and use modulo operator on
    index for getting who wins and stuff but eh
    """
    with open("../data/task2.txt", "r") as measurements:
        total_score = 0
        for line in measurements:
            _line = line.strip()
            opponent_choice, own_choice = _line.split(" ")
            own_choice = translate_dict[own_choice]
            total_score += score_dict[own_choice]
            total_score += win_dict[opponent_choice + own_choice]
    return total_score


@time_and_result
def task2():
    """
    Same thing really
    """
    with open("../data/task2.txt", "r") as measurements:
        total_score = 0
        for line in measurements:
            _line = line.strip()
            opponent_choice, own_choice = _line.split(" ")
            own_choice = translate_dict[own_choice]
            # translate own_choice into draw winning or loosing choice
            if own_choice == 'A':
                own_choice = get_loose_dict[opponent_choice]
            elif own_choice == 'B':
                own_choice = opponent_choice
            elif own_choice == 'C':
                own_choice = get_winning_dict[opponent_choice]
            total_score += score_dict[own_choice]
            total_score += win_dict[opponent_choice + own_choice]
    return total_score


task1()
task2()
