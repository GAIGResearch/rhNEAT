from os import listdir
from os.path import isfile, join
import numpy as np
import matplotlib.pyplot as plt

# rhNEAT-1: popsize 10, indl = 15, heuristic=accum, species=on, client_avg_fitness=off, fitness = fitness + 0.2 * (lastScore - fitness);
# rhNEAT-2: popsize 10, indl = 15, heuristic=final, species=on, client_avg_fitness=off, fitness = fitness + 0.2 * (lastScore - fitness);
# rhNEAT-3: popsize 10, indl = 15, heuristic=accum_disc, species=on, client_avg_fitness=off, fitness = fitness + 0.2 * (lastScore - fitness);
# rhNEAT-4: popsize 10, indl = 15, heuristic=final, species=on, client_avg_fitness=off, fitness = lastScore;
# rhNEAT-5: popsize 20, indl = 30, heuristic=accum_disc, species=on, client_avg_fitness=off, fitness = fitness + 0.2 * (lastScore - fitness);
# rhNEAT-6: rhNEAT-4 + score, -hp, pos
# rhNEAT-7: rhNEAT-4 - SPECIES
# rhNEAT-8: rhNEAT-4 - CARRY_POP
# rhNEAT-9: rhNEAT-4 - SPECIES, - CARRY_POP

#------ From here on, also including 'movable' sprites, and variants of rhneat-4 config

# rhneat_all - with all features
# rhneat_red - dynamic feature selection
# rhneat_A = rhneat_red - CARRY_POP
# rhneat_B = rhneat_red - SPECIES
# rhneat_C = rhneat_red - SPECIES, CARRY_POP
# rhneat_D = rhneat_all - CARRY_POP
# rhneat_E = rhneat_all - SPECIES
# rhneat_F = rhneat_all - SPECIES, CARRY_POP
# rhneat_G = rhneat_red, heuristic=accum_disc
# rhneat_H = rhneat_red, heuristic=accum
# rhneat_I = rhneat_red, fitness = fitness + 0.2 * (lastScore - fitness);
# rhneat_J = rhneat_red, fitness = fitness + (1/n) * (lastScore - fitness);

# running:

# to be run:


# Ablations on red
# file_paths = ["rhneat_C/", "rhneat_B/", "rhneat_A/", "rhneat_red/"]

# Ablations on all
# file_paths = ["rhneat_all/", "rhneat_E/", "rhneat_D/", "rhneat_F/"]
# names = ["rhNEAT", "rhNEAT(-sp)", "rhNEAT(-cp)", "rhNEAT(-sp,-cp)"]
# names = ["baseline rhNEAT", "rhNEAT(+cp)", "rhNEAT(+sp)", "rhNEAT(+sp,+cp)"]

# Different heuristics (red)
# file_paths = ["rhneat_red/", "rhneat_H/", "rhneat_G/"]
# names = ["rhNEAT", "rhNEAT-acc", "rhNEAT-accdisc"]

# Different fitness calculations (red)
# file_paths = ["rhneat_red/", "rhneat_I/", "rhneat_J/"]
# names = ["rhNEAT", "rhNEAT-lr", "rhNEAT-avg"]

# SotA
file_paths = ["rhneat_red/", "rhea/", "mcts/"]
names = ["rhNEAT", "RHEA", "MCTS"]

# file_paths = ["rhneat_1/", "rhneat_2/", "rhneat_3/", "rhneat_4/", "rhneat_5/", "rhneat_6/", "mcts/", "rhea/"]

# file_paths = ["rhneat_4/", "rhneat_7/", "mcts/", "rhea/"]
colors = ["#E66908", "#FFCE6B", "#5F7EC6", "#FF8A6B", "#B3F064", "#8D5DC7", "#FFF36B", "#0000FF"]

old_win_rate = [0, 4, 0, 100, 10, 13.13, 11, 46, 12, 20, 78.33, 54.55, 37.5, 77.78, 98.99, 65, 100, 100, 96, 100]
old_avg_score = [21.0, -0.02, 7.44, 17.44, 0.23, 4.06, -0.69, 0.48, 14.4, 6.74, 0.79, 3.65, 0.33, 5.15, 56.09, 2533.07, 17.78, 68.87, 35.72, 14.2]

n_games = 20
game_mapping = ["Dig Dug",
                "Lemmings",
                "Roguelike",
                "Chopper",
                "Crossfire",
                "Chase",
                "Camel Race",
                "Escape",
                "Hungry Birds",
                "Bait",
                "Wait for Breakfast",
                "Survive Zombies",
                "Modality",
                "Missile Command",
                "Plaque Attack",
                "SeaQuest",
                "Infection",
                "Aliens",
                "Butterflies",
                "Intersection"
                ]


def process_file(f_name):
    # game = int(f_name.split("/")[-1].split("_")[1])
    game = int(f_name.split("/")[-1].split("_")[1])
    win = []
    score = []
    # remove_output = ""
    with open(f_name) as f:
        lines = f.readlines()
        for line in lines:
            if "Result " in line:
                split_line = line.split(' ')
                win.append(int(split_line[1]))
                score.append(float(split_line[2]))
                # remove_output += line
    # with open(f_name, "w") as f:
    #     f.write(remove_output)

    win_rate = np.average(win)
    score_avg = np.average(score)
    # print(game, win_rate*100, score_avg)
    return game, win_rate*100, score_avg


def plot(all_res, include_soa = False, label_loc='upper left'):
    """
    Bar plot for each game compared against SotA
    :param res - mapping res[game] = (win, score)
    """
    x = np.arange(n_games)
    fig, ax = plt.subplots(figsize=(10, 5))
    n = 1 + len(file_paths)
    w = 1.0 / (n+1)
    half_w = n*w * 0.5

    xpos = x - half_w
    data_idx = 0

    if include_soa:
        ax.bar(xpos, old_win_rate, width=w, label='SotA', color="#56CE8A", edgecolor='black')

    for res in all_res:
        xpos = x + w*(data_idx+1) - half_w
        # alg_name = file_paths[data_idx][0:-1]
        alg_name = names[data_idx] #[0:-1]
        ax.bar(xpos, res, width=w, label=alg_name, color=colors[data_idx], edgecolor='black')
        data_idx+=1

    ax.set_ylabel('Win Rate')
    ax.set_xlabel('Game')
    plt.xticks(x, game_mapping, rotation=45, ha='right')
    plt.xlim(-1, 21)
    # plt.ylim(0, 105)
    ax.yaxis.grid()

    plt.legend(loc=label_loc, fontsize=13)
    plt.tight_layout()
    plt.show()


def main():

    include_scores_sota = True

    n_file_paths = len(file_paths)
    all_res = [0] * n_file_paths
    data_idx = 0
    if include_scores_sota:
        all_scores = [0] * (n_file_paths+1)
        all_scores_n = [0] * (n_file_paths+1)
    else:
        all_scores = [0] * (n_file_paths)
        all_scores_n = [0] * (n_file_paths)

    for file_path in file_paths:

        files = [join(file_path, f) for f in listdir(file_path) if isfile(join(file_path, f)) and f.endswith(".txt")]
        res = [0] * n_games
        scores = [0] * n_games
        better_than_and_positive = 0
        better_than = 0
        avg_than = 0

        for f in files:
            game, win_rate, score = process_file(f)
            res[game] = win_rate
            scores[game] = score
            if win_rate > 0 and win_rate >= old_win_rate[game]:
                better_than_and_positive += 1
            elif win_rate >= old_win_rate[game]:
                better_than += 1
            else:
                avg_than += old_win_rate[game] - win_rate


        all_res[data_idx] = res
        all_scores[data_idx] = scores
        all_scores_n[data_idx] = [0] * n_games
        data_idx+=1

        std_err = np.std(res) / np.sqrt(len(res))
        print(file_path + " equiv. SotA: " + str(better_than_and_positive) + " " + str(better_than) + " "+ str(avg_than/n_games) + " " + str(np.average(res)) + " (" + str(std_err) + ")")

    max_wr = [0] * n_games
    max_wr_count = [0] * n_file_paths
    max_unique = [0] * n_games

    for game_idx in range(n_games):
        m = [row[game_idx] for row in all_res]
        max_wr[game_idx] = np.max(m)

        n_val = 0
        for val in m:
            if val == max_wr[game_idx]:
                n_val += 1
        if n_val == 1:
            max_unique[game_idx] = 1
        else:
            max_unique[game_idx] = 0


    for i in range(len(all_res)):
        alg = all_res[i]
        for game_idx in range(n_games):
            if max_wr[game_idx] == alg[game_idx] and max_wr[game_idx] > 0 and max_unique[game_idx] == 1:
                max_wr_count[i] += 1

    for i in range(len(file_paths)):
        print(file_paths[i] + " max win rate count: " + str(max_wr_count[i]))

    # SCORES
    if include_scores_sota:
        all_scores[data_idx] = old_avg_score
        all_scores_n[data_idx] = old_avg_score
        file_paths.append("SotA")

    std_err = np.std(old_win_rate) / np.sqrt(len(old_win_rate))
    print("StoA avg win rate " + str(np.average(old_win_rate)) + " (" + str(std_err) + ")")

    max = [0] * n_games
    min = [0] * n_games
    higher_than_scores = [0] * n_games
    for game_idx in range(n_games):
        m = [row[game_idx] for row in all_scores]
        max[game_idx] = np.max(m)
        min[game_idx] = np.min(m)

    for i in range(len(all_scores)):
        alg = all_scores[i]
        for game_idx in range(n_games):
            if max[game_idx] - min[game_idx] == 0:
                all_scores_n[i][game_idx] = 0
            else:
                all_scores_n[i][game_idx] = (alg[game_idx] - min[game_idx]) / (max[game_idx] - min[game_idx])
            if all_scores_n[i][game_idx] == 1.0:
                higher_than_scores[i] += 1

    for i in range(len(all_scores)):
        print(file_paths[i] + " scores: " + str(higher_than_scores[i]) + " " + str( np.average(all_scores_n[i])))

    # plot(all_scores_n, True, 'upper right')
    plot(all_res, True)

main()
