from Util import time_and_result


def get_neighbors(graph, node):
    ret = []
    edges = graph[1]
    for i in edges:
        if i[0] == node:
            ret += [i[1]]
        if i[1] == node:
            ret += [i[0]]
    return ret


def dfs(current: str, stack: list, visited: list, end: str, paths: list, graph: tuple):
    # marked as visited
    stack.append(current)
    if current.lower() == current:
        visited.append(current)

    # check if last node
    if current == end:
        paths.append(stack)
        return
    else:
        # get neighbors which we have not visited before
        for neighbor in get_neighbors(graph, current):
            if neighbor not in visited:
                dfs(neighbor, stack[:], visited[:], end, paths, graph)


@time_and_result
def task1(file):
    with open(file, "r") as inputs:
        nodes = []
        edges = []
        graph = (nodes, edges)
        for line in inputs:
            _nodes = line.strip().split("-")
            nodes += _nodes
            edges.append(_nodes)
        paths = []
        dfs("start", [], [], "end", paths, graph)
    return len(paths)


def dfs2(current: str, stack: list, visited: list, end: str, paths: list, graph: tuple, twice_left):
    _visited = visited[:]
    # marked as visited
    stack.append(current)
    if current.islower():
        visited.append(current)
    # check if last node
    if current == end:
        paths.append(stack)
        return
    else:
        # get neighbors which we have not visited before
        for neighbor in get_neighbors(graph, current):
            if neighbor not in visited:
                dfs2(neighbor, stack[:], visited[:], end, paths, graph, twice_left)
            elif twice_left and neighbor not in ["start", "end"]:
                dfs2(neighbor, stack[:], visited[:], end, paths, graph, False)


@time_and_result
def task2(file):
    with open(file, "r") as inputs:
        nodes = []
        edges = []
        graph = (nodes, edges)
        for line in inputs:
            _nodes = line.strip().split("-")
            nodes += _nodes
            edges.append(_nodes)
        paths = []
        dfs2("start", [], [], "end", paths, graph, True)
    return len(paths)


def test():
    print("- - - - - TESTS - - - - -")
    task1("../data/task12_test.txt")
    task2("../data/task12_test.txt")
    print()


def run():
    print("- - - - - TASKS - - - - -")
    task1("../data/task12.txt")
    task2("../data/task12.txt")
    print()


test()
run()
