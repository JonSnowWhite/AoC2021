from Util import time_and_result


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        simple_digits = 0
        for line in inputs:
            examples, output = [x.strip().split(" ") for x in line.split(" | ")]
            for i in output:
                if len(i) in [2, 3, 4, 7]:
                    simple_digits += 1
        return simple_digits


@time_and_result
def task2(file):
    with open(file, "r") as inputs:
        ret_sum = 0
        for line in inputs:
            examples, output = [x.strip().split(" ") for x in line.split(" | ")]
            example_lengths = []
            for i in range(9):
                example_lengths.append([])
            # make accesible by length
            for out in examples:
                if len(out) in [5, 6]:
                    example_lengths[len(out)] += [out]
                else:
                    example_lengths[len(out)] = out
            # parse 1
            c_or_f = example_lengths[2]
            # parse 7
            a = [x for x in example_lengths[3] if x not in c_or_f][0]
            # parse 4
            b_or_d = [x for x in example_lengths[4] if x not in c_or_f]
            # parse 3
            encoding_of_3 = [y for y in example_lengths[5] if c_or_f[0] in y and c_or_f[1] in y][0]
            g = [x for x in encoding_of_3 if x != a and x not in c_or_f and x not in b_or_d][0]
            # determine e
            e = list(filter(lambda letter: letter not in c_or_f and letter not in b_or_d and letter != a and letter != g, "abcdefg"))[0]
            # parse 2
            encoding_of_2 = list(filter(lambda number: e in number, example_lengths[5]))[0]
            c = list(filter(lambda letter: letter not in b_or_d and letter != a and letter != g and letter != e, encoding_of_2))[0]
            f = list(filter(lambda letter: letter != c, c_or_f))[0]
            d = list(filter(lambda letter: letter in b_or_d, encoding_of_2))[0]
            b = list(filter(lambda letter: letter != d, b_or_d))[0]

            # rearrange letters
            parsing_table = {a: 'a', b: 'b', c: 'c', d: 'd', e: 'e', f: 'f', g: 'g'}
            # parse outputs
            num_table = {'cf': 1, 'acdeg': 2, 'acdfg': 3, 'bcdf': 4, 'abdfg': 5, 'abdefg': 6, 'acf': 7, 'abcdefg': 8, 'abcdfg': 9, 'abcefg': 0}
            num = ''
            for out in output:
                digit = []
                for letter in out:
                    digit += parsing_table[letter]
                digit = ''.join(sorted(digit))
                num += str(num_table[digit])
            ret_sum += int(num)
        return ret_sum


def test():
    task1("../data/task8_test.txt")
    task2("../data/task8_test.txt")


def run():
    task1("../data/task8.txt")
    task2("../data/task8.txt")


test()
run()
