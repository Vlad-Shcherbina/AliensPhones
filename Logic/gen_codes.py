from random import randrange, seed
from prefix import PrefixSet

from codes import reusable, unique, get_code_path, get_codes

seed(123)

used = PrefixSet()

for name in reusable:
    codes = get_codes(name)
    for code in codes:
        used.add(code)


counts = dict(
    AntiAlvine=10,
    Surfaktant=10,
    PlasmaToxone=10,
    BenzylAlienat=10,
    )

for name in unique:
    if name in counts:
        with open(get_code_path(name), 'w') as fout:
            for _ in range(counts[name]):
                while True:
                    code = str(randrange(10**5, 10**6))
                    if used.can_add(code):
                        break;
                used.add(code)
                print>>fout, code
        del counts[name]

print len(used), 'codes total'

assert not counts, counts