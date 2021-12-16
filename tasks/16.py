from Util import time_and_result


def remove_first(num, l):
    for i in range(num):
        l.pop(0)


def parse_packet(packet, counter):

    return parse_version(packet, counter)


def parse_version(packet, counter):
    counter[0] += int(''.join(packet[:3]), 2)

    remove_first(3, packet)
    return parse_type_id(packet, counter)


def parse_type_id(packet, counter):
    type_id = int(''.join(packet[:3]), 2)
    remove_first(3, packet)

    # literal value
    if type_id == 4:
        return parse_literals(packet)
    else:
        return parse_length_type_id(packet, counter, type_id)


def parse_length_type_id(packet, counter, type_id):
    length_type_id = packet[0]
    remove_first(1, packet)

    if length_type_id == '0':
        sub_packets = parse_sub_packets_length(packet, counter)
    else:
        sub_packets = parse_sub_packets_number(packet, counter)

    if type_id == 0:
        return sum(sub_packets)
    elif type_id == 1:
        prod = 1
        for packet in sub_packets:
            prod *= packet
        return prod
    elif type_id == 2:
        return min(sub_packets)
    elif type_id == 3:
        return max(sub_packets)
    elif type_id == 5:
        return int(sub_packets[0] > sub_packets[1])
    elif type_id == 6:
        return int(sub_packets[0] < sub_packets[1])
    elif type_id == 7:
        return int(sub_packets[0] == sub_packets[1])


def parse_sub_packets_length(packet, counter):
    sub_packets_length = int(''.join(packet[:15]), 2)
    remove_first(15, packet)

    sub_packets_to_parse = packet[:sub_packets_length]
    remove_first(sub_packets_length, packet)
    sub_packets = []
    while sub_packets_to_parse:
        sub_packets.append(parse_packet(sub_packets_to_parse, counter))
    return sub_packets


def parse_sub_packets_number(packet, counter):
    sub_packets_number = int(''.join(packet[:11]), 2)
    remove_first(11, packet)

    sub_packets = []
    for _ in range(sub_packets_number):
        sub_packets.append(parse_packet(packet, counter))
    return sub_packets


def parse_literals(packet):

    finished = False
    number = ''
    while not finished:
        literal = packet[:5]
        remove_first(5, packet)
        if literal[0] == '0':
            finished = True
        literal = literal[1:]
        number += ''.join(literal)

    return int(number, 2)


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        packet = [i for i in bin(int('1' + inputs.readline().strip(), 16))[3:]]
        counter = [0]
        parse_packet(packet, counter)
        return counter[0]


@time_and_result
def task2(file):
    with open(file, "r") as inputs:
        packet = [i for i in bin(int('1' + inputs.readline().strip(), 16))[3:]]
        counter = [0]
        return parse_packet(packet, counter)


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task16_test.txt")
    task2("../data/task16_test.txt")
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task16.txt")
    task2("../data/task16.txt")
    print()


test()
run()
