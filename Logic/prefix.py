

class PrefixSet(object):
    'Set that checks and maintains prefix property'

    def __init__(self):
        self.elems = set()
        self.prefixes = set()

    def can_add(self, e):
        if e in self.prefixes:
            return False
        for i in range(len(e)+1):
            if e[:i] in self.elems:
                return False
        return True

    def add(self, e):
        assert self.can_add(e)
        self.elems.add(e)
        for i in range(len(e)+1):
            self.prefixes.add(e[:i])

    def __len__(self):
        return len(self.elems)


