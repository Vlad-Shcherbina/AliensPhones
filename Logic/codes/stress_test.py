n = 200000
m = 6000

with open("StressTest", 'w') as fout:
    for i in range(n, n+m):
        print >>fout, i