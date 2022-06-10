"""
Tic Tac Toe Player
"""

import copy
from json.encoder import INFINITY
from xml.dom import InvalidAccessErr

X = "X"
O = "O"
EMPTY = None


def initial_state():
    """
    Returns starting state of the board.
    """
    return [[EMPTY, EMPTY, EMPTY],
            [EMPTY, EMPTY, EMPTY],
            [EMPTY, EMPTY, EMPTY]]


def player(board):
    """
    Returns player who has the next turn on a board.
    """
    x_num = 0
    o_num = 0
    for row in board:
        for i in row:
            if i == X:
                x_num += 1
            elif i == O:
                o_num += 1
    if x_num == o_num:
        return X
    else:
        return O


def actions(board):
    """
    Returns set of all possible actions (i, j) available on the board.
    """
    ret = []
    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] == EMPTY:
                ret.append((i, j))
    return ret


def result(board, action):
    """
    Returns the board that results from making move (i, j) on the board.
    """
    play = player(board)
    x, y = action
    if board[x][y] != EMPTY:
        raise InvalidAccessErr
    result_board = copy.deepcopy(board)
    result_board[x][y] = play
    return result_board
    


def winner(board):
    """
    Returns the winner of the game, if there is one.
    """
    for i in range(len(board)):
        if board[i][0] == board[i][1] and board[i][1] == board[i][2] and board[i][0] != EMPTY:
            return board[i][0]
        if board[0][i] == board[1][i] and board[2][i] == board[1][i] and board[0][i] != EMPTY:
            return board[0][i]
    if board[0][0] == board[1][1] and board[1][1] == board[2][2] and board[1][1] != EMPTY:
        return board[1][1]
    elif board[0][2] == board[1][1] and board[1][1] == board[2][0] and board[1][1] != EMPTY:
        return board[1][1]
    return None


def terminal(board):
    """
    Returns True if game is over, False otherwise.
    """
    if winner(board) is not None or is_full(board):
        return True
    return False

def is_full(board):
    for row in board:
        for block in row:
            if block is EMPTY:
                return False
    return True

def utility(board):
    """
    Returns 1 if X has won the game, -1 if O has won, 0 otherwise.
    """
    if winner(board) == X:
        return 1
    elif winner(board) == O:
        return -1
    else:
        return 0 


def min_value(board):
    if terminal(board):
        return utility(board)
    v = INFINITY
    for action in actions(board):
        v = min(v, max_value(result(board, action)))
    return v
    
def max_value(board):
    if terminal(board):
        return utility(board)
    v = -INFINITY
    for action in actions(board):
        v = max(v, min_value(result(board, action)))
    return v
    
def max_index(values):
    maxi = 0
    maxv = values[0]
    for i, v in enumerate(values):
        if maxv < v:
            maxi = i
            maxv = v
    return maxi

def min_index(values):
    mini = 0
    minv = values[0]
    for i, v in enumerate(values):
        if minv > v:
            minv = v
            mini = i
    return mini

def minimax(board):
    """
    Returns the optimal action for the current player on the board.
    """
    if terminal(board):
        return None
    
    Actions = actions(board)
    values = []
    if player(board) == X:
        for action in actions(board):
            values.append(min_value(result(board, action)))
        return Actions[max_index(values)]
        
    elif player(board) == O:
        for action in actions(board):
            values.append(max_value(result(board, action)))
        return Actions[min_index(values)]
                
    