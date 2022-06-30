from email.mime import base
from re import A
from logic import *

AKnight = Symbol("A is a Knight")
AKnave = Symbol("A is a Knave")

BKnight = Symbol("B is a Knight")
BKnave = Symbol("B is a Knave")

CKnight = Symbol("C is a Knight")
CKnave = Symbol("C is a Knave")

base_knowledge = And(
    Or(AKnight, AKnave),
    Or(BKnight, BKnave),
    Implication(AKnight, Not(AKnave)),
    Implication(BKnight, Not(BKnave)),
    Implication(AKnave, Not(AKnight)),
    Implication(BKnave, Not(BKnight))
)

# Puzzle 0
# A says "I am both a knight and a knave."
Asays_puzzle0 = And(AKnight, AKnave)
knowledge0 = And(
    base_knowledge,
    Biconditional(AKnight, Asays_puzzle0),
    Biconditional(AKnave, Not(Asays_puzzle0))
)

# Puzzle 1
# A says "We are both knaves."
# B says nothing.
Asays_puzzle1 = And(AKnave, BKnave)
knowledge1 = And(
    base_knowledge,
    Biconditional(AKnight, Asays_puzzle1),
    Biconditional(AKnave, Not(Asays_puzzle1))
)

# Puzzle 2
# A says "We are the same kind."
# B says "We are of different kinds."
Asays_puzzle2 = Or(And(AKnight, BKnight), And(AKnave, BKnave))
Bsays_puzzle2 = Or(And(AKnight, BKnave), And(AKnave, BKnight))
knowledge2 = And(
    base_knowledge,
    Biconditional(AKnight, Asays_puzzle2),
    Biconditional(AKnave, Not(Asays_puzzle2)),
    Biconditional(BKnight, Bsays_puzzle2),
    Biconditional(BKnave, Not(Bsays_puzzle2))
)

# Puzzle 3
# A says either "I am a knight." or "I am a knave.", but you don't know which.
# B says "A said 'I am a knave'."
# B says "C is a knave."
# C says "A is a knight."
Bsays_puzzle3 = And(AKnave, CKnave)
Csays_puzzle3 = AKnight
knowledge3 = And(
    base_knowledge,
    AKnight,
    Implication(CKnight, Not(CKnave)),
    Implication(CKnave, Not(CKnight)),
    Biconditional(BKnight, Bsays_puzzle3),
    Biconditional(BKnave, Not(Bsays_puzzle3)),
    Biconditional(CKnight, Csays_puzzle3),
    Biconditional(CKnave, Not(Csays_puzzle3))
)


def main():
    symbols = [AKnight, AKnave, BKnight, BKnave, CKnight, CKnave]
    puzzles = [
        ("Puzzle 0", knowledge0),
        ("Puzzle 1", knowledge1),
        ("Puzzle 2", knowledge2),
        ("Puzzle 3", knowledge3)
    ]
    for puzzle, knowledge in puzzles:
        print(puzzle)
        if len(knowledge.conjuncts) == 0:
            print("    Not yet implemented.")
        else:
            for symbol in symbols:
                if model_check(knowledge, symbol):
                    print(f"    {symbol}")


if __name__ == "__main__":
    main()
