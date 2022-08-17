def priority(symbol):
    if symbol == '(':
        return 0
    if symbol == '+':
        return 1
    if symbol == '*':
        return 2
    return 0


def getval(a, b, op):
    if op == '+':
        return a + b
    if op == '*':
        return a * b
    return 0


def evalpost(infix, s):
    mp = {}
    j = 0
    for ch in infix:
        if 97 <= ord(ch) <= 122 or 65 <= ord(ch) <= 90:
            if ch not in mp.keys():
                mp[ch] = ord(s[j]) - ord('0')
                j += 1

    val = []
    ops = []

    for i in range(len(infix)):
        ch = infix[i]
        if ch == ' ' or ch == '~':
            continue
        elif ch == '(':
            ops.append(ch)
        elif 97 <= ord(ch) <= 122 or 65 <= ord(ch) <= 90:
            if i > 0 and infix[i-1] == '~':
                val.append(1 - mp[ch])
            else:
                val.append(mp[ch])
        elif ch == ')':
            while len(ops) > 0 and ops[-1] != '(':
                a = val.pop(-1)
                b = val.pop(-1)

                op = ops.pop(-1)

                val.append(getval(b, a, op))

            if len(ops) > 0:
                ops.pop(-1)
        else:
            while len(ops) > 0 and  priority(ops[-1]) >= priority(ch):
                a = val.pop(-1)
                b = val.pop(-1)

                op = ops.pop(-1)

                val.append(getval(b, a, op))

            ops.append(ch)

    while len(ops) > 0:
        a = val.pop(-1)
        b = val.pop(-1)

        op = ops.pop(-1)

        val.append(getval(b, a, op))

    return val[-1] != 0


def print_table(s, posb):
    c = 0
    for p in posb:
        for ch in p:
            if ch == '0':
                print("False\t", end='')
            else:
                print("True\t", end='')

        if evalpost(s, p):
            c += 1
            print("True")
        else:
            print("False")
    return c != 0


def gen(t, i, n, posb):
    if i == n:
        posb.append(t)
        return

    t += '0'
    gen(t, i+1, n, posb)
    t = t[:-1]

    t += '1'
    gen(t, i + 1, n, posb)
    t = t[:-1]


def main():
    n = int(input("Enter number of variables: "))
    posb = []

    gen('', 0, n, posb)

    print("(Use + for OR, * for AND, ~for NOT")
    infix = input("Enter the wff: ")

    if print_table(infix, posb):
        print("\n=>The wff is valid.")
    else:
        print("\n=>The wff is satisfiable.")


main()

# (a+~b)*c
