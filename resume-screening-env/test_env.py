from env.environment import ResumeScreeningEnv
from env.grader import compute_ground_truth_scores, rank_candidates
from models.action import Action

env = ResumeScreeningEnv(task="easy")
obs = env.reset()

print("Job:", obs.job_description["role"])
print("Candidates:", [c["candidate_id"] for c in obs.candidates])

# Test with PERFECT ranking
gt_scores = compute_ground_truth_scores(obs.job_description, obs.candidates)
gt_ranking = rank_candidates(gt_scores)
action = Action(ranked_candidates=gt_ranking)
obs, reward, done, info = env.step(action)
print("Easy perfect ranking reward:", reward.score)
print("Done:", done)
print("Info:", info)
print()

for task in ["medium", "hard"]:
    env2 = ResumeScreeningEnv(task=task)
    obs2 = env2.reset()
    gt_scores2 = compute_ground_truth_scores(obs2.job_description, obs2.candidates)
    gt_ranking2 = rank_candidates(gt_scores2)
    action2 = Action(ranked_candidates=gt_ranking2)
    _, r, _, _ = env2.step(action2)
    print(f"Task={task} | Candidates={len(gt_ranking2)} | Perfect ranking reward={r.score:.2f}")